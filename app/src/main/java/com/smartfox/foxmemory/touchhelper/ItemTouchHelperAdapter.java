package com.smartfox.foxmemory.touchhelper;

/**
 * Created by SmartFox on 10.03.2018.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

    void onItemMoved();

    boolean isComplete(int position);

    void complete(int position);

    void notComplete(int position);

}
