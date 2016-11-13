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

import android.content.Context;
import android.content.res.ColorStateList;

import com.github.shareme.bluebutterfly.core.Carbon;
import com.github.shareme.bluebutterfly.core.R;


/**
 * Created by Marcin on 2015-03-16.
 */
public class DefaultAccentColorStateList extends ColorStateList {
    public DefaultAccentColorStateList(Context context) {
        super(new int[][]{
                new int[]{R.attr.carbon_state_invalid},
                new int[]{-android.R.attr.state_enabled},
                new int[]{}
        }, new int[]{
                Carbon.getThemeColor(context, R.attr.carbon_colorError),
                Carbon.getThemeColor(context, R.attr.carbon_colorDisabled),
                Carbon.getThemeColor(context, R.attr.colorAccent)
        });
    }

    /**
     * @param context context
     * @param attr    attribute to get from the current theme
     * @return color from the current theme
     * @deprecated use {@link Carbon#getThemeColor(Context, int)} instead. This method was duplicated in all ColorStateList implementations
     */
    @Deprecated
    public static int getThemeColor(Context context, int attr) {
        return Carbon.getThemeColor(context, attr);
    }
}
