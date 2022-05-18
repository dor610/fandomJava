package com.fandom.controller;

import com.dropbox.core.DbxException;
import com.fandom.model.Post;
import com.fandom.model.PostLog;
import com.fandom.model.PostState;
import com.fandom.model.PostType;
import com.fandom.services.PostLogServices;
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
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class PostController {

    private PostServices postServices;
    private PostLogServices pls;


    @Autowired
    public PostController(PostServices postServices, PostLogServices pls) {
        this.pls = pls;
        this.postServices = postServices;
    }

    //get posts, 10 per time
    @GetMapping("/post/{page}") //
    public ResponseEntity<Map<String, Post>> getPosts(@PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String,Post> posts = postServices.getPosts(page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
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
    public ResponseEntity<Map<String,Post>> getPostByAuthor(@PathVariable("author") String author, @PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String,Post> posts = postServices.getPostsByAuthor(author, page);
        if (posts.size() == 0) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post/count/pending")
    public ResponseEntity<Integer> countPendingPost() {
        int count = postServices.countPostByState(PostState.PENDING);
        return  new ResponseEntity<>(count, HttpStatus.OK);
    }
    @GetMapping("/post/count/locked")
    public ResponseEntity<Integer> countLockedPost() {
        int count = postServices.countPostByState(PostState.LOCKED);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    //count pending post by author
    @GetMapping("/post/pending/count/{author}")
    public ResponseEntity<Integer> countPendingPostByAuthor(@PathVariable("author") String author){
        int count = postServices.countPostsByAuthorAndState(author, PostState.PENDING);
        return new ResponseEntity(count, HttpStatus.OK);
    }

    @GetMapping("/post/deleted/count/{author}")
    public ResponseEntity<Integer> countDeletedPostByAuthor(@PathVariable("author") String author){
        int count = postServices.countPostsByAuthorAndState(author, PostState.DELETED);
        return new ResponseEntity(count, HttpStatus.OK);
    }

    @GetMapping("/post/deleted/count/all/{account}")
    public ResponseEntity<Integer> countAllDeletedPostByAuthor(@PathVariable("account") String account){
        int count = postServices.countAllDeletedPostByAccount(account);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/post/approved/count/{author}")
    public ResponseEntity<Integer> countApprovedPostByAuthor(@PathVariable("author") String author){
        int count = postServices.countPostsByAuthorAndState(author, PostState.APPROVED);
        return new ResponseEntity(count, HttpStatus.OK);
    }

    @GetMapping("/post/approved/count/all/{account}")
    public ResponseEntity<Integer> countAllApprovedPostByAuthor(@PathVariable("account") String account){
        int count = postServices.countAllApprovedPostByAccount(account);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/post/locked/count/{author}")
    public ResponseEntity<Integer> countLockedPostByAuthor(@PathVariable("author") String author){
        int count = postServices.countPostsByAuthorAndState(author, PostState.LOCKED);
        return new ResponseEntity(count, HttpStatus.OK);
    }

    @GetMapping("/post/locked/count/all/{account}")
    public  ResponseEntity<Integer> countAllLockedPost(@PathVariable("account") String account){
        int count = postServices.countAllLockedPostByAccount(account);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping("/post/count/all/{account}")
    public ResponseEntity<Integer> countAllPost(@PathVariable("account") String account){
        int count = postServices.countPostByAuthor(account);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }


    //get pending posts, 10 per time //
    @GetMapping("/post/pending/{page}")
    public ResponseEntity<Map<String, Post>> getPendingPost(@PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String, Post> posts = postServices.getPendingPosts(page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //get locked posts, 10 per time
    @GetMapping("/post/locked/{page}") //
    public ResponseEntity<Map<String,Post>> getLockedPost(@PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String, Post> posts = postServices.getLockedPosts(page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/post/approved/{page}")
    public ResponseEntity<Map<String, Post>> getApprovedPost(@PathVariable("page") String page){
        Map<String, Post> posts = postServices.getApprovedPosts(Integer.parseInt(page));
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/post/removed/{page}")
    public ResponseEntity<Map<String, Post>> getRemovedPost(@PathVariable("page") String page){
        Map<String,Post> posts = postServices.getRemovedPosts(Integer.parseInt(page));
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping("/post/user/{account}/pending/{page}")
    public ResponseEntity<Map<String, Post>> getPendingpostByUser(@PathVariable("account") String account, @PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String, Post> posts = postServices.getPostByAuthorAndState(account, PostState.PENDING, page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/post/user/{account}/approved/{page}")
    public ResponseEntity<Map<String, Post>> getApprovedpostByUser(@PathVariable("account") String account, @PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String, Post> posts = postServices.getPostByAuthorAndState(account, PostState.APPROVED, page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/post/user/{account}/locked/{page}")
    public ResponseEntity<Map<String, Post>> getLockedpostByUser(@PathVariable("account") String account, @PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String, Post> posts = postServices.getPostByAuthorAndState(account, PostState.LOCKED, page);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/post/user/{account}/deleted/{page}")
    public ResponseEntity<Map<String, Post>> getDeletedpostByUser(@PathVariable("account") String account, @PathVariable("page") String pageStr){
        int page = Integer.parseInt(pageStr);
        Map<String, Post> posts = postServices.getPostByAuthorAndState(account, PostState.DELETED, page);
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

    //approval post, used by admin //
    @PostMapping("/post/approval/{postId}")
    public ResponseEntity<String> approvalPost(@PathVariable("postId") String id){
        if(postServices.approval(id)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //lock post, used by admin
    @PostMapping("/post/locked")
    public ResponseEntity<String> lockPost(@RequestParam("postId") String id, @RequestParam("note") String note){
        System.out.println("hihihi");
        if(postServices.lockPost(id, note)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>("Bị lỗi gì đó",HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //delete post, used by admin
    @PostMapping("/post/delete")
    public ResponseEntity<String> deletePost(@RequestParam("postId") String id, @RequestParam("note") String note){
        if(postServices.delete(id, note)) return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //unlock post, used by admin
    @PostMapping("/post/unlock/{postId}")
    public ResponseEntity<String> unlockPost(@PathVariable("postId") String id){
        if(postServices.unlock(id)) return  new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<String> createImgPost(@RequestParam("images") MultipartFile[] images, @RequestParam("imgDesciption") String[] descriptions,
                                                @RequestParam("author") String author, @RequestParam("content") String content, @RequestParam("title") String title){
        try{
            postServices.createImagePost(images, descriptions, author, content, title);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException | DbxException e){
            System.out.println(e.getStackTrace().toString());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //create video post
    @PostMapping("/post/create/video")
    public ResponseEntity<String> createVideoPost(@RequestParam("video")MultipartFile video, @RequestParam("videoDescription") String description,
                                                  @RequestParam("author") String author, @RequestParam("content") String content,
                                                  @RequestParam("title") String title){
        try{
            postServices.createVideoPost(video, description,author, content, title);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException | DbxException e){
            System.out.println(e.getStackTrace());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //create img and video post
    @PostMapping("/post/create/img_video")
    public ResponseEntity<String> createImgAndVIdeoPost(@RequestParam("images") MultipartFile[] images, @RequestParam("imgDesciption") String[] imgDescriptions,
                                                        @RequestParam("video")MultipartFile video, @RequestParam("videoDescription") String description,
                                                        @RequestParam("author") String author, @RequestParam("content") String content, @RequestParam("title") String title){
        try {
            postServices.createImageAndVideoPost(images,imgDescriptions,video, description, author, content, title);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (IOException | DbxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
