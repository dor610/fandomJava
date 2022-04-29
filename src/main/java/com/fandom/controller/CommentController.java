package com.fandom.controller;

import com.fandom.model.Comment;
import com.fandom.services.CommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class CommentController {

    private CommentServices commentServices;

    @Autowired
    public CommentController(CommentServices commentServices){
        this.commentServices = commentServices;
    }


    //get comment count by the id of target
    @GetMapping("/comment/count/{targetId}")
    public ResponseEntity<String> count(@PathVariable("targetId") String targetId){
        int count = commentServices.countComment(targetId);
        return new ResponseEntity<>(count+ "", HttpStatus.OK);
    }

    @GetMapping("/comment/get/{targetId}/{page}")
    public ResponseEntity<Map<String,Comment>> getComments(@PathVariable("targetId") String targetId, @PathVariable("page") String page){
        Map<String,Comment> map = commentServices.getComments(targetId, Integer.parseInt(page));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/comment/post/text")
    public ResponseEntity<String> createTextComment(@RequestParam("author") String author, @RequestParam("targetId") String targetId,
                                                    @RequestParam("content") String content) {
        commentServices.addTextComment(author, targetId, content);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/post/img")
    public ResponseEntity<String> createImgComment(@RequestParam("images")MultipartFile[] images, @RequestParam("author") String author,
                                                   @RequestParam("targetId") String targetId, @RequestParam("content") String content){
        commentServices.addImageComment(author, targetId, content, images);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/comment/delete")
    public ResponseEntity<String> deleteComment(@RequestParam("id") String id){
        try {
            commentServices.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
