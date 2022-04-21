package com.fandom.controller;

import com.fandom.model.Interaction;
import com.fandom.services.InteractionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class InteractionController {
    private InteractionServices interactionServices;

    @Autowired
    public InteractionController(InteractionServices interactionServices){
        this.interactionServices = interactionServices;
    }

    @GetMapping("/interaction/user/{account}/{targetId}")
    public ResponseEntity<Interaction> getUserInteraction(@PathVariable("account") String account, @PathVariable("targetId") String targetId){
        Interaction interaction = interactionServices.getUserInteraction(account, targetId);
        if(interaction == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(interaction, HttpStatus.OK);
    }

    @DeleteMapping("/interaction/user/{account}/{targetId}")
    public void removeInteraction(@PathVariable("account") String account, @PathVariable("targetId") String targetId){
        interactionServices.removeInteraction(account, targetId);
    }

    @PostMapping("/interaction/upvote")
    public ResponseEntity<String> upvote(@RequestParam("account") String account, @RequestParam("targetId") String targetId){
        interactionServices.addUpvoteInteraction(account, targetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/interaction/upvote/{targetId}")
    public ResponseEntity<String> getUpvoteCount(@PathVariable("targetId") String targetId){
        int count = interactionServices.getUpvoteCount(targetId);
        return new ResponseEntity<>(count+"", HttpStatus.OK);
    }
    @GetMapping("/interaction/downvote/{targetId}")
    public ResponseEntity<String> getdownvoteCount(@PathVariable("targetId") String targetId){
        int count = interactionServices.getDownvoteCount(targetId);
        return new ResponseEntity<>(count+"", HttpStatus.OK);
    }

    @GetMapping("/interaction/count/{targetId}")
    public ResponseEntity<Map<String, Integer>> getCount(@PathVariable("targetId") String targetId){
        int upvoteCount = interactionServices.getUpvoteCount(targetId);
        int downvoteCount = interactionServices.getDownvoteCount(targetId);
        Map<String, Integer> map = new HashMap<>();
        map.put("upvote", upvoteCount);
        map.put("downvote", downvoteCount);

        return new ResponseEntity(map, HttpStatus.OK);
    }

    @PostMapping("/interaction/downvote")
    public ResponseEntity<String> downvote(@RequestParam("account") String account, @RequestParam("targetId") String targetId){
        interactionServices.addDownvoteInteraction(account, targetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
