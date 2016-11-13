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
