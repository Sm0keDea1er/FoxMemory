package com.smartfox.foxmemory.db;

import com.smartfox.foxmemory.db.models.Task;
import com.smartfox.foxmemory.db.models.TaskList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;


/**
 * Created by SmartFox on 06.03.2018.
 */

public class DbService {


    public static void save(Realm realm) {

        realm.executeTransaction(realm1 -> {
            Task task = realm1.createObject(Task.class, UUID.randomUUID().toString());
            task.setName("Name");
            task.setDescription("Description example");
            Random ran = new Random();
            task.setPriority(ran.nextInt(9) + 1);
            task.setCreatedAt(System.currentTimeMillis());
            onlyOneList(realm).add(task);
        });
    }

    public static List<Task> getListTasks(Realm realm) {

        List<Task> list = new ArrayList<>();
        RealmList<Task> results = onlyOneList(realm);
        list.addAll(realm.copyFromRealm(results));
        return list;
    }

    public static RealmList<Task> onlyOneList(Realm realm) {

        TaskList result = realm.where(TaskList.class).equalTo(TaskList.NAME, "name example").findFirst();
        if (result == null) {
            createListTask(realm);
        }
        return realm.where(TaskList.class).equalTo(TaskList.NAME, "name example").findFirst().getList();
    }

    public static void createListTask(Realm realm) {

        realm.executeTransaction((Realm realm1) -> {
            TaskList taskList = realm.createObject(TaskList.class, UUID.randomUUID().toString());
            taskList.setName("name example");
            taskList.setCreatedAt(System.currentTimeMillis());
            taskList.setList(new RealmList<>());
        });
    }
}
