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
package com.github.shareme.bluebutterfly.core.internal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.github.shareme.bluebutterfly.core.R;
import com.github.shareme.bluebutterfly.core.widget.FrameLayout;
import com.github.shareme.bluebutterfly.core.widget.TextView;


/**
 * Created by Marcin on 2016-02-29.
 */
@SuppressWarnings("unused")
public class SeekBarPopup extends PopupWindow {
    private final Context context;
    private final View contentView;
    FrameLayout bubble;
    TextView label;

    @SuppressLint("InflateParams")
    public SeekBarPopup(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.carbon_seekbar_bubble, null, false));
        contentView = getContentView();
        label = (TextView) contentView.findViewById(R.id.carbon_label);
        bubble = (FrameLayout) contentView.findViewById(R.id.carbon_bubble);
        this.context = context;

        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        setTouchable(false);
        setFocusable(false);
        setOutsideTouchable(false);
        setAnimationStyle(0);
        setClippingEnabled(false);
    }

    @Override
    public void update(int x, int y) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        super.update(0, y, wm.getDefaultDisplay().getWidth(), contentView.getMeasuredHeight());
        bubble.setTranslationX(x);
    }

    public boolean show(View anchor) {

        super.showAtLocation(anchor, Gravity.START | Gravity.TOP, 0, 0);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        contentView.measure(View.MeasureSpec.makeMeasureSpec(wm.getDefaultDisplay().getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        super.update(wm.getDefaultDisplay().getWidth(), contentView.getMeasuredHeight());

        bubble.setVisibility(View.VISIBLE);

        return true;
    }

    public boolean showImmediate(View anchor) {
        super.showAtLocation(anchor, Gravity.START | Gravity.TOP, 0, 0);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        contentView.measure(View.MeasureSpec.makeMeasureSpec(wm.getDefaultDisplay().getWidth(), View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        super.update(wm.getDefaultDisplay().getWidth(), contentView.getMeasuredHeight());

        bubble.setVisibilityImmediate(View.VISIBLE);

        return true;
    }

    @Override
    public void dismiss() {
        bubble.setVisibility(View.INVISIBLE);
        Animator animator = bubble.getAnimator();
        if (animator != null) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    SeekBarPopup.super.dismiss();
                }
            });
        }
    }

    public void dismissImmediate() {
        super.dismiss();
    }

    public void setText(String text) {
        label.setText(text);
    }

    public int getBubbleWidth() {
        return bubble.getMeasuredWidth();
    }
}
