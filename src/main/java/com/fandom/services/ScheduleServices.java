package com.fandom.services;

import com.fandom.model.PostState;
import com.fandom.model.Schedule;
import com.fandom.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ScheduleServices {
    ScheduleRepository sr;

    @Autowired
    public ScheduleServices(ScheduleRepository sr){
        this.sr = sr;
    }

    public Map<String, Schedule> getSchedules(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Schedule> sp = sr.getSchedulesByState(PostState.APPROVED, pageable);

        return listToMap(sp.getContent());
    }

    public Map<String, Schedule> getUpcoming(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Schedule> list = sr.getUpcoming(System.currentTimeMillis(), pageable);
        return listToMap(list.getContent());
    }

    public Map<String, Schedule> getBetween( long start, long end, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Schedule> list = sr.findScheduleByTimestampBetween(start, end, pageable);

        return listToMap(list.getContent());
    }

    public Map<String, Schedule> getFinished(int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Schedule> sp = sr.getFinished(System.currentTimeMillis(), pageable);

        return listToMap(sp.getContent());
    }

    public Map<String, Schedule> getSchedule(){
        Pageable pageable = PageRequest.of(0, 6);
        Page<Schedule> sp = sr.getUpcoming(System.currentTimeMillis(), pageable);

        return listToMap(sp.getContent());
    }


    public void createSchedule(String content, long timestamp, String location){
        Schedule s = new Schedule(timestamp, location, content);

        sr.save(s);
    }

    public void updateSchedule(String id, String content, long timestamp, String location){
        Optional<Schedule> op = sr.findById(id);
        if(op.isPresent()){
            Schedule s = op.get();
            s.setTimestamp(timestamp);
            s.setContent(content);
            s.setLocation(location);
            sr.save(s);
        }
    }

    public void deleteSchedule(String id) {
        Optional<Schedule> op = sr.findById(id);
        if(op.isPresent()){
            Schedule s = op.get();
            s.setState(PostState.DELETED);
            sr.save(s);
        }
    }

    private Map<String, Schedule> listToMap(List<Schedule> list){
        Map<String, Schedule> map = new LinkedHashMap<>();
        for(Schedule s: list){
            map.put(s.getId(), s);
        }
        return map;
    }
}
