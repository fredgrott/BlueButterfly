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
package com.github.shareme.bluebutterfly.materialsettings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.shareme.bluebutterfly.materialsettings.storage.StorageInterface;


/**
 * Created by Kenumir on 2015-03-16.
 */
@SuppressWarnings("unused")
public abstract class MaterialSettingsItem {

	protected MaterialSettingsFragment mContext;
	protected MaterialSettingsFragment mMaterialSettings;
	protected String name;

	public MaterialSettingsItem(MaterialSettingsFragment ctx, String name) {
		this.mContext = ctx;
		//if (ctx instanceof MaterialSettingsFragment)
		//	this.mMaterialSettings = (MaterialSettingsFragment) ctx;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public View initView(ViewGroup parent, int res) {
		return LayoutInflater.from(mContext.getActivity()).inflate(res, parent, false);
	}

	public View getView(ViewGroup parent) {
		if (getViewResource() > 0) {
			View v = initView(parent, getViewResource());
			setupView(v);
			return v;
		} else
			return null;
	}

	public void setMaterialSettings(MaterialSettingsFragment m) {
		mMaterialSettings = m;
	}

	public StorageInterface getStorageInterface() {
		if (mMaterialSettings != null)
			return mMaterialSettings.getStorageInterface();
		else
			return null;
	}

	public abstract int getViewResource();
	public abstract void setupView(View v);
	public abstract void save();

}
