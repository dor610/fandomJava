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
import java.util.*;

@Service
public class PostServices {

    private PostRepository postRepository;
    private PostLogServices pls;

    @Autowired
    public PostServices(PostRepository postRepository, PostLogServices pls){
        this.pls = pls;
        this.postRepository = postRepository;
    }

    //lấy nhiều post đã được duyệt từ mới tới cũ
    public Map<String,Post> getPosts(int page){
        Pageable pageable = PageRequest.of(page, 1);

        Page<Post> posts = postRepository.getPostByState(PostState.APPROVED, pageable);

        Map<String, Post> map = new LinkedHashMap();
        for(Post p: posts.getContent()){
            map.put(p.getId(), p);
        }
        return map;
    }

    //lấy một post theo id
    public Post getPost(String id){
        //Post post = postRepository.findPost(id);
        //return post;
        Optional<Post> postOptional = postRepository.findById(id);
        return postOptional.orElse(null);
    }

    //lấy post by tác giả
    public Map<String, Post> getPostsByAuthor(String author, int page){
        Pageable pageable = PageRequest.of(page, 1);
        Page<Post> postPage = postRepository.findPostsByAuthor(author, pageable);
        Map<String, Post> map = new LinkedHashMap();
        for(Post p: postPage.getContent()){
            map.put(p.getId(), p);
        }
        return map;
    }

    //lấy các post theo state và theo tác giả
    public Map<String, Post> getPostByAuthorAndState(String author, PostState state, int page){
        Pageable pageable = PageRequest.of(page, 3);
        Page<Post> postPage = postRepository.getPostByAuthorAndState(author, state, pageable);
        Map<String, Post> map = new LinkedHashMap();
        for(Post p: postPage.getContent()){
            map.put(p.getId(), p);
        }
        return map;
    }

    public Map<String,Post> getPendingPosts(int page){
        Pageable pageable = PageRequest.of(page, 1);
        Page<Post> posts = postRepository.getPostByState(PostState.PENDING, pageable);
        Map<String, Post> map = new LinkedHashMap<>();
        if(posts.getContent().size() > 0){
            for(Post p: posts.getContent()){
                map.put(p.getId(), p);
            }
        }
        return map;
    }

    public int countPostsByAuthorAndState(String author, PostState state){
        return postRepository.countPostsByAuthorAndState(author, state);
    }
    public int countPostByState(PostState state){
        return  postRepository.countPostsByState(state);
    }

    public Map<String,Post> getLockedPosts(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Post> posts = postRepository.getPostByState(PostState.LOCKED, pageable);
        Map<String, Post> map = new LinkedHashMap<>();
        if(posts.getContent().size() > 0){
            for(Post p: posts.getContent()){
                map.put(p.getId(), p);
            }
        }
        return map;
    }

    public boolean approval(String id){
        Optional<Post> postContainer = postRepository.findById(id);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setState(PostState.APPROVED);
            postRepository.save(post);
            pls.writeLog(id, PostState.APPROVED, "Bài viết đã được đuyệt");
            return true;
        }else return false;
    }

    public boolean lockPost(String id, String note){
        Optional<Post> postContainer = postRepository.findById(id);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setState(PostState.LOCKED);
            postRepository.save(post);
            pls.writeLog(id, PostState.LOCKED, note);
            return true;
        }else return false;
    }

    public boolean unlock(String id){
        Optional<Post> postContainer = postRepository.findById(id);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setState(PostState.APPROVED);
            postRepository.save(post);
            pls.writeLog(id, PostState.APPROVED, "Được mở khoá");
            return true;
        }
        return false;
    }

    public boolean delete(String id, String note){
        Optional<Post> postContainer = postRepository.findById(id);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setState(PostState.DELETED);
            postRepository.save(post);
            pls.writeLog(id, PostState.DELETED, note);
            return true;
        }else return false;
    }

    public Post createTextPost(String author, String title, String content){
        Post post = new Post(title, content, author);
        pls.writeLog(post.getId(), PostState.CREATED, "Tạo mới");
        return postRepository.insert(post);
    }

    public void createImagePost(MultipartFile[] files, String[] description, String author, String content, String title)  throws IOException, DbxException {
        Map<String, Media> map = new LinkedHashMap<>();
        ArrayList<MultipartFile> arr = new ArrayList<>(Arrays.asList(files));
        for(int i = 0; i < arr.size(); i++){
            try {
                MultipartFile file = arr.get(i);
                Media m = DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + "");
                m.setDescription(description[i]);
                map.put(m.getName(), m);
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            }
        }
        Post post = new Post(title, content, author, map);
        pls.writeLog(post.getId(), PostState.CREATED, "Tạo mới");
        postRepository.save(post);
    }

    public void createVideoPost(MultipartFile file, String description, String author, String content, String title) throws IOException, DbxException{
        Media m = DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + "");
        m.setDescription(description);
        Post post = new Post(title, content, author, m);
        pls.writeLog(post.getId(), PostState.CREATED, "Tạo mới");
        postRepository.save(post);
    }

    public void createImageAndVideoPost(MultipartFile[] images, String[] imageDes, MultipartFile video, String videoDes, String author, String content, String title)
        throws  IOException, DbxException{
        Map<String, Media> map = new LinkedHashMap<>();
        ArrayList<MultipartFile> arr = new ArrayList<>(Arrays.asList(images));
        for(int i = 0; i < arr.size(); i++){
            try {
                MultipartFile file = arr.get(i);
                Media m = DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + "");
                m.setDescription(imageDes[i]);
                map.put(m.getName(), m);
            } catch (DbxException | IOException e) {
                e.printStackTrace();
            }
        }
        Media media = DropboxUtils.uploadFile(video.getInputStream(), System.currentTimeMillis() + "");
        media.setDescription(videoDes);

        Post post = new Post(title, content, author, map, media);
        pls.writeLog(post.getId(), PostState.CREATED, "Tạo mới");
        postRepository.save(post);
    }

    public void updateTextPost(String postId, String content) throws Exception{
        Optional<Post> postContainer = postRepository.findById(postId);
        if(postContainer.isPresent()){
            Post post = postContainer.get();
            post.setPostContent(content);
            post.setState(PostState.PENDING);
            pls.writeLog(post.getId(), PostState.CREATED, "Cập nhật");
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
            pls.writeLog(post.getId(), PostState.CREATED, "Cập nhật");
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
            pls.writeLog(post.getId(), PostState.CREATED, "Cập nhật");
            postRepository.save(post);
        }else {
            throw new Exception("No_Content");
        }
    }




}
