package com.smartfox.foxmemory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.smartfox.foxmemory.data.Contract.TaskEntry;

/**
 * Created by SmartFox on 01.03.2018.
 */

public class DbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TEST_SQLITE";
    private static final int DATABASE_VERSION = 1;

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE " + TaskEntry.TABLE_TASKS + "("
                        + TaskEntry._ID + " INTEGER PRIMARY KEY, "
                        + TaskEntry.KEY_NAME + " TEXT, "
                        + TaskEntry.KEY_DESCRIPTION + " TEXT, "
                        + TaskEntry.KEY_PRIORITY + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TaskEntry.TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(Task task) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TaskEntry.KEY_NAME, task.get_name());
        cv.put(TaskEntry.KEY_DESCRIPTION, task.get_description());
        cv.put(TaskEntry.KEY_PRIORITY, task.get_priority());

        db.insert(TaskEntry.TABLE_TASKS, null, cv);
        db.close();
    }

    public List<Task> getAllTasks() {

        List<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TaskEntry.TABLE_TASKS, null);
        while (cursor.moveToNext()) {
            Task task = new Task();
            task.set_id(cursor.getInt(0));
            task.set_name(cursor.getString(1));
            task.set_description(cursor.getString(2));
            task.set_priority(cursor.getInt(3));

            tasks.add(task);
        }
        cursor.close();
        return tasks;
    }

    public int getTasksCount() {
        int count = -1;
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TaskEntry.TABLE_TASKS, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }

    public int update(Task task, int id) {

        int number = -1;

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TaskEntry.KEY_NAME, task.get_name());
        cv.put(TaskEntry.KEY_DESCRIPTION, task.get_description());
        cv.put(TaskEntry.KEY_PRIORITY, task.get_priority());

        number = db.update(TaskEntry.TABLE_TASKS, cv, TaskEntry._ID + "=?", new String[]{String.valueOf(id)});
        db.close();

        return number;
    }

    public int delete(int id) {

        int number = -1;
        SQLiteDatabase db = getWritableDatabase();
        number = db.delete(TaskEntry.TABLE_TASKS, TaskEntry._ID + "=?", new String[]{String.valueOf(id)});
        db.close();

        return number;
    }

    public void swap(Task firstMember, Task secondMember) {

        update(firstMember,secondMember.get_id());
        update(secondMember,firstMember.get_id());
    }
}
