/*
Copyright 2013-2015 MichaÅ‚ Szwarc
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
package com.github.shareme.bluebutterfly.materialsettings.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.github.shareme.bluebutterfly.materialsettings.R;


@SuppressWarnings("unused")
public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };
    
    private static final int[] DISABLED_STATE_SET = {
        -android.R.attr.state_enabled
    };
    
    private boolean checked = false, autoCheck = false;
	private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    @SuppressLint("NewApi")
    public CheckableLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckableLinearLayout);
        checked = ta.getBoolean(R.styleable.CheckableLinearLayout_marked, false);
	    autoCheck = ta.getBoolean(R.styleable.CheckableLinearLayout_autocheck, false);
        ta.recycle();
	    init();
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CheckableLinearLayout);
        checked = ta.getBoolean(R.styleable.CheckableLinearLayout_marked, false);
	    autoCheck = ta.getBoolean(R.styleable.CheckableLinearLayout_autocheck, false);
        ta.recycle();
	    init();
    }
	
	private void init() {
		if (autoCheck) {
			setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					toggle();
				}
			});
		}
	}

	// get all touch events
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}

    @Override
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);
    	refreshDrawableState();
    }

    public CheckableLinearLayout(Context context) {
        super(context);
    }

    @Override
    public boolean isChecked() {
        return checked;
    }
    
    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        
        refreshDrawableState();
    
        //Propagate to child's
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if(child instanceof Checkable) {
                ((Checkable)child).setChecked(checked);
            } else {
	            if (child instanceof ViewGroup) {
		            int c = ((ViewGroup) child).getChildCount();
		            for (int ii = 0; ii < c; ii++) {
			            final View cc = ((ViewGroup) child).getChildAt(ii);
			            if(cc instanceof Checkable) {
				            ((Checkable) cc).setChecked(checked);
			            }
		            }
	            }
            }
        }
	    
	    if (getOnCheckedChangeListener() != null)
		    getOnCheckedChangeListener().onCheckedChanged(null, this.checked);
    }
    
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isEnabled()) {
	        if (isChecked()) {
	            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
	        }
        } else {
        	mergeDrawableStates(drawableState, DISABLED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void toggle() {
        this.checked = !this.checked;
	    setChecked(this.checked);
    }

	public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener) {
		this.mOnCheckedChangeListener = mOnCheckedChangeListener;
	}

	public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
		return mOnCheckedChangeListener;
	}
}
