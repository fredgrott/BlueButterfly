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
package com.github.shareme.bluebutterfly.core.animation;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.os.Parcel;
import android.util.StateSet;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.shareme.bluebutterfly.core.internal.ArgbEvaluator;

import java.lang.reflect.Field;
import java.util.Arrays;



/**
 * Created by Marcin on 2016-01-16.
 */
@SuppressWarnings("unused")
public class AnimatedColorStateList extends ColorStateList {
    private final int[][] states;
    private int[] currentState = null;
    private ValueAnimator colorAnimation = null;
    private int animatedColor;

    public static AnimatedColorStateList fromList(ColorStateList list, ValueAnimator.AnimatorUpdateListener listener) {
        int[][] mStateSpecs; // must be parallel to mColors
        int[] mColors;      // must be parallel to mStateSpecs
        int mDefaultColor;

        try {
            Field mStateSpecsField = ColorStateList.class.getDeclaredField("mStateSpecs");
            mStateSpecsField.setAccessible(true);
            mStateSpecs = (int[][]) mStateSpecsField.get(list);
            Field mColorsField = ColorStateList.class.getDeclaredField("mColors");
            mColorsField.setAccessible(true);
            mColors = (int[]) mColorsField.get(list);
            Field mDefaultColorField = ColorStateList.class.getDeclaredField("mDefaultColor");
            mDefaultColorField.setAccessible(true);
            mDefaultColor = (int) mDefaultColorField.get(list);
            AnimatedColorStateList animatedColorStateList = new AnimatedColorStateList(mStateSpecs, mColors, listener);
            mDefaultColorField.set(animatedColorStateList, mDefaultColor);
            return animatedColorStateList;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public AnimatedColorStateList(int[][] states, int[] colors, final ValueAnimator.AnimatorUpdateListener listener) {
        super(states, colors);
        this.states = states;
        colorAnimation = ValueAnimator.ofInt(0, 0);
        colorAnimation.setEvaluator(new ArgbEvaluator());
        colorAnimation.setDuration(200);
        colorAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        colorAnimation.addUpdateListener(animation -> {
            synchronized (AnimatedColorStateList.this) {
                animatedColor = (int) animation.getAnimatedValue();
                listener.onAnimationUpdate(animation);
            }
        });
    }

    @Override
    public int getColorForState(int[] stateSet, int defaultColor) {
        synchronized (AnimatedColorStateList.this) {
            if (Arrays.equals(stateSet, currentState)) {
                return animatedColor;
            }
        }
        return super.getColorForState(stateSet, defaultColor);
    }

    public void setState(int[] newState) {
        if (Arrays.equals(newState, currentState))
            return;
        if (currentState != null)
            cancel();

        for (final int[] state : states) {
            if (StateSet.stateSetMatches(state, newState)) {
                int firstColor = super.getColorForState(currentState, getDefaultColor());
                int secondColor = super.getColorForState(newState, getDefaultColor());
                colorAnimation.setIntValues(firstColor, secondColor);
                currentState = newState;
                animatedColor = firstColor;
                colorAnimation.start();
                return;
            }
        }

        currentState = newState;
    }

    private void cancel() {
        colorAnimation.cancel();
    }

    public void jumpToCurrentState() {
        colorAnimation.end();
    }

    public static final Creator<AnimatedColorStateList> CREATOR =
            new Creator<AnimatedColorStateList>() {
                @Override
                public AnimatedColorStateList[] newArray(int size) {
                    return new AnimatedColorStateList[size];
                }

                @Override
                public AnimatedColorStateList createFromParcel(Parcel source) {
                    final int N = source.readInt();
                    final int[][] stateSpecs = new int[N][];
                    for (int i = 0; i < N; i++) {
                        stateSpecs[i] = source.createIntArray();
                    }
                    final int[] colors = source.createIntArray();
                    return AnimatedColorStateList.fromList(new ColorStateList(stateSpecs, colors), null);
                }
            };
}
