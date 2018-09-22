package com.example.dimav.rxmvp.tasks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Extends {@link SwipeRefreshLayout} to support non-direct descendant scrolling views.
 * <p>
 *     {@link SwipeRefreshLayout} its triggers the refresh only when the view is on top.
 *     This class adds a way {@link #setScrollUpChild} to define which view controls this behavior.
 * </p>
 */

public class ScrollChildSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mScrollUpChild;

    public ScrollChildSwipeRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public ScrollChildSwipeRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if(mScrollUpChild != null) {

            return mScrollUpChild.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }
}
