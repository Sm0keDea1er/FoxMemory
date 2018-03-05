package com.smartfox.foxmemory;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.smartfox.foxmemory.db.models.TaskRealmModel;
import com.smartfox.foxmemory.touchhelper.SDTouchHelperCallback;

import io.realm.Realm;
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
        RealmQuery<TaskRealmModel> query = realm.where(TaskRealmModel.class);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        homeRecyclerView = (RecyclerView) findViewById(R.id.home_recycle_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        homeRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        homeRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(homeRecyclerView.getContext(),
                layoutManager.getOrientation());
        homeRecyclerView.addItemDecoration(dividerItemDecoration);

        RealmResults<TaskRealmModel> tasks = query.findAll();

        homeAdapter = new HomeAdapter(tasks, realm);
        homeRecyclerView.setAdapter(homeAdapter);


        ItemTouchHelper.Callback callback = new SDTouchHelperCallback((HomeAdapter) homeAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(homeRecyclerView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeAdapter) homeAdapter).addItem();
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
