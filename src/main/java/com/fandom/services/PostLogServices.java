package com.fandom.services;

import com.fandom.model.PostLog;
import com.fandom.model.PostState;
import com.fandom.repository.PostLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PostLogServices {

    private PostLogRepository plr;

    @Autowired
    public PostLogServices(PostLogRepository plr){
        this.plr = plr;
    }

    public void writeLog(String id, PostState state, String note){
        PostLog pl = new PostLog(id, state, note);
        plr.save(pl);
    }

    public Map<String, PostLog> getPostLog(String id, PostState state, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<PostLog> plp = plr.getByPostIdAndState(id, state, pageable);
        Map<String, PostLog> map = new HashMap<>();
        for(PostLog pl: plp.getContent()){
            map.put(pl.getId(), pl);
        }

        return map;
    }
}
