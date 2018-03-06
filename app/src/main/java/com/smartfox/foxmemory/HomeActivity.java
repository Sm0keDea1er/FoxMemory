package com.smartfox.foxmemory;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.smartfox.foxmemory.db.DbService;
import com.smartfox.foxmemory.db.models.Task;
import com.smartfox.foxmemory.db.models.TasksList;
import com.smartfox.foxmemory.touchhelper.SDTouchHelperCallback;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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


        realm = Realm.getDefaultInstance();

        DbService.onlyOneTable(realm);
        TasksList list = realm.where(TasksList.class).findFirst();
        final String tableId = list.getId();
        RealmList<Task>  tasks = list.getTasks();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        homeRecyclerView = (RecyclerView) findViewById(R.id.home_recycle_view);


        homeRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(homeRecyclerView.getContext(),
                layoutManager.getOrientation());
        homeRecyclerView.addItemDecoration(dividerItemDecoration);


        homeAdapter = new HomeAdapter(tasks, realm);
        homeRecyclerView.setAdapter(homeAdapter);


        ItemTouchHelper.Callback callback = new SDTouchHelperCallback((HomeAdapter) homeAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(homeRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DbService.save(realm, tableId);
                homeAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        realm.close();
    }
}
// change
