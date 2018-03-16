package com.smartfox.foxmemory;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartfox.foxmemory.databinding.ListItemBinding;
import com.smartfox.foxmemory.db.DbService;
import com.smartfox.foxmemory.db.models.Task;
import com.smartfox.foxmemory.touchhelper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by SmartFox on 10.03.2018.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> implements ItemTouchHelperAdapter {

    private Context context;
    private List<Task> tasks;
    private Realm realm;


    public TaskAdapter(Context context, List<Task> tasks, Realm realm) {
        this.context = context;
        this.tasks = tasks;
        this.realm = realm;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding = ListItemBinding.inflate(inflater, parent, false);
        return new TaskViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task = tasks.get(position);
        holder.binding.setTask(task);
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(tasks, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(tasks, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {

        Task task = tasks.get(position);
        String id = task.getId();

        realm.executeTransaction(realm -> {
            RealmResults<Task> result = realm.where(Task.class).equalTo(Task.ID, id).findAll();
            result.deleteAllFromRealm();
        });

        tasks.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMoved() {
        realm.executeTransaction(realm1 -> {
            RealmList<Task> list = DbService.onlyOneList(realm);
            list.clear();
            list.addAll(tasks);
    });

    }



    public void addTask(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding binding;

        TextView name, description, id, priority;
        public View line;

        public TaskViewHolder(View v) {
            super(v);

            binding = DataBindingUtil.bind(v);

            line = v.findViewById(R.id.item_line);
        }

    }

}
