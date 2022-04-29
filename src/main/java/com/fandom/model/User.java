package com.fandom.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

@Document(collection = "user")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String account;
    private String userName;
    private String password;
    private String dateOfBirth;
    private String email;
    private long createdDate;
    private Role role;
    private UserState status;
    private String avatar;
    private ArrayList<String> recentChat;

    public User(){}

    public User(String id, String account, String userName, String password, long createdDate, String dateOfBirth, String email, Role role, UserState status, String avatar,
                ArrayList<String> recentChat) {
        this.id = id;
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.createdDate = createdDate;
        this.email = email;
        this.role = role;
        this.status = status;
        this.avatar = avatar;
        this.recentChat = recentChat;
    }

    public User(String account, String userName, String password, String dateOfBirth, String email){
        ObjectId id = new ObjectId();
        this.id = "user_"+id.toString();
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.role = Role.MEMBER;
        this.createdDate = System.currentTimeMillis();
        this.status = UserState.ACTIVE;
        this.avatar = "https://www.dropbox.com/s/wm9xcmn2z82yydi/avatar.png?raw=1";
        this.recentChat = new ArrayList<>();
    }

    public User(String account, String userName, String password, String dateOfBirth, String email, Role role, String avatar) {
        this.account = account;
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.role = role;
        this.avatar = avatar;
    }

    public void addRecentChat(String account){
        if(recentChat.size() < 7){
            recentChat.add(account);
        }else {
            recentChat.remove(0);
            recentChat.add(account);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getRecentChat(){
        return this.recentChat;
    }

    public void setRecentChat(ArrayList<String> recentChat){
        this.recentChat = recentChat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserState getStatus() {
        return status;
    }

    public void setStatus(UserState status) {
        this.status = status;
    }
}
