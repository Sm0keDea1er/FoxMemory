package com.smartfox.foxmemory.touchhelper;

/**
 * Created by SmartFox on 10-Feb-18.
 */

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);
}
