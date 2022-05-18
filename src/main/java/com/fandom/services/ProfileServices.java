package com.fandom.services;

import com.dropbox.core.DbxException;
import com.fandom.model.Media;
import com.fandom.model.Post;
import com.fandom.model.PostState;
import com.fandom.model.Profile;
import com.fandom.model.infomation.Achievement;
import com.fandom.model.infomation.BasicInfo;
import com.fandom.model.infomation.LifeAndCareer;
import com.fandom.model.infomation.Link;
import com.fandom.repository.ProfileRepository;
import com.fandom.util.DropboxUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProfileServices {
    private ProfileRepository profileRepository;

    private String id ="627937235464b60579d666ed";

    @Autowired
    public ProfileServices(ProfileRepository profileRepository){ this.profileRepository = profileRepository;}

/*
    public void addImageProfile(MultipartFile[] files) {

        Optional<Profile> op = profileRepository.findById(id);
        if (op.isPresent()) {
            Profile profile = op.get();
            Map<String, Media> map = profile.getImages();
            ArrayList<MultipartFile> arr = new ArrayList<>(Arrays.asList(files));
            for (int i = 0; i < arr.size(); i++) {
                try {
                    MultipartFile file = arr.get(i);
                    Media m = DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + "");
                    m.setDescription("");
                    map.put(m.getName(), m);
                } catch (DbxException | IOException e) {
                    e.printStackTrace();
                }
                profile.setImages(map);
                profileRepository.save(profile);
            }
        }
    }

    public void updateBasicProfile(BasicInfo basicInfo){
        Optional<Profile> op = profileRepository.findById(id);
        if(op.isPresent()){
            Profile profile = op.get();
            profile.setBasicInfo(basicInfo);
            profileRepository.save(profile);
        }
    }
    public void AddLifeAndCareerProfile(LifeAndCareer lifeAndCareer, int Stt){
        Optional<Profile> op = profileRepository.findById(id);
        if(op.isPresent()){
            Profile profile = op.get();
            List<LifeAndCareer> list = profile.getLifeAndCareers();
            list.add(Stt, lifeAndCareer);
            profile.setLifeAndCareers(list);
            profileRepository.save(profile);
        }
    }*/

    /*
    public void updateLifeAndCareerProfile(List<LifeAndCareer> lifeAndCareers){
        Optional<Profile> op = profileRepository.findById(id);
        if(op.isPresent()){
            Profile profile = op.get();
            profile.setLifeAndCareers(lifeAndCareers);
            profileRepository.save(profile);
        }
    }

    public void updateAchievementProfile(List<Achievement> achievements){
        Optional<Profile> op = profileRepository.findById(id);
        if(op.isPresent()){
            Profile profile = op.get();
            profile.setAchievements(achievements);
            profileRepository.save(profile);
        }
    }*/

    public Profile getProfile(){
        Optional<Profile> op = profileRepository.findById(id);
        return op.orElse(null);
    }

    public Profile getBasicProfile(){
        Profile profile = profileRepository.getBasicProfile(id);

        return profile;
    }

    public void updateBasicInfo(String name, String altName, String dateOfBirth, String placeOfBirth, String occupation,
                                String genres, String spouse, String yearActive, String children){
        Optional<Profile> pc = profileRepository.findById(id);
        if(pc.isPresent()){
            Profile profile = pc.get();
            BasicInfo bi = profile.getBasicInfo();
            bi.setBasicInfo(name, altName, dateOfBirth, placeOfBirth, occupation, genres, yearActive, spouse, children);
            profile.setBasicInfo(bi);
            profileRepository.save(profile);
        }
    }

    public void updateLinks(String official, String facebook, String instagram, String youtube, String twitter, String spotify, String appleMusic){
        Optional<Profile> pc = profileRepository.findById(id);
        if(pc.isPresent()){
            Profile profile = pc.get();
            ArrayList<Link> links = new ArrayList<>();
            Link of = new Link("Official Website", official);
            Link spot = new Link("Spotify", spotify);
            Link am = new Link("Apple Music", appleMusic);
            Link fb = new Link("Facebook", facebook);
            Link ist = new Link("Instagram", instagram);
            Link tw = new Link("Twitter", twitter);
            Link yt = new Link("Youtube", youtube);

            links.add(of);
            links.add(spot);
            links.add(am);
            links.add(fb);
            links.add(ist);
            links.add(tw);
            links.add(yt);

            profile.setLinks(links);

            profileRepository.save(profile);
        }
    }

    public void updateAvatar(MultipartFile file, String des) throws IOException, DbxException {
        Optional<Profile> pc = profileRepository.findById(id);
        if(pc.isPresent()) {
            Profile profile = pc.get();
            Media m = DropboxUtils.uploadFile(file.getInputStream(), System.currentTimeMillis() + "");
            m.setDescription(des);
            BasicInfo bi = profile.getBasicInfo();
            DropboxUtils.deleteFile(bi.getAvatar().getPath());
            bi.setAvatar(m);
            profile.setBasicInfo(bi);

            profileRepository.save(profile);
        }
    }

    public void updateAvatarDes(String des){
        Optional<Profile> pc = profileRepository.findById(id);
        if(pc.isPresent()) {
            Profile profile = pc.get();
            BasicInfo bi = profile.getBasicInfo();
            Media m = bi.getAvatar();
            m.setDescription(des);
            bi.setAvatar(m);
            profile.setBasicInfo(bi);

            profileRepository.save(profile);
        }
    }

    public void updateLifeAndCareers(String lac){
        Optional<Profile> pc = profileRepository.findById(id);
        if(pc.isPresent()) {
            Profile profile = pc.get();
            profile.setLifeAndCareers(lac);
            profileRepository.save(profile);
        }
    }

    public void updateActivities(String act){
        Optional<Profile> pc = profileRepository.findById(id);
        if(pc.isPresent()) {
            Profile profile = pc.get();
            profile.setActivities(act);
            profileRepository.save(profile);
        }
    }
    public void updateAchievement(String ach){
        Optional<Profile> pc = profileRepository.findById(id);
        if(pc.isPresent()) {
            Profile profile = pc.get();
            profile.setAchievements(ach);
            profileRepository.save(profile);
        }
    }

}
