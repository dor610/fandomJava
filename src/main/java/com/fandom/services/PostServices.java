package com.fandom.services;

import com.dropbox.core.DbxException;
import com.fandom.model.Media;
import com.fandom.model.Post;
import com.fandom.model.PostState;
import com.fandom.model.PostType;
import com.fandom.repository.PostRepository;
import com.fandom.util.DropboxUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PostServices {

    private PostRepository postRepository;

    @Autowired
    public PostServices(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> getPosts(int page){
        Pageable pageable = PageRequest.of(page, 1);

        Page<Post> posts = postRepository.findPostsByState(PostState.APPROVED, pageable);

        List<Post> postList = posts.getContent();

        return postList;
    }

    public Post getPost(String id){
        //Post post = postRepository.findPost(id);
        //return post;
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.orElse(null);
    }

    public List<Post> getPostsByAuthor(String author, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> postPage = postRepository.findPostsByAuthor(author, pageable);
        return postPage.getContent();
    }

    public List<Post> getPostsByAuthor(String author){
        List<Post> posts = postRepository.findPostsByAuthor(author);
        return posts;
    }

    public List<Post> getPendingPosts(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> posts = postRepository.findPostsByState(PostState.PENDING, pageable);
        return posts.getContent();
    }

    public int countPedingPostsByAuthor(String author){
        return postRepository.countPostsByAuthorAndState(author, PostState.PENDING);
    }

    public List<Post> getBlockedPosts(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> posts = postRepository.findPostsByState(PostState.BLOCKED, pageable);
        return posts.getContent();
    }

    public boolean approval(String id){
        Optional<Post> postContainer = postRepository.findById(id);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setState(PostState.APPROVED);
            postRepository.save(post);
            return true;
        }else return false;
    }

    public boolean block(String id){
        Optional<Post> postContainer = postRepository.findById(id);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setState(PostState.BLOCKED);
            postRepository.save(post);
            return true;
        }else return false;
    }

    public boolean delete(String id){
        Optional<Post> postContainer = postRepository.findById(id);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setState(PostState.DELETED);
            postRepository.save(post);
            return true;
        }else return false;
    }

    public Post createTextPost(String author, String title, String content){
        Post post = new Post(title, content, author);
        return postRepository.insert(post);
    }

    public void createImagePost(MultipartFile[] files, String author, String content, String title)  throws IOException, DbxException {
        List<Media> imgList = new ArrayList<>();
        Arrays.asList(files).stream().forEach(file -> {
            try {
                imgList.add(DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + ""));
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            }
        });
        Post post = new Post(title, content, author, imgList.toArray(new Media[0]), PostType.IMAGE);
        postRepository.save(post);
    }

    public void createVideoPost(MultipartFile file, String author, String content, String title) throws IOException, DbxException{
        List<Media> video = new ArrayList<>();
        video.add(DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + ""));
        Post post = new Post(title, content, author, video.toArray(new Media[0]), PostType.VIDEO);
        postRepository.save(post);
    }

    public void updateTextPost(String postId, String content) throws Exception{
        Optional<Post> postContainer = postRepository.findById(postId);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setPostContent(content);
            post.setState(PostState.PENDING);
            postRepository.save(post);
        }else {
            throw new Exception("No_Content");
        }
    }

    public void updateImgPost(String postId, String content, String url) throws Exception{
        Optional<Post> postContainer = postRepository.findById(postId);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setPostContent(content);
            post.setState(PostState.PENDING);
            List<String> media = new ArrayList<>();
            media.add(url);
            ////
            postRepository.save(post);
        }else {
            throw new Exception("No_Content");
        }
    }
/////
    public void updateVideoPost(String postId, String content) throws Exception{
        Optional<Post> postContainer = postRepository.findById(postId);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setPostContent(content);
            post.setState(PostState.PENDING);
            postRepository.save(post);
        }else {
            throw new Exception("No_Content");
        }
    }




}
