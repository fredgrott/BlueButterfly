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

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.shareme.bluebutterfly.materialsettings.MaterialSettingsFragment;
import com.github.shareme.bluebutterfly.materialsettings.MaterialSettingsItem;
import com.github.shareme.bluebutterfly.materialsettings.R;


/**
 * Created by Kenumir on 2015-03-16.
 */
@SuppressWarnings("unused")
public class TextItem extends MaterialSettingsItem {

	public static interface OnClickListener {
		public void onClick(TextItem item);
	}
	private String title, subtitle;
	private OnClickListener onclick;
	private TextView titleView, subtitleView;
	private ImageView image;
	private Drawable icon;
	private int iconRes = 0;

	public TextItem(MaterialSettingsFragment ctx, String name) {
		super(ctx, name);
	}

	@Override
	public int getViewResource() {
		return R.layout.item_text;
	}

	@Override
	public void setupView(View v) {
		titleView = (TextView) v.findViewById(R.id.material_settings_text_title);
		subtitleView = (TextView) v.findViewById(R.id.material_settings_text_subtitle);
		image = (ImageView) v.findViewById(R.id.material_settings_text_icon);

		updateTitle(title);
		updateSubTitle(subtitle);
		if (iconRes > 0) {
			updateIcon(iconRes);
		} else if (icon != null) {
			updateIcon(icon);
		} else {
			image.setVisibility(View.GONE);
		}

		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getOnclick() != null)
					getOnclick().onClick(TextItem.this);
			}
		});
	}

	public TextItem setIcon(int icon) {
		iconRes = icon;
		return this;
	}

	public TextItem setIcon(Drawable icon) {
		this.icon = icon;
		return this;
	}

	@Override
	public void save() {
		// NOP
	}

	public TextItem updateTitle(String newTitle) {
		if (titleView != null)
			titleView.setText(newTitle);
		return this;
	}

	public TextItem updateSubTitle(String newSubTitle) {
		if (subtitleView != null) {
			subtitleView.setText(newSubTitle);
			subtitleView.setVisibility(subtitle != null && subtitle.trim().length() > 0 ? View.VISIBLE : View.GONE);
		}
		return this;
	}

	public TextItem updateIcon(int icon) {
		this.iconRes = icon;
		this.image.setImageResource(icon);
		this.image.setVisibility(View.VISIBLE);
		return this;
	}

	public TextItem updateIcon(Drawable icon) {
		this.icon = icon;
		this.image.setImageDrawable(icon);
		this.image.setVisibility(View.VISIBLE);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public TextItem setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public TextItem setSubtitle(String subtitle) {
		this.subtitle = subtitle;
		return this;
	}

	public OnClickListener getOnclick() {
		return onclick;
	}

	public TextItem setOnclick(OnClickListener onclick) {
		this.onclick = onclick;
		return this;
	}
}
