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
package com.github.shareme.bluebutterfly.core.widget.materialcalendarcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;


public class CheckableLayout extends com.github.shareme.bluebutterfly.core.widget.RelativeLayout implements Checkable {

    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };
    
    private boolean checked = false;

    @SuppressLint("NewApi")
    public CheckableLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CheckableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CheckableLayout(Context context) {
        super(context);
        init();
    }
    
    private void init() {
    	/*setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setChecked(!isChecked());
			}
		});*/
    }

    @Override
    public boolean isChecked() {
        return checked;
    }
    
    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        
        refreshDrawableState();
    
        //Propagate to childs
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if(child instanceof Checkable) {
                ((Checkable)child).setChecked(checked);
            }
        }
    }
    
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    public void toggle() {
        this.checked = !this.checked;
    }

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		//if (changed)
		//	getLayoutParams().height = r - l;
	}
}
