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

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.shareme.bluebutterfly.materialanimatedswitch.MaterialAnimatedSwitchState;
import com.github.shareme.bluebutterfly.materialanimatedswitch.observer.BallMoveObservable;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Adrián García Lomas
 */
@SuppressWarnings("unused")
public class BasePainter implements SwitchInboxPinnedPainter, Observer {

  private Paint paint;
  private int bgColor;
  private int toBgColor;
  private int padding;
  private int height;
  private int width;
  private Paint toBgPainter;
  private ValueAnimator colorAnimator;
  private BallMoveObservable ballMoveObservable;

  public BasePainter(int bgColor, int toBgColor, int padding,
      BallMoveObservable ballMoveObservable) {
    this.bgColor = bgColor;
    this.toBgColor = toBgColor;
    this.padding = padding;
    this.ballMoveObservable = ballMoveObservable;
    init();
  }

  private void init() {
    paint = new Paint();
    paint.setColor(bgColor);
    paint.setStrokeCap(Paint.Cap.ROUND);
    paint.setAntiAlias(true);

    toBgPainter = new Paint();
    toBgPainter.setColor(toBgColor);
    toBgPainter.setStrokeCap(Paint.Cap.ROUND);
    toBgPainter.setAntiAlias(true);
    toBgPainter.setAlpha(0);

    initColorAnimator();
    ballMoveObservable.addObserver(this);
  }

  private void initColorAnimator() {
    colorAnimator = ValueAnimator.ofInt(0, 255);
    colorAnimator.setDuration(100);
    colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        toBgPainter.setAlpha((Integer) animation.getAnimatedValue());
      }
    });
  }

  @Override public void draw(Canvas canvas) {
    canvas.drawLine(padding, height / 2, width - padding, height / 2, paint);
    canvas.drawLine(padding, height / 2, width - padding, height / 2, toBgPainter);
  }

  @Override public int getColor() {
    return bgColor;
  }

  @Override public void setColor(int color) {

  }

  @Override public void onSizeChanged(int height, int width) {
    this.height = height;
    this.width = width;
    paint.setStrokeWidth(height / 2);
    toBgPainter.setStrokeWidth(height / 2);
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    //Empty
  }

  @Override public void update(Observable observable, Object data) {
    int value = ((BallMoveObservable) observable).getBallAnimationValue();
    colorAnimator.setCurrentPlayTime(value);
  }
}
