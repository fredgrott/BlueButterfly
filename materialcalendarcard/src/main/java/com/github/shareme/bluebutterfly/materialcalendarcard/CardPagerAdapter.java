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
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.Calendar;

@SuppressWarnings("unused")
public class CardPagerAdapter extends PagerAdapter {
	
	private Context mContext;
	private OnCellItemClick defaultOnCellItemClick;

	public CardPagerAdapter(Context ctx) {
		mContext = ctx;
	}

	@Override
	public Object instantiateItem(View collection, final int position) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, position);
		CalendarCard card = new CalendarCard(mContext);
		card.setDateDisplay(cal);
		card.notifyChanges();
		if (card.getOnCellItemClick() == null)
			card.setOnCellItemClick(defaultOnCellItemClick);

		((ViewPager) collection).addView(card,0);

		return card;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==((View)object);
	}

	@Override
	public void finishUpdate(View arg0) {}
	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {}
	@Override
	public Parcelable saveState() { return null; }
	@Override
	public void startUpdate(View arg0) {}

	@Override
	public int getCount() {
		// TODO almoast ifinite ;-)
		return Integer.MAX_VALUE;
	}

	public OnCellItemClick getDefaultOnCellItemClick() {
		return defaultOnCellItemClick;
	}

	public void setDefaultOnCellItemClick(OnCellItemClick defaultOnCellItemClick) {
		this.defaultOnCellItemClick = defaultOnCellItemClick;
	}

}
