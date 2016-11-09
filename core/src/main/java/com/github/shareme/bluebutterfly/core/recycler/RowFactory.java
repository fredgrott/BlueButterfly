package com.github.shareme.bluebutterfly.core.recycler;

import android.view.ViewGroup;

/**
 * Created by Marcin on 2016-08-16.
 */

public interface RowFactory {
    Row create(ViewGroup parent);
}
