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
package com.github.shareme.bluebutterfly.core.internal;

import android.graphics.Matrix;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Marcin on 2015-01-15.
 */
@SuppressWarnings("unused")
public class MatrixHelper {
    private MatrixHelper() {

    }

    static Animation animation;
    static Transformation transformation;

    public static Matrix getMatrix(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            return view.getMatrix();
        animation = view.getAnimation();
        if (animation == null)
            return new Matrix();
        transformation = new Transformation();
        animation.getTransformation(view.getDrawingTime(), transformation);
        return transformation.getMatrix();
    }
}
