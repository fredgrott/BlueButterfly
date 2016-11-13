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
package com.github.shareme.bluebutterfly.core.drawable.ripple;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Marcin on 2014-11-19.
 */
@SuppressWarnings("unused")
public interface RippleDrawable {

    Drawable getBackground();

    enum Style {
        Over, Background, Borderless
    }

    boolean setState(int[] stateSet);

    void draw(Canvas canvas);

    void setAlpha(int i);

    void setColorFilter(ColorFilter colorFilter);

    void jumpToCurrentState();

    int getOpacity();

    Style getStyle();

    boolean isHotspotEnabled();

    void setHotspotEnabled(boolean useHotspot);

    void setBounds(int left, int top, int right, int bottom);

    void setBounds(Rect bounds);

    void setHotspot(float x, float y);

    boolean isStateful();

    void setCallback(Drawable.Callback cb);

    ColorStateList getColor();

    void setRadius(int radius);

    int getRadius();
}
