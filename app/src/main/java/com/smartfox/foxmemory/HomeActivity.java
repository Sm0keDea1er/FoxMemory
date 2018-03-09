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
import com.smartfox.foxmemory.db.models.TasksList;
import com.smartfox.foxmemory.touchhelper.SDTouchHelperCallback;

import io.realm.Realm;
import io.realm.RealmList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView homeRecyclerView;
    private RecyclerView.Adapter homeAdapter;
    private LinearLayoutManager layoutManager;
    FloatingActionButton fab;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fab = findViewById(R.id.fab);
        homeRecyclerView = findViewById(R.id.home_recycle_view);


        realm = Realm.getDefaultInstance();

        DbService.onlyOneTable(realm);
        TasksList list = realm.where(TasksList.class).findFirst();
        final String tableId = list.getId();
        RealmList<Task> tasks = list.getTasks();


        homeRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(homeRecyclerView.getContext(), layoutManager.getOrientation());
        homeRecyclerView.addItemDecoration(dividerItemDecoration);


        homeAdapter = new HomeAdapter(tasks, realm);
        homeRecyclerView.setAdapter(homeAdapter);


        ItemTouchHelper.Callback callback = new SDTouchHelperCallback((HomeAdapter) homeAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(homeRecyclerView);

        fab.setOnClickListener(v -> {
            DbService.save(realm, tableId);
            homeAdapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }
}