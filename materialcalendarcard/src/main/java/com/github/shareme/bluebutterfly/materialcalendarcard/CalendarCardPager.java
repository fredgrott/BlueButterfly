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
package com.github.shareme.bluebutterfly.materialcalendarcard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

@SuppressWarnings("unused")
public class CalendarCardPager extends com.github.shareme.bluebutterfly.core.widget.ViewPager {
	
	private CardPagerAdapter mCardPagerAdapter;
	private OnCellItemClick mOnCellItemClick;
	
	public CalendarCardPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		init(context);
	}

	public CalendarCardPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public CalendarCardPager(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		 mCardPagerAdapter = new CardPagerAdapter(context);
		 setAdapter(mCardPagerAdapter);
	}
	
	public CardPagerAdapter getCardPagerAdapter() {
		return mCardPagerAdapter;
	}

	public OnCellItemClick getOnCellItemClick() {
		return mOnCellItemClick;
	}

	public void setOnCellItemClick(OnCellItemClick mOnCellItemClick) {
		this.mOnCellItemClick = mOnCellItemClick;
		mCardPagerAdapter.setDefaultOnCellItemClick(this.mOnCellItemClick);
		if (getChildCount() > 0) {
			for(int i=0; i<getChildCount(); i++) {
				View v = getChildAt(i);
				if (v instanceof CalendarCard) {
					((CalendarCard) v).setOnCellItemClick(this.mOnCellItemClick);
				}
			}
		}
	}

}
