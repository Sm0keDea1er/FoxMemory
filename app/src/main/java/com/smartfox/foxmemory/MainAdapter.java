package com.smartfox.foxmemory;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartfox.foxmemory.db.DbHandler;
import com.smartfox.foxmemory.db.models.TaskRealmModel;
import com.smartfox.foxmemory.touchhelper.ItemTouchHelperAdapter;

import java.util.Collections;
import java.util.Random;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by SmartFox on 09-Feb-18.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements ItemTouchHelperAdapter, RealmChangeListener<TaskRealmModel> {


    private final RealmResults<TaskRealmModel> tasks;



    public MainAdapter(RealmResults<TaskRealmModel> tasks) {

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
    public void onBindViewHolder(MainAdapter.ViewHolder holder, final int position) {

        TaskRealmModel task = tasks.get(position);
        holder.id.setText("id: " + String.valueOf(task.get_id()));
        holder.name.setText(task.getName());
        holder.description.setText(task.getDescription());
        holder.priority.setText("priority: " + String.valueOf(task.getPriority()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }


    @Override
    public void onItemDismiss(int position) {

        dbHandler.delete(tasks.get(position).get_id());

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, tasks.size());
        notifyDataSetChanged();


//        mRecyclerV.removeViewAt(position);

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

        dbHandler.swap(tasks.get(fromPosition), tasks.get(toPosition));
        notifyItemMoved(fromPosition, toPosition);

    }

    public void addItem(){

        TaskRealmModel task = new TaskRealmModel();
        task.setName("DO IT");
        task.setDescription("simple description");

        Random ran = new Random();
        task.setPriority(ran.nextInt(9) + 1);

        dbHandler.addTask(task);
        tasks = dbHandler.getAllTasks();

        notifyDataSetChanged();
    }

    @Override
    public void onChange(TaskRealmModel task) {
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
            id = (TextView) itemView.findViewById(R.id.item_id);
            name = (TextView) itemView.findViewById(R.id.item_name);
            description = (TextView) itemView.findViewById(R.id.item_description);
            priority = (TextView) itemView.findViewById(R.id.item_priority);
        }
    }
}
