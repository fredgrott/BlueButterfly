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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.github.shareme.bluebutterfly.core.widget.ProgressBar;


/**
 * Created by Mbarin on 2015-02-08.
 */
public class ProgressBarDrawable extends ProgressDrawable {
    private static final long DEFAULT_SWEEP_DURATION = 800;
    private static final long DEFAULT_SWEEP_OFFSET = 500;
    private long sweepDuration = DEFAULT_SWEEP_DURATION;
    private long sweepDelay = DEFAULT_SWEEP_OFFSET;

    Interpolator interpolator = new AccelerateDecelerateInterpolator();

    public ProgressBarDrawable() {
        forePaint.setStyle(Paint.Style.FILL);
        forePaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();

        if (style == ProgressBar.Style.BarIndeterminate) {
            long time = (System.currentTimeMillis() - startTime) % (sweepDuration + sweepDelay);
            float t = (float) (time) / (sweepDuration);
            float t2 = Math.max(0, (float) (time - sweepDelay) / sweepDuration);
            float end = interpolator.getInterpolation(t2);
            canvas.drawRect(end * bounds.width(), getBarPadding(), t * bounds.width(), bounds.height(), forePaint);
        } else if (style == ProgressBar.Style.BarQuery) {
            long time = (System.currentTimeMillis() - startTime) % (sweepDuration + sweepDelay);
            float t = 1 - (float) (time) / (sweepDuration);
            float t2 = Math.max(0, (float) (time - sweepDelay) / sweepDuration);
            float end = 1 - interpolator.getInterpolation(t2);
            canvas.drawRect(t * bounds.width(), getBarPadding(), end * bounds.width(), bounds.height(), forePaint);
        } else {
            canvas.drawRect(0, getBarPadding(), progress * bounds.width(), bounds.height(), forePaint);
        }

        invalidateSelf();
    }
}
