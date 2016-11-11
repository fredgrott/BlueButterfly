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
package com.github.shareme.bluebutterfly.materialsettings.items;

import android.view.View;
import android.widget.TextView;

import com.github.shareme.bluebutterfly.materialsettings.MaterialSettingsFragment;
import com.github.shareme.bluebutterfly.materialsettings.MaterialSettingsItem;
import com.github.shareme.bluebutterfly.materialsettings.R;


/**
 * Created by Kenumir on 2015-03-16.
 */
@SuppressWarnings("unused")
public class HeaderItem extends MaterialSettingsItem {

	private String title;

	public HeaderItem(MaterialSettingsFragment ctx) {
		super(ctx, null);
	}

	@Override
	public int getViewResource() {
		return R.layout.item_header;
	}

	@Override
	public void setupView(View v) {
		((TextView) v).setText(title);
	}

	@Override
	public void save() {
		// NOP
	}

	public String getTitle() {
		return title;
	}

	public HeaderItem setTitle(String title) {
		this.title = title;
		return this;
	}
}
