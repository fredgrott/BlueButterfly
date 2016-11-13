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

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

@SuppressWarnings("unused")
public class TypefaceUtils {
    private static HashMap<Roboto, Typeface> robotoCache = new HashMap<>();
    private static HashMap<String, Typeface> pathCache = new HashMap<>();
    private static HashMap[] familyStyleCache = new HashMap[4];

    static {
        familyStyleCache[Typeface.NORMAL] = new HashMap();
        familyStyleCache[Typeface.ITALIC] = new HashMap();
        familyStyleCache[Typeface.BOLD] = new HashMap();
        familyStyleCache[Typeface.BOLD_ITALIC] = new HashMap();
    }

    public static Typeface getTypeface(Context context, String path) {
        // get from cache
        Typeface t = pathCache.get(path);
        if (t != null)
            return t;

        // Roboto?
        for (Roboto style : Roboto.values()) {
            if (style.getPath().equals(path)) {
                t = loadRoboto(context, style);
                if (t != null)
                    return t;
            }
        }

        // load from asset
        t = Typeface.createFromAsset(context.getAssets(), path);
        if (t != null) {
            pathCache.put(path, t);
            return t;
        }

        return Typeface.DEFAULT;
    }

    @SuppressWarnings("unchecked")
    public static Typeface getTypeface(Context context, String fontFamily, int textStyle) {
        // get from cache
        Typeface t = (Typeface) familyStyleCache[textStyle].get(fontFamily);
        if (t != null)
            return t;

        // Roboto?
        for (Roboto style : Roboto.values()) {
            if (style.getFontFamily().equals(fontFamily) && style.getTextStyle() == textStyle) {
                t = loadRoboto(context, style);
                if (t != null)
                    return t;
            }
        }

        // load from system res
        t = Typeface.create(fontFamily, textStyle);
        if (t != null) {
            familyStyleCache[textStyle].put(fontFamily, t);
            return t;
        }

        return Typeface.DEFAULT;
    }

    public static Typeface getTypeface(Context context, Roboto roboto) {
        // get from cache
        Typeface t = robotoCache.get(roboto);
        if (t != null)
            return t;

        t = loadRoboto(context, roboto);
        if (t != null)
            return t;

        return Typeface.DEFAULT;
    }

    @SuppressWarnings("unchecked")
    private static Typeface loadRoboto(Context context, Roboto roboto) {
        // try to load asset
        try {
            Typeface t = Typeface.createFromAsset(context.getAssets(), roboto.getPath());
            robotoCache.put(roboto, t);
            pathCache.put(roboto.getPath(), t);
            familyStyleCache[roboto.getTextStyle()].put(roboto.getFontFamily(), t);
            return t;
        } catch (Exception e) {
        }

        // try system font
        Typeface t = Typeface.create(roboto.getFontFamily(), roboto.getTextStyle());
        if (t != null) {
            robotoCache.put(roboto, t);
            pathCache.put(roboto.getPath(), t);
            familyStyleCache[roboto.getTextStyle()].put(roboto.getFontFamily(), t);
            return t;
        }

        return null;
    }
}
