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

import java.util.Calendar;


public class CardGridItem {

	private Integer dayOfMonth;
	private Object data;
	private boolean enabled = true;
	private Calendar date;
	
	public CardGridItem(Integer dom) { 
		setDayOfMonth(dom);
	}

	public Integer getDayOfMonth() {
		return dayOfMonth;
	}

	public CardGridItem setDayOfMonth(Integer dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
		return this;
	}

	public Object getData() {
		return data;
	}

	public CardGridItem setData(Object data) {
		this.data = data;
		return this;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public CardGridItem setEnabled(boolean enabled) {
		this.enabled = enabled;
		return this;
	}

	public Calendar getDate() {
		return date;
	}

	public CardGridItem setDate(Calendar date) {
		this.date = date;
		return this;
	}
	
}
