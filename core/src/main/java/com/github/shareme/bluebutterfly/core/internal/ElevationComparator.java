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

import android.view.View;

import com.github.shareme.bluebutterfly.core.shadow.ShadowView;

import java.util.Comparator;


/**
 * Created by Marcin on 2015-01-15.
 */
@SuppressWarnings("unused")
public class ElevationComparator implements Comparator<View> {
    @Override
    public int compare(View lhs, View rhs) {
        float elevation1 = 0;
        if (lhs instanceof ShadowView)  // this casting is not redundant
            elevation1 = ((ShadowView) lhs).getElevation() + ((ShadowView) lhs).getTranslationZ();
        float elevation2 = 0;
        if (rhs instanceof ShadowView)
            elevation2 = ((ShadowView) rhs).getElevation() + ((ShadowView) rhs).getTranslationZ();
        return (int) Math.signum(elevation1 - elevation2);
    }
}
