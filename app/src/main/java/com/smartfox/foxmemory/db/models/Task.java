package com.smartfox.foxmemory.db.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by SmartFox on 06.03.2018.
 */

public class Task extends RealmObject {

    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRIORITY = "priority";
    public static final String CREATED_AT = "createdAt";

    @Required
    private String name;
    private String description;
    private int priority;
    private Date createdAt = new Date();

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
