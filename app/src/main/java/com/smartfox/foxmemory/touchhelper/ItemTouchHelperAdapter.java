package com.smartfox.foxmemory.touchhelper;

/**
 * Created by SmartFox on 10.03.2018.
 */

public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

    void onItemMoved();
}
