package com.smartfox.foxmemory.db.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by SmartFox on 12.03.2018.
 */

public class TaskList extends RealmObject {

    public static final String ID = "id";
    public static final String LIST = "list";
    public static final String CREATED_AT = "createdAt";
    public static final String NAME = "name";

    @PrimaryKey
    private String id;
    private String name;
    private Long createdAt;
    private RealmList<Task> list;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public RealmList<Task> getList() {
        return list;
    }

    public void setList(RealmList<Task> list) {
        this.list = list;
    }
}
