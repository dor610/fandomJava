package com.fandom.services;

import com.fandom.model.Interaction;
import com.fandom.model.InteractionType;
import com.fandom.repository.InterationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteractionServices {

    private InterationRepository interationRepository;

    @Autowired
    public InteractionServices(InterationRepository interationRepository){
        this.interationRepository =interationRepository;
    }

    public int getUpvoteCount(String targetId){
        int count = interationRepository.countInteractionByTargetIdAndType(targetId, InteractionType.UPVOTE);
        return count;
    }

    public int getDownvoteCount(String targetId){
        int count = interationRepository.countInteractionByTargetIdAndType(targetId, InteractionType.DOWNVOTE);
        return count;
    }

    public Interaction getUserInteraction(String account, String targetId){
        Interaction interaction = interationRepository.findInteractionByAccountAndTargetId(account, targetId);
        return interaction;
    }

    public void addUpvoteInteraction(String account, String targetId){
        Interaction i = interationRepository.findInteractionByAccountAndTargetId(account, targetId);
        if(i == null){
            Interaction interaction = new Interaction(account, targetId, InteractionType.UPVOTE);
            interationRepository.save(interaction);
        }else {
            if(i.getType().equals(InteractionType.UPVOTE)){
                interationRepository.delete(i);
            }else if(i.getType().equals(InteractionType.DOWNVOTE)){
                i.setType(InteractionType.UPVOTE);
                interationRepository.save(i);
            }
        }

    }

    public void addDownvoteInteraction(String account, String targetId){
        Interaction i = interationRepository.findInteractionByAccountAndTargetId(account, targetId);
        if(i == null){
            Interaction interaction = new Interaction(account, targetId, InteractionType.DOWNVOTE);
            interationRepository.save(interaction);
        }else {
            if(i.getType().equals(InteractionType.DOWNVOTE)){
                interationRepository.delete(i);
            }else if(i.getType().equals(InteractionType.UPVOTE)){
                i.setType(InteractionType.DOWNVOTE);
                interationRepository.save(i);
            }
        }
    }

    public void removeInteraction(String account, String targetId){
        interationRepository.deleteInteractionByAccountAndTargetId(account, targetId);
    }

    public void deleteAllInteraction(String account, String targetId){
        interationRepository.deleteInteractionsByAccountAndTargetId(account, targetId);
    }
}
