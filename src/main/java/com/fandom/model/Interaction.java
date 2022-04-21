package com.fandom.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "interaction")
public class Interaction {
    @Id
    private String id;

    @Indexed(unique = true)
    private String account;
    private String targetId;
    private InteractionType type;

    public Interaction(){}

    public Interaction(String id, String account, String targetId, InteractionType type) {
        this.id = id;
        this.account = account;
        this.targetId = targetId;
        this.type = type;
    }

    public Interaction(String account, String targetId, InteractionType type) {
        this.account = account;
        this.targetId = targetId;
        this.type = type;
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

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public InteractionType getType() {
        return type;
    }

    public void setType(InteractionType type) {
        this.type = type;
    }
}
