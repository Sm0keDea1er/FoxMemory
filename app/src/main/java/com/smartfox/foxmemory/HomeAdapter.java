package com.smartfox.foxmemory;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartfox.foxmemory.db.models.Task;
import com.smartfox.foxmemory.db.models.TasksList;
import com.smartfox.foxmemory.touchhelper.ItemTouchHelperAdapter;

import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

/**
 * Created by SmartFox on 09-Feb-18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements ItemTouchHelperAdapter, RealmChangeListener<TasksList> {


    private final RealmList<Task> tasks;
    private Realm realm;


    public HomeAdapter(RealmList<Task> tasks, Realm realm) {
        this.realm = realm;
        this.tasks = tasks;
    }


    /**
     * Создание новых View и ViewHolder элемента списка, которые впоследствии могут переиспользоваться.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }


    /**
     * Заполнение виджетов View данными из элемента списка с номером i
     */
    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, final int position) {

        Task task = tasks.get(position);
        holder.name.setText(task.getName());
        holder.description.setText(task.getDescription());
        holder.priority.setText(String.valueOf(task.getPriority()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    @Override
    public void onItemDismiss(int position) {

        realm.beginTransaction();
        tasks.get(position).deleteFromRealm();
        realm.commitTransaction();

        notifyDataSetChanged();


//        mRecyclerV.removeViewAt(position);

    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {


        realm.beginTransaction();
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(tasks, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(tasks, i, i - 1);
            }
        }
        realm.commitTransaction();

        notifyDataSetChanged();

    }


    @Override
    public void onChange(TasksList task) {
        notifyDataSetChanged();
    }


    /**
     * Реализация класса ViewHolder, хранящего ссылки на виджеты.
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView description;
        private TextView id;
        private TextView priority;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_id);
            name = itemView.findViewById(R.id.item_name);
            description = itemView.findViewById(R.id.item_description);
            priority = itemView.findViewById(R.id.item_priority);
        }
    }
}
