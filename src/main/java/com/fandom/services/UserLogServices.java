package com.fandom.services;

import com.fandom.model.UserLog;
import com.fandom.model.UserState;
import com.fandom.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLogServices {
    private UserLogRepository ulr;

    @Autowired
    public UserLogServices( UserLogRepository ulr){
        this.ulr = ulr;
    }

    public void writeLog(String id, UserState state, String note){
        UserLog pl = new UserLog(id, state, note);
        ulr.save(pl);
    }

    public Map<String, UserLog> getPostLog(String id, UserState state, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<UserLog> plp = ulr.getByUserIdAndState(id, state, pageable);
        Map<String, UserLog> map = new HashMap<>();
        for(UserLog pl: plp.getContent()){
            map.put(pl.getId(), pl);
        }

        return map;
    }
}
