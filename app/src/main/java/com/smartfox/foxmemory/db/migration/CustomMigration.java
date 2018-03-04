package com.smartfox.foxmemory.db.migration;

import java.util.Locale;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by SmartFox on 04.03.2018.
 */

public class CustomMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            // Migrate from v0 to v1
            oldVersion++;
        }

        if (oldVersion < newVersion) {
            throw new IllegalStateException(String.format(Locale.US, "Migration missing from v%d to v%d", oldVersion, newVersion));
        }

    }
}
