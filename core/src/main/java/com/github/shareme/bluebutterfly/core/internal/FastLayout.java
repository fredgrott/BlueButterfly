package com.github.shareme.bluebutterfly.core.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Marcin on 2015-01-11.
 */
public class FastLayout extends com.github.shareme.bluebutterfly.core.widget.FrameLayout {
    public FastLayout(Context context) {
        super(context);
    }

    public FastLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FastLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void requestLayout() {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            View parent = ((View) getParent());
            measure(View.MeasureSpec.makeMeasureSpec(layoutParams.width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY));
            layout(layoutParams.leftMargin + parent.getPaddingLeft(),
                    parent.getPaddingTop() + layoutParams.topMargin,
                    parent.getPaddingLeft() + layoutParams.leftMargin + layoutParams.width,
                    parent.getPaddingTop() + layoutParams.topMargin + layoutParams.height);
        } else {
            super.requestLayout();
        }
    }
}
