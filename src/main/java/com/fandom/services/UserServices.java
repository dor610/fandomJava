package com.fandom.services;

import com.fandom.model.Post;
import com.fandom.model.Role;
import com.fandom.model.User;
import com.fandom.model.UserState;
import com.fandom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServices {

    private UserRepository userRepository;

    private UserLogServices uls;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServices(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                        UserLogServices uls){
        this.uls = uls;
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    //Create new account
    public void createUser(String account, String name, String password, String dob, String email) throws Exception {
        if(userRepository.findUserByAccount(account) == null){
            User user = new User(account, name, passwordEncoder.encode(password), dob, email);
            userRepository.insert(user);
            uls.writeLog(user.getId(), UserState.CREATED, "Tạo mới");
        }else {
            throw new Exception(account + " already exists");
        }
    }

    //Get user info by account
    public User getUserInfo(String account) throws Exception {
        User user = userRepository.findUserByAccount(account);
        if(user != null) return user;
        else throw new Exception("User does not exist!");
    }

    public Map<String,User> getUserByState( UserState state, int page){
        Pageable pageable = PageRequest.of(page, 10);
        Page<User> userPage = userRepository.findUserbyStatus(state, pageable);
        Map<String, User> map = new LinkedHashMap<>();
        if(userPage.getContent().size() > 0){
            for(User u: userPage.getContent()){
                map.put(u.getId(), u);
            }
        }
        return map;
    }

    public Map<String, String> getUserBasicInfo(String account){
        User user = userRepository.findUserByAccount(account);
        if(user != null){
            Map<String, String> map = new LinkedHashMap<>();
            map.put("account", user.getAccount());
            map.put("userName", user.getUserName());
            map.put("email", user.getEmail());
            map.put("role", user.getRole().name());
            map.put("avatar", user.getAvatar());
            map.put("dateOfBirth", user.getDateOfBirth());
            return map;
        }
        return null;
    }

    public boolean isExist(String account){
        User user = userRepository.findUserByAccount(account);
        return user != null;
    }


    //Khoá tài khoản
    public boolean ban(String account, String note){
        User user = userRepository.findUserByAccount(account);
        if(user != null){
            user.setStatus(UserState.BANNED);
            uls.writeLog(user.getId(), UserState.BANNED, note);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    //mở khoá tài khoản
    public boolean unBan(String account){
        User user = userRepository.findUserByAccount(account);
        if(user != null){
            user.setStatus(UserState.ACTIVE);
            uls.writeLog(user.getId(), UserState.UNBANNED, "");
        }
        return false;
    }

    //Delete an account
    public boolean deleteAccount(String account, String note) throws Exception {
        User user = userRepository.findUserByAccount(account);
        if(user != null){
            user.setStatus(UserState.DELETED);
            uls.writeLog(user.getId(), UserState.DELETED, note);
            userRepository.save(user);
            return true;
        }else throw new Exception(account + " does not exist");
    }


}
