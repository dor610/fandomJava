package com.fandom.repository;

import com.fandom.model.Interaction;
import com.fandom.model.InteractionType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterationRepository extends MongoRepository<Interaction, String> {

   // @Query(value = "{'targetId': ?0, 'type': ?1}", count = true)
    //public int countInteractionByTargetId(String targetId, InteractionType type);

    public int countInteractionByTargetIdAndType(String targetId, InteractionType type);

    public Interaction findInteractionByAccountAndTargetId(String account, String targetId);

    public long deleteInteractionByAccountAndTargetIdAndType(String account, String targetId, InteractionType type);

    public void deleteInteractionByAccountAndTargetId(String account, String targetId);

    public void deleteInteractionsByAccountAndTargetId(String account, String targetId);

    public boolean deleteInteractionByTargetId(String targetId);

    public Interaction findInteractionByAccountAndTargetIdAndType(String account, String targetId, InteractionType type);

}
