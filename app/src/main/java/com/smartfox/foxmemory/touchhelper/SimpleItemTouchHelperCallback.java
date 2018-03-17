package com.smartfox.foxmemory.touchhelper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.smartfox.foxmemory.TaskAdapter;

/**
 * Created by SmartFox on 10.03.2018.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter adapter;
    private boolean movedFlag = false;

    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        return makeMovementFlags(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.END | ItemTouchHelper.START);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        if (direction == 32) {

            adapter.complete(viewHolder.getAdapterPosition());
        }

        if (direction == 16) {

            if (!adapter.isComplete(viewHolder.getAdapterPosition())) {

                adapter.onItemDismiss(viewHolder.getAdapterPosition());
            } else {

                adapter.notComplete(viewHolder.getAdapterPosition());
            }
        }
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder
            viewHolder, RecyclerView.ViewHolder target) {

        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        adapter.onItemMove(fromPosition, toPosition);
        movedFlag = true;
        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (movedFlag) {
            adapter.onItemMoved();
            movedFlag = false;
            Log.d("SDS", "movedFlag ");
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder
            viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        if (dX > Resources.getSystem().getDisplayMetrics().widthPixels || dX < -Resources.getSystem().getDisplayMetrics().widthPixels) return;
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

//        Log.d("SDS", String.valueOf(dX));


        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            TaskAdapter.TaskViewHolder taskViewHolder = (TaskAdapter.TaskViewHolder) viewHolder;

            boolean isComplete = adapter.isComplete(viewHolder.getAdapterPosition());


            if (isComplete && dX < 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    taskViewHolder.itemView.setElevation(0);
                }
                taskViewHolder.itemView.setTranslationX(0);

                taskViewHolder.line.setScaleX(taskViewHolder.itemView.getWidth() + dX);
                if (dX < viewHolder.itemView.getWidth())
                    taskViewHolder.linearLayout.setAlpha(0.4f + (-dX / taskViewHolder.itemView.getWidth()) * (1 - 0.4f));


            } else if (!isComplete && dX > 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    taskViewHolder.itemView.setElevation(0);
                }
                taskViewHolder.itemView.setTranslationX(0);

                taskViewHolder.line.setVisibility(View.VISIBLE);
                taskViewHolder.line.setScaleX(dX);
                if (dX < viewHolder.itemView.getWidth())
                    taskViewHolder.linearLayout.setAlpha(1 - (dX / taskViewHolder.itemView.getWidth()) * (1 - 0.4f));


            }else if (isComplete && dX > 0){

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    taskViewHolder.itemView.setElevation(0);
                }
                taskViewHolder.itemView.setTranslationX(0);
            }
        }
    }
}
