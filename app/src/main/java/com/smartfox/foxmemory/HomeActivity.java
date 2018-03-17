package com.smartfox.foxmemory;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.smartfox.foxmemory.db.DbService;
import com.smartfox.foxmemory.db.models.Task;
import com.smartfox.foxmemory.touchhelper.SimpleItemTouchHelperCallback;

import io.realm.Realm;
import io.realm.Sort;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycler_view);

        realm = Realm.getDefaultInstance();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        TaskAdapter taskAdapter = new TaskAdapter(this, DbService.getListTasks(realm), realm);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(taskAdapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(taskAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);

        fab.setOnClickListener(v -> {

            DbService.save(realm);
            Task task = realm.where(Task.class).findAll().sort(Task.CREATED_AT, Sort.DESCENDING).first();
            taskAdapter.addTask(task);
//            layoutManager.smoothScrollToPosition(recyclerView, null, taskAdapter.getItemCount() - 1);
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (realm != null)
            realm.close();
        realm = null;
    }
}