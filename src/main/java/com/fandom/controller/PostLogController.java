package com.fandom.controller;

import com.fandom.model.PostLog;
import com.fandom.model.PostState;
import com.fandom.services.PostLogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class PostLogController {
    private PostLogServices pls;

    @Autowired
    public PostLogController(PostLogServices pls){
        this.pls = pls;
    }

    @GetMapping("/post/log/{postId}")
    public ResponseEntity<Map<String, PostLog>> getPostLog(@PathVariable("postId") String postId){
        Map<String, PostLog> map = pls.getPostLogByPostId(postId);
        if(map.size() > 0) return  new ResponseEntity<>(map, HttpStatus.OK);
        return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/post/log/upcoming")
    public ResponseEntity<Map<String, PostLog>> getUpcoming(){
        Map<String, PostLog> map = pls.getUpcoming();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/post/log/timestamp/{postId}/{state}")
    public ResponseEntity<String> getLatestTimestamp(@PathVariable("postId") String postId, @PathVariable("state") String state){
        PostLog pl = pls.getLatestTimestampByPostIdAndState(postId, PostState.valueOf(state));
        if(pl != null) return new ResponseEntity<>(pl.getTimestamp()+"", HttpStatus.OK);
        return new ResponseEntity<>("Lỗi gì đó" ,HttpStatus.BAD_REQUEST);
    }
}
