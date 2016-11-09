package com.github.shareme.bluebutterfly.core.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.github.shareme.bluebutterfly.core.animation.AnimatedColorStateList;


/**
 * Created by Marcin on 2015-08-21.
 */
public class ColorStateListDrawable extends Drawable {
    private AnimatedColorStateList list;

    public ColorStateListDrawable(AnimatedColorStateList list) {
        this.list = list;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawColor(list.getColorForState(getState(), list.getDefaultColor()));
    }

    @Override
    public void setAlpha(int i) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public boolean setState(@NonNull int[] stateSet) {
        list.setState(stateSet);
        return super.setState(stateSet);
    }
}
