package com.smartfox.foxmemory;

import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

        if (task.isComplete()) {

            holder.line.setVisibility(View.VISIBLE);
            holder.line.setScaleX(Resources.getSystem().getDisplayMetrics().widthPixels);
            holder.linearLayout.setAlpha(0.4f);

        } else if (!task.isComplete()) {

            holder.line.setVisibility(View.INVISIBLE);
            holder.line.setScaleX(1);
            holder.linearLayout.setAlpha(1);
        }
    }


    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {

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

    @Override
    public boolean isComplete(int position) {

        if (position == -1) return false;

        return tasks.get(position).isComplete();
    }

    @Override
    public void complete(int position) {

//        Log.d("SDS", "complete ");

        realm.executeTransaction(realm1 -> {
            tasks.get(position).setComplete(true);
        });
        notifyItemChanged(position);

    }

    @Override
    public void notComplete(int position) {

//        Log.d("SDS", "notComplete ");
        realm.executeTransaction(realm1 -> {
            tasks.get(position).setComplete(false);
        });
        notifyItemChanged(position);
    }


    public void addTask(Task task) {
        tasks.add(task);
        notifyDataSetChanged();
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder {

        ListItemBinding binding;
        public View line;
        public LinearLayout linearLayout;

        public TaskViewHolder(View v) {
            super(v);

            binding = DataBindingUtil.bind(v);

            line = v.findViewById(R.id.item_line);
            linearLayout = v.findViewById(R.id.item_layout);
            line.setPivotX(0);
        }

    }

}
