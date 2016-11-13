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

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Marcin on 2015-04-11.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class RippleDrawableLollipop extends android.graphics.drawable.RippleDrawable implements RippleDrawable {

    private final ColorStateList color;
    private final Drawable background;
    private Style style;
    private boolean useHotspot;
    private int radius;

    public RippleDrawableLollipop(ColorStateList color, Drawable background, Style style) {
        super(color, background, style == Style.Borderless ? null : new ColorDrawable(0xffffffff));
        this.style = style;
        this.color = color;
        this.background = background;
    }

    @Override
    public Drawable getBackground() {
        return background;
    }

    @Override
    public Style getStyle() {
        return style;
    }

    @Override
    public boolean isHotspotEnabled() {
        return useHotspot;
    }

    @Override
    public void setHotspotEnabled(boolean useHotspot) {
        this.useHotspot = useHotspot;
    }

    @Override
    public ColorStateList getColor() {
        return color;
    }

    @Override
    public void setRadius(int radius) {
        this.radius = radius;
        try {
            Method setMaxRadiusMethod = android.graphics.drawable.RippleDrawable.class.getDeclaredMethod("setMaxRadius", int.class);
            setMaxRadiusMethod.invoke(this, radius);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getRadius() {
        return radius;
    }
}
