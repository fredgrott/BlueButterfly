package com.github.shareme.bluebutterfly.core.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;

import com.github.shareme.bluebutterfly.core.Carbon;
import com.github.shareme.bluebutterfly.core.R;
import com.github.shareme.bluebutterfly.core.animation.AnimUtils;
import com.github.shareme.bluebutterfly.core.animation.AnimatedColorStateList;
import com.github.shareme.bluebutterfly.core.animation.StateAnimator;
import com.github.shareme.bluebutterfly.core.drawable.DefaultPrimaryColorStateList;
import com.github.shareme.bluebutterfly.core.drawable.ripple.RippleDrawable;
import com.github.shareme.bluebutterfly.core.drawable.ripple.RippleView;
import com.github.shareme.bluebutterfly.core.internal.SeekBarPopup;

import static com.github.shareme.bluebutterfly.core.animation.AnimatorProxy.NEEDS_PROXY;
import static com.github.shareme.bluebutterfly.core.animation.AnimatorProxy.wrap;


/**
 * Created by Marcin on 2015-06-25.
 */
public class SeekBar extends View implements RippleView, StateAnimatorView, AnimatedView, TintedView {
    private static float THUMB_RADIUS, THUMB_RADIUS_DRAGGED, STROKE_WIDTH;
    float value = 0.5f;
    float min = 0, max = 1, step = 1;
    float thumbRadius;
    int tickStep = 1;
    boolean tick = true;
    int tickColor = 0;
    boolean showLabel;
    String labelFormat;

    SeekBarPopup popup;

    OnValueChangedListener onValueChangedListener;

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private int colorControl;

    private Style style;

    DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private ValueAnimator radiusAnimator, valueAnimator;

    public enum Style {
        Continuous, Discrete
    }

    public interface OnValueChangedListener {
        void onValueChanged(SeekBar seekBar, float value);
    }

    public SeekBar(Context context) {
        super(context);
        initSeekBar(null, android.R.attr.seekBarStyle);
    }

    public SeekBar(Context context, AttributeSet attrs) {
        super(Carbon.getThemedContext(context, attrs, R.styleable.SeekBar, android.R.attr.seekBarStyle, R.styleable.SeekBar_carbon_theme), attrs);
        initSeekBar(attrs, android.R.attr.seekBarStyle);
    }

    public SeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(Carbon.getThemedContext(context, attrs, R.styleable.SeekBar, defStyleAttr, R.styleable.SeekBar_carbon_theme), attrs, defStyleAttr);
        initSeekBar(attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(Carbon.getThemedContext(context, attrs, R.styleable.SeekBar, defStyleAttr, R.styleable.SeekBar_carbon_theme), attrs, defStyleAttr, defStyleRes);
        initSeekBar(attrs, defStyleAttr);
    }

    private static int[] rippleIds = new int[]{
            R.styleable.SeekBar_carbon_rippleColor,
            R.styleable.SeekBar_carbon_rippleStyle,
            R.styleable.SeekBar_carbon_rippleHotspot,
            R.styleable.SeekBar_carbon_rippleRadius
    };
    private static int[] animationIds = new int[]{
            R.styleable.SeekBar_carbon_inAnimation,
            R.styleable.SeekBar_carbon_outAnimation
    };
    private static int[] tintIds = new int[]{
            R.styleable.SeekBar_carbon_tint,
            R.styleable.SeekBar_carbon_tintMode,
            R.styleable.SeekBar_carbon_backgroundTint,
            R.styleable.SeekBar_carbon_backgroundTintMode,
            R.styleable.SeekBar_carbon_animateColorChanges
    };

    private void initSeekBar(AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode())
            return;

        colorControl = Carbon.getThemeColor(getContext(), R.attr.colorControlNormal);

        thumbRadius = THUMB_RADIUS = Carbon.getDip(getContext()) * 8;
        THUMB_RADIUS_DRAGGED = Carbon.getDip(getContext()) * 10;
        STROKE_WIDTH = Carbon.getDip(getContext()) * 2;

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SeekBar, defStyleAttr, R.style.carbon_SeekBar);

        setStyle(Style.values()[a.getInt(R.styleable.SeekBar_carbon_barStyle, 0)]);
        setMin(a.getFloat(R.styleable.SeekBar_carbon_min, 0));
        setMax(a.getFloat(R.styleable.SeekBar_carbon_max, 0));
        setStepSize(a.getFloat(R.styleable.SeekBar_carbon_stepSize, 0));
        setValue(a.getFloat(R.styleable.SeekBar_carbon_value, 0));
        setTick(a.getBoolean(R.styleable.SeekBar_carbon_tick, true));
        setTickStep(a.getInt(R.styleable.SeekBar_carbon_tickStep, 1));
        setTickColor(a.getColor(R.styleable.SeekBar_carbon_tickColor, 0));
        setShowLabel(a.getBoolean(R.styleable.SeekBar_carbon_showLabel, false));
        setLabelFormat(a.getString(R.styleable.SeekBar_carbon_labelFormat));

        Carbon.initAnimations((com.github.shareme.bluebutterfly.core.animation.AnimatedView) this, a, animationIds);
        Carbon.initTint(this, a, tintIds);
        Carbon.initRippleDrawable(this, a, rippleIds);

        a.recycle();

        setFocusableInTouchMode(false); // TODO: from theme
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return Math.max((int) Math.ceil(THUMB_RADIUS_DRAGGED * 2), super.getSuggestedMinimumWidth());
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int) Math.ceil(THUMB_RADIUS_DRAGGED * 2), super.getSuggestedMinimumHeight());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = getSuggestedMinimumWidth();
        int desiredHeight = getSuggestedMinimumHeight();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (!changed)
            return;

        if (getWidth() == 0 || getHeight() == 0)
            return;

        if (rippleDrawable != null)
            rippleDrawable.setBounds(0, 0, getWidth(), getHeight());
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        super.draw(canvas);

        float v = (value - min) / (max - min);
        int thumbX = (int) (v * (getWidth() - getPaddingLeft() - getPaddingRight()) + getPaddingLeft());
        int thumbY = getHeight() / 2;

        paint.setStrokeWidth(STROKE_WIDTH);
        if (!isInEditMode())
            paint.setColor(tint.getColorForState(getDrawableState(), tint.getDefaultColor()));
        if (getPaddingLeft() < thumbX - thumbRadius)
            canvas.drawLine(getPaddingLeft(), thumbY, thumbX - thumbRadius, thumbY, paint);

        paint.setColor(colorControl);
        if (thumbX + thumbRadius < getWidth() - getPaddingLeft())
            canvas.drawLine(thumbX + thumbRadius, thumbY, getWidth() - getPaddingLeft(), thumbY, paint);

        if (style == Style.Discrete && tick) {
            paint.setColor(tickColor);
            float range = (max - min) / step;
            for (int i = 0; i < range; i += tickStep)
                canvas.drawCircle(i / range * (getWidth() - getPaddingLeft() - getPaddingRight()) + getPaddingLeft(), getHeight() / 2, STROKE_WIDTH / 2, paint);
            canvas.drawCircle(getWidth() - getPaddingRight(), getHeight() / 2, STROKE_WIDTH / 2, paint);
        }

        if (!isInEditMode())
            paint.setColor(tint.getColorForState(getDrawableState(), tint.getDefaultColor()));
        canvas.drawCircle(thumbX, thumbY, thumbRadius, paint);

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Over)
            rippleDrawable.draw(canvas);
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
        if (showLabel)
            popup = new SeekBarPopup(getContext());
    }

    public boolean getShowLabel() {
        return showLabel;
    }

    public void setLabelFormat(String format) {
        labelFormat = format;
    }

    public String getLabelFormat() {
        return labelFormat;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        if (max > min) {
            this.max = max;
        } else {
            this.max = min + step;
        }
        this.value = Math.max(min, Math.min(value, max));
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        if (min < max) {
            this.min = min;
        } else if (this.max > step) {
            this.min = max - step;
        } else {
            this.min = 0;
        }
        this.value = Math.max(min, Math.min(value, max));
    }

    private int stepValue(float v) {
        return (int) (Math.floor((v - min + step / 2) / step) * step + min);
    }

    public float getValue() {
        if (style == Style.Discrete)
            return stepValue(value);
        return value;
    }

    public void setValue(float value) {
        if (style == Style.Discrete) {
            this.value = stepValue(Math.max(min, Math.min(value, max)));
        } else {
            this.value = Math.max(min, Math.min(value, max));
        }
    }

    public float getStepSize() {
        return step;
    }

    public void setStepSize(float step) {
        if (step > 0) {
            this.step = step;
        } else {
            this.step = 1;
        }
    }

    public boolean hasTick() {
        return tick;
    }

    public void setTick(boolean tick) {
        this.tick = tick;
    }

    public int getTickStep() {
        return tickStep;
    }

    public void setTickStep(int tickStep) {
        this.tickStep = tickStep;
    }

    public int getTickColor() {
        return tickColor;
    }

    public void setTickColor(int tickColor) {
        this.tickColor = tickColor;
    }

    public void setOnValueChangedListener(OnValueChangedListener onValueChangedListener) {
        this.onValueChangedListener = onValueChangedListener;
    }


    // -------------------------------
    // ripple
    // -------------------------------

    private RippleDrawable rippleDrawable;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (radiusAnimator != null)
                radiusAnimator.end();
            radiusAnimator = ValueAnimator.ofFloat(thumbRadius, THUMB_RADIUS_DRAGGED);
            radiusAnimator.setDuration(200);
            radiusAnimator.setInterpolator(interpolator);
            radiusAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    thumbRadius = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            radiusAnimator.start();
            ViewParent parent = getParent();
            if (parent != null)
                parent.requestDisallowInterceptTouchEvent(true);
            if (showLabel)
                popup.show(this);
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP) {
            if (style == Style.Discrete) {
                float val = (float) Math.floor((value - min + step / 2) / step) * step + min;
                if (valueAnimator != null)
                    valueAnimator.cancel();
                valueAnimator = ValueAnimator.ofFloat(value, val);
                valueAnimator.setDuration(200);
                valueAnimator.setInterpolator(interpolator);
                valueAnimator.addUpdateListener(animation -> {
                    value = (float) animation.getAnimatedValue();
                    int thumbX = (int) ((value - min) / (max - min) * (getWidth() - getPaddingLeft() - getPaddingRight()) + getPaddingLeft());
                    int thumbY = getHeight() / 2;
                    int radius = rippleDrawable.getRadius();
                    rippleDrawable.setBounds(thumbX - radius, thumbY - radius, thumbX + radius, thumbY + radius);
                    postInvalidate();
                });
                valueAnimator.start();
            }
            if (radiusAnimator != null)
                radiusAnimator.end();
            radiusAnimator = ValueAnimator.ofFloat(thumbRadius, THUMB_RADIUS);
            radiusAnimator.setDuration(200);
            radiusAnimator.setInterpolator(interpolator);
            radiusAnimator.addUpdateListener(animation -> {
                thumbRadius = (float) animation.getAnimatedValue();
                postInvalidate();
            });
            radiusAnimator.start();
            ViewParent parent = getParent();
            if (parent != null)
                parent.requestDisallowInterceptTouchEvent(false);
            if (showLabel)
                popup.dismiss();
        }

        float v = (event.getX() - getPaddingLeft()) / (getWidth() - getPaddingLeft() - getPaddingRight());
        v = Math.max(0, Math.min(v, 1));
        float newValue = v * (max - min) + min;

        int thumbX = (int) (v * (getWidth() - getPaddingLeft() - getPaddingRight()) + getPaddingLeft());
        int thumbY = getHeight() / 2;
        int radius = rippleDrawable.getRadius();

        if (showLabel) {
            int[] location = new int[2];
            getLocationInWindow(location);
            popup.setText(String.format(labelFormat, newValue));
            popup.update(thumbX + location[0] - popup.getBubbleWidth() / 2, thumbY - radius + location[1] - popup.getHeight());
        }

        if (rippleDrawable != null) {
            rippleDrawable.setHotspot(event.getX(), event.getY());
            rippleDrawable.setBounds(thumbX - radius, thumbY - radius, thumbX + radius, thumbY + radius);
        }

        postInvalidate();
        if (newValue != value && onValueChangedListener != null) {
            if (style == Style.Discrete) {
                int sv = stepValue(newValue);
                if (stepValue(value) != sv)
                    onValueChangedListener.onValueChanged(this, sv);
            } else {
                onValueChangedListener.onValueChanged(this, newValue);
            }
        }
        value = newValue;
        super.onTouchEvent(event);
        return true;
    }

    @Override
    public RippleDrawable getRippleDrawable() {
        return rippleDrawable;
    }

    public void setRippleDrawable(RippleDrawable newRipple) {
        if (rippleDrawable != null) {
            rippleDrawable.setCallback(null);
            if (rippleDrawable.getStyle() == RippleDrawable.Style.Background)
                super.setBackgroundDrawable(rippleDrawable.getBackground());
        }

        if (newRipple != null) {
            newRipple.setCallback(this);
            newRipple.setBounds(0, 0, getWidth(), getHeight());
            if (newRipple.getStyle() == RippleDrawable.Style.Background)
                super.setBackgroundDrawable((Drawable) newRipple);
        }

        rippleDrawable = newRipple;
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || rippleDrawable == who;
    }

    @Override
    public void invalidateDrawable(@NonNull Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).invalidate();
    }

    @Override
    public void invalidate(@NonNull Rect dirty) {
        super.invalidate(dirty);
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).invalidate(dirty);
    }

    @Override
    public void invalidate(int l, int t, int r, int b) {
        super.invalidate(l, t, r, b);
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).invalidate(l, t, r, b);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).invalidate();
    }

    @Override
    public void postInvalidateDelayed(long delayMilliseconds) {
        super.postInvalidateDelayed(delayMilliseconds);
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).postInvalidateDelayed(delayMilliseconds);
    }

    @Override
    public void postInvalidateDelayed(long delayMilliseconds, int left, int top, int right, int bottom) {
        super.postInvalidateDelayed(delayMilliseconds, left, top, right, bottom);
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).postInvalidateDelayed(delayMilliseconds, left, top, right, bottom);
    }

    @Override
    public void postInvalidate() {
        super.postInvalidate();
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).postInvalidate();
    }

    @Override
    public void postInvalidate(int left, int top, int right, int bottom) {
        super.postInvalidate(left, top, right, bottom);
        if (getParent() == null || !(getParent() instanceof View))
            return;

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Borderless)
            ((View) getParent()).postInvalidate(left, top, right, bottom);
    }

    @Override
    public void setBackground(Drawable background) {
        setBackgroundDrawable(background);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        if (background instanceof RippleDrawable) {
            setRippleDrawable((RippleDrawable) background);
            return;
        }

        if (rippleDrawable != null && rippleDrawable.getStyle() == RippleDrawable.Style.Background) {
            rippleDrawable.setCallback(null);
            rippleDrawable = null;
        }
        super.setBackgroundDrawable(background);
    }


    // -------------------------------
    // state animators
    // -------------------------------

    private StateAnimator stateAnimator = new StateAnimator((com.github.shareme.bluebutterfly.core.animation.AnimatedView) this);

    @Override
    public StateAnimator getStateAnimator() {
        return stateAnimator;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (rippleDrawable != null && rippleDrawable.getStyle() != RippleDrawable.Style.Background)
            rippleDrawable.setState(getDrawableState());
        if (stateAnimator != null)
            stateAnimator.setState(getDrawableState());
    }


    // -------------------------------
    // animations
    // -------------------------------

    private AnimUtils.Style inAnim = AnimUtils.Style.None, outAnim = AnimUtils.Style.None;
    private Animator animator;

    public void setVisibility(final int visibility) {
        if (visibility == View.VISIBLE && (getVisibility() != View.VISIBLE || animator != null)) {
            if (animator != null)
                animator.cancel();
            if (inAnim != AnimUtils.Style.None) {
                animator = AnimUtils.animateIn(this, inAnim, new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator a) {
                        animator = null;
                        clearAnimation();
                    }
                });
            }
            super.setVisibility(visibility);
        } else if (visibility != View.VISIBLE && (getVisibility() == View.VISIBLE || animator != null)) {
            if (animator != null)
                animator.cancel();
            if (outAnim == AnimUtils.Style.None) {
                super.setVisibility(visibility);
                return;
            }
            animator = AnimUtils.animateOut(this, outAnim, new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator a) {
                    if (((ValueAnimator) a).getAnimatedFraction() == 1)
                        SeekBar.super.setVisibility(visibility);
                    animator = null;
                    clearAnimation();
                }
            });
        }
    }

    public void setVisibilityImmediate(final int visibility) {
        super.setVisibility(visibility);
    }

    public Animator getAnimator() {
        return animator;
    }

    public AnimUtils.Style getOutAnimation() {
        return outAnim;
    }

    public void setOutAnimation(AnimUtils.Style outAnim) {
        this.outAnim = outAnim;
    }

    public AnimUtils.Style getInAnimation() {
        return inAnim;
    }

    public void setInAnimation(AnimUtils.Style inAnim) {
        this.inAnim = inAnim;
    }


    // -------------------------------
    // tint
    // -------------------------------

    ColorStateList tint;
    PorterDuff.Mode tintMode;
    ColorStateList backgroundTint;
    PorterDuff.Mode backgroundTintMode;
    boolean animateColorChanges;
    ValueAnimator.AnimatorUpdateListener tintAnimatorListener = animation -> {
        updateTint();
        ViewCompat.postInvalidateOnAnimation(SeekBar.this);
    };
    ValueAnimator.AnimatorUpdateListener backgroundTintAnimatorListener = animation -> {
        updateBackgroundTint();
        ViewCompat.postInvalidateOnAnimation(SeekBar.this);
    };

    @Override
    public void setTint(ColorStateList list) {
        this.tint = animateColorChanges && !(list instanceof AnimatedColorStateList) ? AnimatedColorStateList.fromList(list, tintAnimatorListener) : list;
        updateTint();
    }

    @Override
    public void setTint(int color) {
        if (color == 0) {
            setTint(new DefaultPrimaryColorStateList(getContext()));
        } else {
            setTint(ColorStateList.valueOf(color));
        }
    }

    @Override
    public ColorStateList getTint() {
        return tint;
    }

    private void updateTint() {
        postInvalidate();
    }

    @Override
    public void setTintMode(@NonNull PorterDuff.Mode mode) {
        this.tintMode = mode;
        updateTint();
    }

    @Override
    public PorterDuff.Mode getTintMode() {
        return tintMode;
    }

    @Override
    public void setBackgroundTint(ColorStateList list) {
        this.backgroundTint = animateColorChanges && !(list instanceof AnimatedColorStateList) ? AnimatedColorStateList.fromList(list, backgroundTintAnimatorListener) : list;
        updateBackgroundTint();
    }

    @Override
    public void setBackgroundTint(int color) {
        if (color == 0) {
            setBackgroundTint(new DefaultPrimaryColorStateList(getContext()));
        } else {
            setBackgroundTint(ColorStateList.valueOf(color));
        }
    }

    @Override
    public ColorStateList getBackgroundTint() {
        return backgroundTint;
    }

    private void updateBackgroundTint() {
        if (getBackground() == null)
            return;
        if (backgroundTint != null && backgroundTintMode != null) {
            int color = backgroundTint.getColorForState(getDrawableState(), backgroundTint.getDefaultColor());
            getBackground().setColorFilter(new PorterDuffColorFilter(color, tintMode));
        } else {
            getBackground().setColorFilter(null);
        }
    }

    @Override
    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        this.backgroundTintMode = mode;
        updateBackgroundTint();
    }

    @Override
    public PorterDuff.Mode getBackgroundTintMode() {
        return backgroundTintMode;
    }

    public boolean isAnimateColorChangesEnabled() {
        return animateColorChanges;
    }

    public void setAnimateColorChangesEnabled(boolean animateColorChanges) {
        this.animateColorChanges = animateColorChanges;
        if (tint != null && !(tint instanceof AnimatedColorStateList))
            setTint(AnimatedColorStateList.fromList(tint, tintAnimatorListener));
        if (backgroundTint != null && !(backgroundTint instanceof AnimatedColorStateList))
            setBackgroundTint(AnimatedColorStateList.fromList(backgroundTint, backgroundTintAnimatorListener));
    }


    // -------------------------------
    // transformations
    // -------------------------------

    public float getAlpha() {
        return NEEDS_PROXY ? wrap(this).getAlpha() : super.getAlpha();
    }

    public void setAlpha(float alpha) {
        if (NEEDS_PROXY) {
            wrap(this).setAlpha(alpha);
        } else {
            super.setAlpha(alpha);
        }
    }

    public float getPivotX() {
        return NEEDS_PROXY ? wrap(this).getPivotX() : super.getPivotX();
    }

    public void setPivotX(float pivotX) {
        if (NEEDS_PROXY) {
            wrap(this).setPivotX(pivotX);
        } else {
            super.setPivotX(pivotX);
        }
    }

    public float getPivotY() {
        return NEEDS_PROXY ? wrap(this).getPivotY() : super.getPivotY();
    }

    public void setPivotY(float pivotY) {
        if (NEEDS_PROXY) {
            wrap(this).setPivotY(pivotY);
        } else {
            super.setPivotY(pivotY);
        }
    }

    public float getRotation() {
        return NEEDS_PROXY ? wrap(this).getRotation() : super.getRotation();
    }

    public void setRotation(float rotation) {
        if (NEEDS_PROXY) {
            wrap(this).setRotation(rotation);
        } else {
            super.setRotation(rotation);
        }
    }

    public float getRotationX() {
        return NEEDS_PROXY ? wrap(this).getRotationX() : super.getRotationX();
    }

    public void setRotationX(float rotationX) {
        if (NEEDS_PROXY) {
            wrap(this).setRotationX(rotationX);
        } else {
            super.setRotationX(rotationX);
        }
    }

    public float getRotationY() {
        return NEEDS_PROXY ? wrap(this).getRotationY() : super.getRotationY();
    }

    public void setRotationY(float rotationY) {
        if (NEEDS_PROXY) {
            wrap(this).setRotationY(rotationY);
        } else {
            super.setRotationY(rotationY);
        }
    }

    public float getScaleX() {
        return NEEDS_PROXY ? wrap(this).getScaleX() : super.getScaleX();
    }

    public void setScaleX(float scaleX) {
        if (NEEDS_PROXY) {
            wrap(this).setScaleX(scaleX);
        } else {
            super.setScaleX(scaleX);
        }
    }

    public float getScaleY() {
        return NEEDS_PROXY ? wrap(this).getScaleY() : super.getScaleY();
    }

    public void setScaleY(float scaleY) {
        if (NEEDS_PROXY) {
            wrap(this).setScaleY(scaleY);
        } else {
            super.setScaleY(scaleY);
        }
    }

    public float getTranslationX() {
        return NEEDS_PROXY ? wrap(this).getTranslationX() : super.getTranslationX();
    }

    public void setTranslationX(float translationX) {
        if (NEEDS_PROXY) {
            wrap(this).setTranslationX(translationX);
        } else {
            super.setTranslationX(translationX);
        }
    }

    public float getTranslationY() {
        return NEEDS_PROXY ? wrap(this).getTranslationY() : super.getTranslationY();
    }

    public void setTranslationY(float translationY) {
        if (NEEDS_PROXY) {
            wrap(this).setTranslationY(translationY);
        } else {
            super.setTranslationY(translationY);
        }
    }

    public float getX() {
        return NEEDS_PROXY ? wrap(this).getX() : super.getX();
    }

    public void setX(float x) {
        if (NEEDS_PROXY) {
            wrap(this).setX(x);
        } else {
            super.setX(x);
        }
    }

    public float getY() {
        return NEEDS_PROXY ? wrap(this).getY() : super.getY();
    }

    public void setY(float y) {
        if (NEEDS_PROXY) {
            wrap(this).setY(y);
        } else {
            super.setY(y);
        }
    }
}
