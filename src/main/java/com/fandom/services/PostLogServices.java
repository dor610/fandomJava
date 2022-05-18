package com.fandom.services;

import com.fandom.model.PostLog;
import com.fandom.model.PostState;
import com.fandom.repository.PostLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
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

    public Map<String, PostLog> getPostLogByPostIdAndState(String id, PostState state){
        List<PostLog> plp = plr.getByPostIdAndState(id, state);
        Map<String, PostLog> map = new LinkedHashMap<>();
        for(PostLog pl: plp){
            map.put(pl.getId(), pl);
        }

        return map;
    }

    public Map<String, PostLog> getPostLogByPostId(String id){
        List<PostLog> list = plr.getByPostId(id);
        Map<String, PostLog> map = new LinkedHashMap<>();
        for(PostLog p: list){
            map.put(p.getId(), p);
        }

        return  map;
    }

    public Map<String, PostLog> getUpcoming(){
        List<PostLog> list = plr.getUpcoming(System.currentTimeMillis());
        Map<String, PostLog> map = new LinkedHashMap<>();
        for(PostLog p: list){
            map.put(p.getId(), p);
        }
        return  map;
    }

    public PostLog getLatestTimestampByPostIdAndState(String postId, PostState state){
        System.out.println(postId + " --------------------------- " + state);
        List<PostLog> pl = plr.getLatestTimestampByPostIdAndState(postId, state);
        if(pl.size() > 0) return pl.get(0);
        return null;
    }

    public int countPostLogByStateAndPostId(PostState state, String postId){

        int count = plr.countByPostIdAndState(postId, state);

        return count;
    }
}
