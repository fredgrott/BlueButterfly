/*
Copyright 2015 AdriÃ¡n GarcÃ­a Lomas
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
package com.github.shareme.bluebutterfly.materialanimatedswitch.painter;

/**
 * Painter delegate the onDraw method in canvas to draw method here, each painter paints something
 * of the view
 *
 * @author Adrián García Lomas
 */

import android.graphics.Canvas;

@SuppressWarnings("unused")
public interface Painter<T extends Enum> {

  void draw(Canvas canvas);

  void setColor(int color);

  int getColor();

  void onSizeChanged(int height, int width);

  void setState(T state);
}
