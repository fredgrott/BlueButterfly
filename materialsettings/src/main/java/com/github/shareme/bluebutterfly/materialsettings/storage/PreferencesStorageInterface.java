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
package com.github.shareme.bluebutterfly.materialsettings.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by Kenumir on 2015-03-18.
 */
@SuppressWarnings("unused")
public class PreferencesStorageInterface extends StorageInterface  {

	private SharedPreferences prefs;

	public PreferencesStorageInterface(Context ctx) {
		prefs = ctx.getSharedPreferences(ctx.getPackageName() + "_materialsettings", Context.MODE_PRIVATE);
	}

	@Override
	public void save(String key, Boolean value) {
		prefs.edit().putBoolean(key, value).apply();
	}

	@Override
	public boolean load(String key, Boolean defaultValue) {
		return prefs.getBoolean(key, defaultValue);
	}

	@Override
	public void save(String key, String value) {
		prefs.edit().putString(key, value).apply();
	}

	@Override
	public String load(String key, String defaultValue) {
		return prefs.getString(key, defaultValue);
	}

	@Override
	public void save(String key, Integer value) {
		prefs.edit().putInt(key, value).apply();
	}

	@Override
	public Integer load(String key, Integer defaultValue) {
		return prefs.getInt(key, defaultValue);
	}

	@Override
	public void save(String key, Float value) {
		prefs.edit().putFloat(key, value).apply();
	}

	@Override
	public Float load(String key, Float defaultValue) {
		return prefs.getFloat(key, defaultValue);
	}

	@Override
	public void save(String key, Long value) {
		prefs.edit().putLong(key, value).apply();
	}

	@Override
	public Long load(String key, Long defaultValue) {
		return prefs.getLong(key, defaultValue);
	}

	@Override
	public Map<String, ?> getAll() {

		return prefs.getAll();
	}
}
