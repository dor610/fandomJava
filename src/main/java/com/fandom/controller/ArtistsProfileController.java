package com.fandom.controller;

import com.dropbox.core.DbxException;
import com.fandom.model.Profile;
import com.fandom.model.infomation.Achievement;
import com.fandom.model.infomation.BasicInfo;
import com.fandom.model.infomation.LifeAndCareer;
import com.fandom.services.ProfileServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ArtistsProfileController {
    private ProfileServices  profileServices;


    @Autowired
    public ArtistsProfileController(ProfileServices  profileServices) {
        this.profileServices = profileServices;
    }

    @PostMapping("/profile/update/basicInfo")
    public ResponseEntity<String> updateBasicInfo(@RequestParam("name") String name, @RequestParam("altName") String altName,
                                                  @RequestParam("dateOfBirth") String dateOfBirth, @RequestParam("placeOfBirth") String placeOfBirth,
                                                  @RequestParam("occupation") String occupation, @RequestParam("genres") String genres,
                                                  @RequestParam("spouse") String spouse, @RequestParam("yearActive") String yearActive,
                                                  @RequestParam("children") String children){
        profileServices.updateBasicInfo(name, altName, dateOfBirth, placeOfBirth, occupation, genres, spouse, yearActive, children);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/update/avatar")
    public ResponseEntity<String> updateAvatar(@RequestParam("avatar") MultipartFile avatar, @RequestParam("des") String des){
        try {
            profileServices.updateAvatar(avatar, des);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException | DbxException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/update/link")
    public ResponseEntity<String> updateLinks(@RequestParam("official") String officail, @RequestParam("facebook") String facebook,
                                              @RequestParam("instagram") String instagram, @RequestParam("youtube") String youtube,
                                              @RequestParam("twitter") String twitter, @RequestParam("spotify") String spotify,
                                              @RequestParam("appleMusic") String appleMusic){
        profileServices.updateLinks(officail, facebook, instagram, youtube, twitter, spotify, appleMusic);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/update/avatar/des")
    public ResponseEntity<String> updateAvatarDescription(@RequestParam("des") String des){

        profileServices.updateAvatarDes(des);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/update/lifeAndCareer")
    public ResponseEntity<String> updateLifeAndCareer(@RequestParam("lifeAndCareers") String lac){
        profileServices.updateLifeAndCareers(lac);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/update/activities")
    public ResponseEntity<String> updateActivities(@RequestParam("activities") String act){
        profileServices.updateActivities(act);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/update/achievement")
    public ResponseEntity<String> updateAchievement(@RequestParam("achievement") String ach){
        profileServices.updateAchievement(ach);

        return new ResponseEntity<>(HttpStatus.OK);
    }
/*
    //create img Profile
    @PostMapping("/profile/create/img")
    public ResponseEntity<String> createImgComment(@RequestParam("images")MultipartFile[] images){
        profileServices.addImageProfile(images);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/update/lifeandcareer")
    public ResponseEntity<String> updateBasicProfile(@RequestParam("basicInfo") BasicInfo basicInfo){
        try {
            profileServices.updateBasicProfile(basicInfo);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/add/lifeandcareer")
    public ResponseEntity<String> createLifeAndCareerProfile(@RequestParam("title") String title, @RequestParam("content") String content,
                                                             @RequestParam("imges") MultipartFile[] images,@RequestParam("Stt")int Stt){
        profileServices.AddLifeAndCareerProfile(lifeAndCareer, Stt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/profile/update/lifeandcareer")
    public ResponseEntity<String> updateLifeAndCareerProfile(@RequestParam("lifeAndCareer") List<LifeAndCareer> lifeAndCareers){
        try {
            profileServices.updateLifeAndCareerProfile(lifeAndCareers);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/profile/update/achievement")
    public ResponseEntity<String> updateAchievementProfile(@RequestParam("achievement") List<Achievement> achievement){
        try {
            profileServices.updateAchievementProfile(achievement);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/
    @GetMapping("/profile/get")
    public ResponseEntity<Profile> getProfile(){
        Profile profile = profileServices.getProfile();
        return new ResponseEntity(profile, HttpStatus.OK);
    }

    @GetMapping("/profile/get/basic")
    public ResponseEntity<Profile> getBasicProfile(){
        Profile profile = profileServices.getBasicProfile();
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
