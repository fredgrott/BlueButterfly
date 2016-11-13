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


import android.animation.Animator;

import com.github.shareme.bluebutterfly.core.animation.AnimUtils;

/**
 * Created by Marcin on 2014-11-30.
 * <p/>
 * Interface of a view with animations. These animations are used for changing visibility by using setVisible(boolean) method.
 */
public interface AnimatedView {
    /**
     * Gets the current Animator object. Works like View.getAnimation() but with animators.
     *
     * @return the current Animator object or null
     */
    Animator getAnimator();

    /**
     * Gets the animation used when view's visibility is changed from VISIBLE to GONE/INVISIBLE
     *
     * @return the current out animation or AnimUtils.Style.None if nothing is set.
     */
    AnimUtils.Style getOutAnimation();

    /**
     * Sets the animation used when view's visibility is changed from VISIBLE to GONE/INVISIBLE
     *
     * @param outAnim new out animation. Use AnimUtils.Style.None for no animation.
     */
    void setOutAnimation(AnimUtils.Style outAnim);

    /**
     * Gets the animation used when view's visibility is changed from GONE/INVISIBLE to VISIBLE
     *
     * @return the current in animation or AnimUtils.Style.None if nothing is set.
     */
    AnimUtils.Style getInAnimation();

    /**
     * Sets the animation used when view's visibility is changed from GONE/INVISIBLE to VISIBLE
     *
     * @param inAnim new in animation. Use AnimUtils.Style.None for no animation.
     */
    void setInAnimation(AnimUtils.Style inAnim);
}
