/*
Copyright 2015 Marcin Korniluk 'Zielony'
Modifications Copyright(C) 2016 Fred Grott(GrottWorkShop)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */
package com.github.shareme.bluebutterfly.core.beta;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.github.shareme.bluebutterfly.core.widget.FrameLayout;


/**
 * Created by Marcin on 17.03.2016.
 */
@SuppressWarnings("unused")
public class BottomSheet extends FrameLayout {
    private ViewDragHelper mDragHelper;

    public BottomSheet(Context context) {
        super(context);
        initBottomSheet();
    }

    public BottomSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBottomSheet();
    }

    public BottomSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBottomSheet();
    }

    public BottomSheet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBottomSheet();
    }

    private void initBottomSheet() {
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        mDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    public class DragHelperCallback extends ViewDragHelper.Callback {

        public int getViewVerticalDragRange(View child) {
            return ((ViewGroup) getParent()).getMeasuredHeight() - child.getMeasuredHeight();
        }

        @Override
        public boolean tryCaptureView(View view, int i) {
            return true;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragHelper.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() { // needed for automatic settling.
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


}
