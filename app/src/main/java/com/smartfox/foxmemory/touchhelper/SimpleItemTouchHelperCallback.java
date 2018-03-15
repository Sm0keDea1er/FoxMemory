package com.smartfox.foxmemory.touchhelper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.smartfox.foxmemory.TaskAdapter;

/**
 * Created by SmartFox on 10.03.2018.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ItemTouchHelperAdapter mAdapter;

    public SimpleItemTouchHelperCallback(
            ItemTouchHelperAdapter adapter) {
        mAdapter = adapter;
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

        // Set movement flags based on the layout manager
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = ItemTouchHelper.END | ItemTouchHelper.START;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());

        if (direction == 32){
            Log.d("SDS","RIGHT");

            viewHolder.itemView.animate().translationX(20).start();
            viewHolder.itemView.setTranslationX(20);
        }
        if (direction == 16) {
            Log.d("SDS","LEFT");

            viewHolder.itemView.animate().translationX(-20).start();
            viewHolder.itemView.setTranslationX(-20);
        }

    }



    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        mAdapter.onItemMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        mAdapter.onItemMoved();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        TaskAdapter.TaskViewHolder taskViewHolder = (TaskAdapter.TaskViewHolder) viewHolder;




    }
}
