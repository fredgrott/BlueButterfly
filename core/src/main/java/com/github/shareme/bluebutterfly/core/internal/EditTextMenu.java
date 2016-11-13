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
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.github.shareme.bluebutterfly.core.R;
import com.github.shareme.bluebutterfly.core.widget.Button;
import com.github.shareme.bluebutterfly.core.widget.EditText;
import com.github.shareme.bluebutterfly.core.widget.LinearLayout;


/**
 * Created by Marcin on 2015-09-29.
 */
@SuppressWarnings("unused")
public class EditTextMenu extends PopupWindow {
    private EditText editText;

    @SuppressLint("InflateParams")
    public EditTextMenu(Context context) {
        super(LayoutInflater.from(context).inflate(R.layout.carbon_editormenu, null, false));
        getContentView().setLayoutParams(new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(android.R.color.transparent)));

        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
        setAnimationStyle(0);
        setClippingEnabled(false);
    }

    public boolean show(EditText anchor) {
        editText = anchor;

        super.showAtLocation(anchor, Gravity.START | Gravity.TOP, 0, 0);

        update();

        View content = getContentView().findViewById(R.id.carbon_menuContainer);
        content.setVisibility(View.VISIBLE);

        return true;
    }

    public boolean showImmediate(EditText anchor) {
        editText = anchor;

        super.showAtLocation(anchor, Gravity.START | Gravity.TOP, 0, 0);

        update();

        LinearLayout content = (LinearLayout) getContentView().findViewById(R.id.carbon_menuContainer);
        content.setVisibilityImmediate(View.VISIBLE);

        return true;
    }

    public void update() {
        if (editText == null)
            return;

        final Resources res = getContentView().getContext().getResources();

        int margin = (int) res.getDimension(R.dimen.carbon_padding);
        int itemHeight = (int) res.getDimension(R.dimen.carbon_menuHeight);

        Rect windowRect = new Rect();
        editText.getWindowVisibleDisplayFrame(windowRect);

        int[] location = new int[2];
        editText.getLocationInWindow(location);

        LinearLayout content = (LinearLayout) getContentView().findViewById(R.id.carbon_menuContent);
        content.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(itemHeight, View.MeasureSpec.EXACTLY));

        int popupX = location[0] - margin;
        int popupY = location[1] - margin * 2 - content.getMeasuredHeight();

        update(popupX, popupY, content.getMeasuredWidth() + margin * 2, content.getMeasuredHeight() + margin * 2);

        super.update();
    }

    @Override
    public void dismiss() {
        LinearLayout content = (LinearLayout) getContentView().findViewById(R.id.carbon_menuContainer);
        content.setVisibility(View.INVISIBLE);
        content.getAnimator().addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                EditTextMenu.super.dismiss();
            }
        });
    }

    public void dismissImmediate() {
        super.dismiss();
    }

    public boolean hasVisibleItems() {
        View cut = getContentView().findViewById(R.id.carbon_cut);
        View copy = getContentView().findViewById(R.id.carbon_copy);
        View paste = getContentView().findViewById(R.id.carbon_paste);
        View selectAll = getContentView().findViewById(R.id.carbon_selectAll);

        return cut.getVisibility() == View.VISIBLE || copy.getVisibility() == View.VISIBLE || paste.getVisibility() == View.VISIBLE || selectAll.getVisibility() == View.VISIBLE;
    }

    public void initCopy(final MenuItem item) {
        Button button = (Button) getContentView().findViewById(R.id.carbon_copy);
        initMenuItem(item, button);
    }

    public void initCut(final MenuItem item) {
        Button button = (Button) getContentView().findViewById(R.id.carbon_cut);
        initMenuItem(item, button);
    }

    public void initPaste(final MenuItem item) {
        Button button = (Button) getContentView().findViewById(R.id.carbon_paste);
        initMenuItem(item, button);
    }

    public void initSelectAll(final MenuItem item) {
        Button button = (Button) getContentView().findViewById(R.id.carbon_selectAll);
        initMenuItem(item, button);
    }

    private void initMenuItem(final MenuItem item, Button button) {
        if (item != null) {
            button.setText(item.getTitle());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.onTextContextMenuItem(item.getItemId());
                    dismiss();
                }
            });
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }
    }

}
