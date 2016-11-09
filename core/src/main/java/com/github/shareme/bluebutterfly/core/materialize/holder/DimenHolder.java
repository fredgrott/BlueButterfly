package com.github.shareme.bluebutterfly.core.materialize.holder;

import android.content.Context;
import android.support.annotation.DimenRes;

import com.github.shareme.bluebutterfly.core.materialize.util.UIUtils;


/**
 * Created by mikepenz on 13.07.15.
 */
public class DimenHolder {
    private int mPixel = Integer.MIN_VALUE;
    private int mDp = Integer.MIN_VALUE;
    private int mResource = Integer.MIN_VALUE;

    public DimenHolder() {

    }

    public int getPixel() {
        return mPixel;
    }

    public void setPixel(int mPixel) {
        this.mPixel = mPixel;
    }

    public int getDp() {
        return mDp;
    }

    public void setDp(int mDp) {
        this.mDp = mDp;
    }

    public int getResource() {
        return mResource;
    }

    public void setResource(int mResource) {
        this.mResource = mResource;
    }

    public static DimenHolder fromPixel(int pixel) {
        DimenHolder dimenHolder = new DimenHolder();
        dimenHolder.mPixel = pixel;
        return dimenHolder;
    }

    public static DimenHolder fromDp(int dp) {
        DimenHolder dimenHolder = new DimenHolder();
        dimenHolder.mDp = dp;
        return dimenHolder;
    }

    public static DimenHolder fromResource(@DimenRes int resource) {
        DimenHolder dimenHolder = new DimenHolder();
        dimenHolder.mResource = resource;
        return dimenHolder;
    }

    public int asPixel(Context ctx) {
        if (mPixel != Integer.MIN_VALUE) {
            return mPixel;
        } else if (mDp != Integer.MIN_VALUE) {
            return (int) UIUtils.convertDpToPixel(mDp, ctx);
        } else if (mResource != Integer.MIN_VALUE) {
            return ctx.getResources().getDimensionPixelSize(mResource);
        }
        return 0;
    }
}
