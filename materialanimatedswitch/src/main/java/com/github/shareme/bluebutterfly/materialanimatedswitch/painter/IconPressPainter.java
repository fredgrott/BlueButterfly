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
import android.content.Context;
import android.graphics.Bitmap;

import com.github.shareme.bluebutterfly.materialanimatedswitch.MaterialAnimatedSwitchState;
import com.github.shareme.bluebutterfly.materialanimatedswitch.R;
import com.github.shareme.bluebutterfly.materialanimatedswitch.observer.BallFinishObservable;
import com.github.shareme.bluebutterfly.materialanimatedswitch.observer.BallMoveObservable;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Adrián García Lomas
 */
@SuppressWarnings("unused")
public class IconPressPainter extends IconPainter {

  private ValueAnimator enterAnimator;
  private ValueAnimator exitAnimator;
  private BallFinishObservable ballFinishObservable;
  private int enterAnimationStartValue;
  private int exitAnimationExitValue;
  private int middle;
  private int iconMargin;
  private int ballRadius;
  private BallMoveObservable ballMoveObservable;

  public IconPressPainter(Context context, Bitmap bitmap, BallFinishObservable ballFinishObservable,
                          BallMoveObservable ballMoveObservable, int margin) {
    super(context, bitmap, margin);
    initValueAnimator();
    this.ballFinishObservable = ballFinishObservable;
    this.ballMoveObservable = ballMoveObservable;
    initObserver();
  }

  private void initValueAnimator() {
    int movementAnimationDuration = context.getResources().getInteger(R.integer.animation_duration);
    int exitAnimationDuration = context.getResources().getInteger(R.integer.exitAnimator);

    enterAnimator = ValueAnimator.ofInt();
    enterAnimator.setDuration(movementAnimationDuration);
    enterAnimator.addUpdateListener(new EnterValueAnimatorListener());

    exitAnimator = ValueAnimator.ofInt();
    exitAnimator.setDuration(exitAnimationDuration);
    exitAnimator.addUpdateListener(new ExitValueAnimatorListener());
  }

  @Override public void onSizeChanged(int height, int width) {
    super.onSizeChanged(height, width);
    initValues();
    int iconCenterY = middle - (iconMargin);
    initAnimationValues();
    enterAnimator.setIntValues(enterAnimationStartValue, iconCenterY);
    exitAnimator.setIntValues(iconCenterY, iconCenterY + exitAnimationExitValue);
  }

  private void initValues() {
    middle = height / 2;
    iconMargin = imageWidth / 2;
    ballRadius = (int) context.getResources().getDimension(R.dimen.ball_radius);
    iconXPosition = (width - ballRadius) + iconMargin;
  }

  private void initAnimationValues() {
    enterAnimationStartValue =
        (int) context.getResources().getDimension(R.dimen.enterAnimationStartValue);
    exitAnimationExitValue =
        (int) context.getResources().getDimension(R.dimen.exitAnimationExitValue);
  }

  @Override public void setColor(int color) {
    //Empty
  }

  @Override public int getColor() {
    return 0;
  }

  private void initObserver() {
    ballFinishObservable.addObserver(new BallFinishListener());
    ballMoveObservable.addObserver(new BallMoveListener());
  }

  @Override public void setState(MaterialAnimatedSwitchState state) {
    switch (state) {
      case PRESS:
        isVisible = true;
        enterAnimator.start();
        break;
      case RELEASE:
        exitAnimator.start();
    }
  }

  private class BallFinishListener implements Observer {

    @Override public void update(Observable observable, Object data) {
      BallFinishObservable.BallState ballState = ((BallFinishObservable) observable).getState();
      switch (ballState) {
        case RELEASE:
          isVisible = false;
          break;
      }
    }
  }

  /**
   * Listener for move the icon with the ball movement
   */
  private class BallMoveListener implements Observer {

    @Override public void update(Observable observable, Object data) {
      BallMoveObservable ballMoveObservable = (BallMoveObservable) observable;
      int ballPositionX = ballMoveObservable.getBallPosition();
      iconXPosition = ballPositionX - iconMargin;
    }
  }

  private class EnterValueAnimatorListener implements ValueAnimator.AnimatorUpdateListener {
    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconYPosition = (int) animation.getAnimatedValue();
    }
  }

  private class ExitValueAnimatorListener implements ValueAnimator.AnimatorUpdateListener {

    @Override public void onAnimationUpdate(ValueAnimator animation) {
      iconYPosition = (int) animation.getAnimatedValue();
    }
  }
}
