package com.smartfox.foxmemory;

import android.app.Application;

import com.smartfox.foxmemory.db.migration.RealmMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by SmartFox on 03.03.2018.
 */

public class FoxMemoryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("FoxDB")
                .schemaVersion(0)
                .migration(new RealmMigration())
                .build();
        Realm.setDefaultConfiguration(configuration);
    }
}
