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
package com.github.shareme.bluebutterfly.core;

import android.view.MotionEvent;

/**
 * Created by Marcin on 2014-12-20.
 */
@SuppressWarnings("unused")
public interface OnGestureListener {
    void onPress(MotionEvent motionEvent);

    void onTap(MotionEvent motionEvent);

    void onDrag(MotionEvent motionEvent);

    void onMove(MotionEvent motionEvent);

    void onRelease(MotionEvent motionEvent);

    void onLongPress(MotionEvent motionEvent);

    void onMultiTap(MotionEvent motionEvent, int clicks);

    void onCancel(MotionEvent motionEvent);
}
