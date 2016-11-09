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
