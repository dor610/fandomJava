package com.fandom.services;

import com.fandom.model.UserLog;
import com.fandom.model.UserState;
import com.fandom.repository.UserLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
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

    public UserLog getLastState(String account, UserState state){
        List<UserLog> list = ulr.getByUserIdAndState(account, state);
        if(list.size() >0) return  list.get(0);
        return null;
    }

    public Map<String, UserLog> getPostLog(String id, UserState state){
        List<UserLog> plp = ulr.getByUserIdAndState(id, state);
        Map<String, UserLog> map = new LinkedHashMap<>();
        for(UserLog pl: plp){
            map.put(pl.getId(), pl);
        }

        return map;
    }
}
