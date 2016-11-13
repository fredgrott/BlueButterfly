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
package com.github.shareme.bluebutterfly.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.github.shareme.bluebutterfly.core.R;


public class TextMarker extends View {
    Paint paint;
    Rect rect = new Rect();
    String text = "I";
    private int id;

    public TextMarker(Context context) {
        super(context);
    }

    public TextMarker(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs, 0);
    }

    public TextMarker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextMarker, defStyleAttr, 0);

            for (int i = 0; i < a.getIndexCount(); i++) {
                int attr = a.getIndex(i);
                if (attr == R.styleable.TextMarker_carbon_text) {
                    setText(a.getText(attr).toString());
                } else if (attr == R.styleable.TextMarker_carbon_textView) {
                    id = a.getResourceId(attr, 0);
                }
            }

            a.recycle();
        }
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (paint == null && id != 0) {
            paint = ((android.widget.TextView) getRootView().findViewById(id)).getPaint();
            paint.getTextBounds(text, 0, text.length(), rect);
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(rect.width(), MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(rect.height(), MeasureSpec.EXACTLY));
    }

    @Override
    public int getBaseline() {
        return getHeight();
    }

}