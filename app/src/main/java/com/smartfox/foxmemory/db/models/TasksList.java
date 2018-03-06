package com.smartfox.foxmemory.db.models;

import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by SmartFox on 01.03.2018.
 */

public class TasksList extends RealmObject {

    @PrimaryKey
    private String  id = UUID.randomUUID().toString();

    private RealmList<Task> tasks;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RealmList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(RealmList<Task> tasks) {
        this.tasks = tasks;
    }



}
