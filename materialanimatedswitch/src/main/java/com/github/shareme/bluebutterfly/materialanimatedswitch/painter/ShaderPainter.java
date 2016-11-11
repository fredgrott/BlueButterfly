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

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import com.github.shareme.bluebutterfly.materialanimatedswitch.MaterialAnimatedSwitchState;


/**
 * @author Adrián García Lomas
 */
@SuppressWarnings("unused")
public class ShaderPainter implements SwitchInboxPinnedPainter {

  private Paint maskPaint;
  private int height;
  private int width;
  private Bitmap mask;

  public ShaderPainter() {
    maskPaint = new Paint();
    maskPaint.setColor(Color.TRANSPARENT);
    maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    maskPaint.setStyle(Paint.Style.FILL);
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawLine(0, height / 2, width, height / 2, maskPaint);
  }

  @Override public void setColor(int color) {
    //Empty
  }

  @Override public int getColor() {
    return 0;
  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
    createMask(height, width, 10);
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    //Empty
  }

  private void createMask(int w, int h, int radius) {
    if (mask != null) {
      mask.recycle();
    }

    mask = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    Canvas maskCanvas = new Canvas(mask);
    maskCanvas.drawCircle(w, h, radius, maskPaint);
  }
}
