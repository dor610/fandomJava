package com.fandom.controller;

import com.fandom.model.Schedule;
import com.fandom.services.ScheduleServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ScheduleController {
    ScheduleServices ss;

    @Autowired
    public ScheduleController(ScheduleServices ss){
        this.ss = ss;
    }

    @GetMapping("/schedule/get/{page}")
    public ResponseEntity<Map<String, Schedule>> getSchedules(@PathVariable("page") String pageStr){
        Map<String, Schedule> map = ss.getSchedules(Integer.parseInt(pageStr));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/schedule/get/upcoming")
    public ResponseEntity<Map<String, Schedule>> getUpcoming(){
        Map<String, Schedule> map = ss.getUpcoming();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/schedule/firstload")
    public ResponseEntity<Map<String, Schedule>> getSchedule(){
        Map<String, Schedule> map = ss.getSchedule();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/schedule/create")
    public ResponseEntity<String> createSchedule(@RequestParam("timestamp") String timestamp, @RequestParam("location") String location,
                                                 @RequestParam("content") String content){
        ss.createSchedule(content, Long.parseLong(timestamp), location);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/schedule/update")
    public ResponseEntity<String> updateShedule(@RequestParam("id") String id, @RequestParam("timestamp") String timestamp,
                                                @RequestParam("location") String location, @RequestParam("content") String content){
        ss.updateSchedule(id, content, Long.parseLong(timestamp), location);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/schedule/delete")
    public ResponseEntity<String> deleteSchedule(@RequestParam("id") String id){
        ss.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
