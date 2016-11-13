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
package com.github.shareme.bluebutterfly.core.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.github.shareme.bluebutterfly.core.R;


/**
 * Created by Marcin on 2016-06-08.
 */
@SuppressWarnings("unused")
public class ExpansionPanel extends RelativeLayout {
    ImageView expandedIndicator;
    private boolean expanded;

    public ExpansionPanel(Context context) {
        super(context);
        initExpansionPanel();
    }

    public ExpansionPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initExpansionPanel();
    }

    public ExpansionPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initExpansionPanel();
    }

    public ExpansionPanel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initExpansionPanel();
    }

    private void initExpansionPanel() {
        View.inflate(getContext(), R.layout.carbon_expansionpanel, this);
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        expandedIndicator = (ImageView) findViewById(R.id.carbon_groupExpandedIndicator);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded()) {
                    collapse();
                } else {
                    expand();
                }
            }
        });
    }

    public void expand() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                expandedIndicator.setRotation(180 * (float) (animation.getAnimatedValue()));
                expandedIndicator.postInvalidate();
            }
        });
        animator.start();
        expanded = true;
    }

    public void collapse() {
        ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                expandedIndicator.setRotation(180 * (float) (animation.getAnimatedValue()));
                expandedIndicator.postInvalidate();
            }
        });
        animator.start();
        expanded = false;
    }

    public void setExpanded(boolean expanded) {
        expandedIndicator.setRotation(expanded ? 180 : 0);
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
