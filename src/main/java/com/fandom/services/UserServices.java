package com.fandom.services;

import com.fandom.model.Role;
import com.fandom.model.User;
import com.fandom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserServices {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServices(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    //Create new account
    public void createUser(String account, String name, String password, String dob, String email) throws Exception {
        if(userRepository.findUserByAccount(account) == null){
            User user = new User(account, name, passwordEncoder.encode(password), dob, email);
            userRepository.insert(user);
        }else {
            throw new Exception(account + " already exist");
        }
    }

    //Get user info by account
    public User getUserInfo(String account) throws Exception {
        User user = userRepository.findUserByAccount(account);
        if(user != null) return user;
        else throw new Exception("User does not exist!");
    }

    public Map<String, String> getUserBasicInfo(String account){
        User user = userRepository.findUserByAccount(account);
        if(user != null){
            Map<String, String> map = new HashMap<>();
            map.put("account", user.getAccount());
            map.put("userName", user.getUserName());
            map.put("email", user.getEmail());
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


    //Delete an account
    public boolean deleteAccount(String account) throws Exception {
        if(userRepository.findUserByAccount(account) != null){
            return true;
        }else throw new Exception(account + " does not exist");
    }


}
