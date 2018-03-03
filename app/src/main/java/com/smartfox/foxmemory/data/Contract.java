package com.smartfox.foxmemory.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by SmartFox on 02.03.2018.
 */

public class Contract {

    private static final String DATABASE_NAME = "TEST_SQLITE";
    private static final int DATABASE_VERSION = 1;


    public static final String CONTENT_AUTHORITY = "com.smartfox.foxmemory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_TASKS = "pets";

    public static final class TaskEntry implements BaseColumns {

        public static final String TABLE_TASKS = "tasks";

        public final static String _ID = BaseColumns._ID;
        public static final String KEY_NAME = "name";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_PRIORITY = "priority";
    }
}
