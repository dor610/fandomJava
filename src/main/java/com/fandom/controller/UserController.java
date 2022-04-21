package com.fandom.controller;

import com.fandom.model.User;
import com.fandom.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    private UserServices userServices;

    @Autowired
    public UserController(UserServices userServices){
        this.userServices = userServices;
    }

    //create new user //
    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(@RequestParam("account") String account, @RequestParam("name") String name,
                              @RequestParam("password") String password,
                              @RequestParam("dob") String dob,
                              @RequestParam("email") String email){
         try{
             userServices.createUser(account, name, password, dob, email);
             return new ResponseEntity<>(HttpStatus.OK);
         }catch (Exception e){
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

    //Get user info by account //
    @GetMapping("/user/{account}")
    public ResponseEntity<User> getUserInfo(@PathVariable("account") String account) throws Exception{
        try{
            User user = userServices.getUserInfo(account);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/isExist/{account}") //
    public ResponseEntity<Boolean> isExist(@PathVariable("account") String account){
        boolean ie = userServices.isExist(account);
        return new ResponseEntity<>(ie, HttpStatus.OK);
    }

    @GetMapping("/user/basic/{account}") //
    public  ResponseEntity<Map<String, String>> getUserBasicInfo(@PathVariable("account") String account){
        Map<String, String> map = userServices.getUserBasicInfo(account);
        if(map != null) return new ResponseEntity<>(map, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    //delete a user by account, admin right is required
    @DeleteMapping("/deleteAccount")
    public boolean deleteAccount(@RequestParam("account") String account) throws Exception {
        return userServices.deleteAccount(account);
    }

}
