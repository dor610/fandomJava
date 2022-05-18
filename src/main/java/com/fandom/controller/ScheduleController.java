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

    //get tất cả schedule - không dùng nữa
    @GetMapping("/schedule/get/{page}")
    public ResponseEntity<Map<String, Schedule>> getSchedules(@PathVariable("page") String pageStr){
        Map<String, Schedule> map = ss.getSchedules(Integer.parseInt(pageStr));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //get các schedule chưa xảy ra
    @GetMapping("/schedule/get/upcoming/{page}")
    public ResponseEntity<Map<String, Schedule>> getUpcoming(@PathVariable("page") String pageStr){
        Map<String, Schedule> map = ss.getUpcoming(Integer.parseInt(pageStr));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //get các schedule sau mốc thời gian
    @GetMapping("/schedule/get/before/{timestamp}/{page}")
    public ResponseEntity<Map<String, Schedule>> getBefore(@PathVariable("timestamp") String timestamp, @PathVariable("page") String page){
        Map<String, Schedule> map = ss.getBefore(Long.parseLong(timestamp), Integer.parseInt(page));

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //get các schedule trước mốc thời gian
    @GetMapping("/schedule/get/after/{timestamp}/{page}")
    public ResponseEntity<Map<String, Schedule>> getAfter(@PathVariable("timestamp") String timestamp, @PathVariable("page") String page){
        Map<String, Schedule> map = ss.getAfter(Long.parseLong(timestamp), Integer.parseInt(page));

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //get giữa 2 mốc thời gian millisecond
    @GetMapping("/schedule/get/filter/{start}/{end}/{page}")
    public ResponseEntity<Map<String, Schedule>> getBetween(@PathVariable("start") String start,
                                                            @PathVariable("end") String end,
                                                            @PathVariable("page") String page){
        Map<String, Schedule> map = ss.getBetween(Long.parseLong(start), Long.parseLong(end), Integer.parseInt(page));

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //get các schedule đã xảy ra
    @GetMapping("schedule/get/finished/{page}")
    public ResponseEntity<Map<String, Schedule>> getFiniished(@PathVariable("page") String pageStr){
        Map<String, Schedule> map = ss.getFinished(Integer.parseInt(pageStr));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    //get schedule cho trang chủ
    @GetMapping("/schedule/firstload")
    public ResponseEntity<Map<String, Schedule>> getSchedule(){
        Map<String, Schedule> map = ss.getSchedule();
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    // tạo schedule
    @PostMapping("/schedule/create")
    public ResponseEntity<String> createSchedule(@RequestParam("timestamp") String timestamp, @RequestParam("location") String location,
                                                 @RequestParam("content") String content){
        ss.createSchedule(content, Long.parseLong(timestamp), location);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // cập nhật schedule
    @PostMapping("/schedule/update")
    public ResponseEntity<String> updateShedule(@RequestParam("id") String id, @RequestParam("timestamp") String timestamp,
                                                @RequestParam("location") String location, @RequestParam("content") String content){
        ss.updateSchedule(id, content, Long.parseLong(timestamp), location);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // xoá schedule
    @PostMapping("/schedule/delete")
    public ResponseEntity<String> deleteSchedule(@RequestParam("id") String id){
        ss.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
