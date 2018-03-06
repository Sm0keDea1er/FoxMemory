package com.smartfox.foxmemory.db.migration;

import java.util.Locale;

import io.realm.DynamicRealm;
import io.realm.RealmSchema;

/**
 * Created by SmartFox on 06.03.2018.
 */

public class RealmMigration implements io.realm.RealmMigration {
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
