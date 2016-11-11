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

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;

import com.github.shareme.bluebutterfly.materialanimatedswitch.observer.BallFinishObservable;
import com.github.shareme.bluebutterfly.materialanimatedswitch.observer.BallMoveObservable;


/**
 * @author Adrián García Lomas
 */
@SuppressWarnings("unused")
public class BallShadowPainter extends BallPainter {

  public BallShadowPainter(int bgColor, int toBgColor, int padding, int shadowColor,
                           BallFinishObservable ballFinishObservable, BallMoveObservable ballMoveObservable,
                           Context context) {
    super(bgColor, toBgColor, padding, ballFinishObservable, ballMoveObservable, context);
    paint.setColor(shadowColor);
    paint.setMaskFilter(new BlurMaskFilter(3, BlurMaskFilter.Blur.NORMAL));
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawCircle(ballPositionX, (height / 2) + 2, radius , paint);
  }
}
