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

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Marcin on 2015-05-08.
 * <p/>
 * Interface of a view with support for tinting.
 */
public interface TintedView {
    PorterDuff.Mode[] modes = {
            PorterDuff.Mode.SRC_OVER,
            PorterDuff.Mode.SRC_IN,
            PorterDuff.Mode.SRC_ATOP,
            PorterDuff.Mode.MULTIPLY,
            PorterDuff.Mode.SCREEN
    };

    /**
     * Sets the tint of foreground parts like checkbox or icon
     *
     * @param list a tint color state list
     */
    void setTint(ColorStateList list);

    /**
     * Sets the tint of foreground parts like checkbox or icon
     *
     * @param color a tint color
     */
    void setTint(int color);

    /**
     * Gets the tint of foreground parts like checkbox or icon
     *
     * @return the tint
     */
    ColorStateList getTint();

    /**
     * Sets the tint mode of foreground parts like checkbox or icon
     *
     * @param mode
     */
    void setTintMode(@NonNull PorterDuff.Mode mode);

    /**
     * Gets the tint mode of foreground parts like checkbox or icon
     *
     * @return
     */
    PorterDuff.Mode getTintMode();

    /**
     * Sets the tint of background Drawable
     *
     * @param list a tint color state list
     */
    void setBackgroundTint(ColorStateList list);

    /**
     * Sets the tint of background Drawable
     *
     * @param color a tint color
     */
    void setBackgroundTint(int color);

    /**
     * Gets the tint of background Drawable
     *
     * @return the tint
     */
    ColorStateList getBackgroundTint();

    /**
     * Sets the tint mode of background Drawable
     *
     * @param mode
     */
    void setBackgroundTintMode(@Nullable PorterDuff.Mode mode);

    /**
     * Gets the tint mode of background Drawable
     *
     * @return
     */
    PorterDuff.Mode getBackgroundTintMode();

    boolean isAnimateColorChangesEnabled();

    void setAnimateColorChangesEnabled(boolean animateColorChanges);
}
