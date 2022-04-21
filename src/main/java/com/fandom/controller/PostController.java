package com.fandom.controller;

import com.dropbox.core.DbxException;
import com.fandom.model.Post;
import com.fandom.model.PostType;
import com.fandom.services.PostServices;
import com.fandom.util.DropboxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    private PostServices postServices;

    @Autowired
    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }

    //get posts, 10 per time
    @GetMapping("/post/{page}") //
    public ResponseEntity<List<Post>> getPosts(@PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        List<Post> posts = postServices.getPosts(page);
        if(posts != null){
            return new ResponseEntity<>(posts, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    //get post by id
    @GetMapping("/post/detail/{postId}") //
    public ResponseEntity<Post> getPost(@PathVariable("postId") String postId){
        Post post = postServices.getPost(postId);
        if(post == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //get posts by author, 10 per time
    @GetMapping("/post/author/{author}/{page}") //
    public ResponseEntity<List<Post>> getPostByAuthor(@PathVariable("author") String author, @PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        List<Post> posts = postServices.getPostsByAuthor(author, page);
        if (posts == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //get pending posts, 10 per time
    @GetMapping("/post/pending/{page}") //
    public ResponseEntity<List<Post>> getPendingPost(@PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        List<Post> posts = postServices.getPendingPosts(page);
        if (posts == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //count pending post by author
    @GetMapping("/post/pending/count/{author}")
    public ResponseEntity<Integer> countPendingPostByAuthor(@PathVariable("author") String author){
        int count = postServices.countPedingPostsByAuthor(author);
        return new ResponseEntity(count, HttpStatus.OK);
    }

    //get blocked posts, 10 per time
    @GetMapping("/post/blocked/{page}") //
    public ResponseEntity<List<Post>> getBlockedPost(@PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        List<Post> posts = postServices.getBlockedPosts(page);
        if (posts == null) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // update post, used by author
    @PostMapping("/post/update")
    public ResponseEntity<String> updateTextPost(@RequestParam("postId") String id, @RequestParam("content") String content){
        try {
            postServices.updateTextPost(id, content);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update img post, used by author


    //update video post, used by author

    //approval post, used by admin
    @PostMapping("/post/approval/{postId}")
    public ResponseEntity<String> approvalPost(@PathVariable("postId") String id){
        if(postServices.approval(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //block post, used by admin
    @PostMapping("/post/blocked/{postId}")
    public ResponseEntity<String> blockPost(@PathVariable("postId") String id){
        if(postServices.block(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //delete post, used by admin
    @PostMapping("/post/delete/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") String id){
        if(postServices.delete(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //create text post -- done
    @PostMapping("/post/create/text")
    public ResponseEntity<String> createTextPost(@RequestParam("author") String author, @RequestParam("content") String content,
                                                 @RequestParam("title") String title){
        Post post = postServices.createTextPost(author, title, content);
        if(post !=null) return new ResponseEntity<>(post.getId(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //create img post
    @PostMapping("/post/create/img")
    public ResponseEntity<String> createImgPost(@RequestParam("images") MultipartFile[] files, @RequestParam("author") String author,
                                                @RequestParam("content") String content, @RequestParam("title") String title){
        try{
            postServices.createImagePost(files,author, content, title);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException | DbxException e){
            System.out.println(e.getStackTrace().toString());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //create video post
    @PostMapping("/post/create/video")
    public ResponseEntity<String> createVideoPost(@RequestParam("video")MultipartFile file, @RequestParam("author") String author,
                                                @RequestParam("content") String content, @RequestParam("title") String title){
        try{
            postServices.createVideoPost(file,author, content, title);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException | DbxException e){
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
