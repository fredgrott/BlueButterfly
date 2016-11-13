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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

class AnimatorsCompat {

    static List<WeakReference<ObjectAnimator>> sRunningAnimators = new ArrayList<>();

    public static void start(ObjectAnimator animator) {
        sRunningAnimators.add(new WeakReference<>(animator));

        animator.start();
    }

    public static void setAutoCancel(final ObjectAnimator animator) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                for (WeakReference<ObjectAnimator> wa : sRunningAnimators) {
                    ObjectAnimator a = wa.get();
                    if (a == null) {
                        continue;
                    }

                    if (hasSameTargetAndProperties(animator, a)) {
                        a.cancel();
                    }
                }
            }
        });
    }

    private static boolean hasSameTargetAndProperties(ObjectAnimator self, @Nullable Animator anim) {
        if (anim instanceof ObjectAnimator) {
            PropertyValuesHolder[] theirValues = ((ObjectAnimator) anim).getValues();
            PropertyValuesHolder[] selfValues = self.getValues();
            if (((ObjectAnimator) anim).getTarget() == self.getTarget() && selfValues.length == theirValues.length) {

                final int length = selfValues.length;
                for (int i = 0; i < length; ++i) {
                    PropertyValuesHolder pvhMine = selfValues[i];
                    PropertyValuesHolder pvhTheirs = theirValues[i];
                    if (pvhMine.getPropertyName() == null || !pvhMine.getPropertyName().equals(pvhTheirs.getPropertyName())) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

}
