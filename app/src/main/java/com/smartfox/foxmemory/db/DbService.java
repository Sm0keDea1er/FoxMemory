package com.smartfox.foxmemory.db;

import com.smartfox.foxmemory.R;
import com.smartfox.foxmemory.db.models.Task;
import com.smartfox.foxmemory.db.models.TasksList;

import java.util.Random;
import java.util.UUID;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by SmartFox on 06.03.2018.
 */

public class DbService {

    public static void onlyOneTable(Realm realm){

        Long count = realm.where(TasksList.class).count();
        if(count == 0){
            newTable(realm);
        }
    }

    private static void newTable(Realm realm){

        realm.beginTransaction();
        TasksList list = realm.createObject(TasksList.class, UUID.randomUUID().toString());
        list.setTasks(new RealmList<Task>());
        realm.commitTransaction();
    }

    public static void save(Realm realm, String id) {

        realm.beginTransaction();
        Task task = realm.createObject(Task.class);
        TasksList list = realm.where(TasksList.class).equalTo("id", id).findFirst();
        RealmList<Task> tasks = list.getTasks();

        task.setName("Name");
        task.setDescription("Description example");
        Random ran = new Random();
        task.setPriority(ran.nextInt(9) + 1);

        tasks.add(task);
        realm.commitTransaction();
    }

    public static void delete(Realm realm) {


    }
}
