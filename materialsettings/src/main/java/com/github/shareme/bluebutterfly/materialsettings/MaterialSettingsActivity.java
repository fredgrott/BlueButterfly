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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.github.shareme.bluebutterfly.materialsettings.storage.SimpleStorageInterface;
import com.github.shareme.bluebutterfly.materialsettings.storage.StorageInterface;

import java.util.Map;

/**
 * Created by Kenumir on 2015-04-07.
 */
@SuppressWarnings("unused")
public abstract class MaterialSettingsActivity extends AppCompatActivity {

	private static String SAVE_PREFIX = "SSI_";

	private MaterialSettingsFragment fragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_material_settings_base);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		fragment = (MaterialSettingsFragment) getFragmentManager().findFragmentById(R.id.material_settings_fragment);

		if (savedInstanceState != null) {
			StorageInterface mStorageInterface = fragment.getStorageInterface();
			for(String key : savedInstanceState.keySet()) {
				if (key.startsWith(SAVE_PREFIX)) {
					String keyName = key.substring(SAVE_PREFIX.length());
					Object value = savedInstanceState.get(key);

					if (value instanceof String) {
						mStorageInterface.save(keyName, (String) value);
					} else if (value instanceof Integer) {
						mStorageInterface.save(keyName, (Integer) value);
					} else if (value instanceof Float) {
						mStorageInterface.save(keyName, (Float) value);
					} else if (value instanceof Long) {
						mStorageInterface.save(keyName, (Long) value);
					} else if (value instanceof Boolean) {
						mStorageInterface.save(keyName, (Boolean) value);
					} else {
						mStorageInterface.save(keyName, value.toString());
					}
				}
			}
		}
	}

	//public abstract MaterialSettingsFragment initMaterialSettingsFragment();

	@SuppressWarnings("ConstantConditions")
	@Override
	public void onSaveInstanceState(Bundle outState) {
		StorageInterface si = getStorageInterface();
		if (si instanceof SimpleStorageInterface) {
			saveAll();
			Map<String, ?> all = ((SimpleStorageInterface) si).getAll();
			if (all.size() > 0) {
				// save to bundle
				for(String key : all.keySet()) {
					Object value = all.get(key);
					if (value instanceof String) {
						outState.putString(SAVE_PREFIX + key, (String) value);
					} else if (value instanceof Integer) {
						outState.putInt(SAVE_PREFIX + key, (Integer) value);
					} else if (value instanceof Float) {
						outState.putFloat(SAVE_PREFIX + key, (Float) value);
					} else if (value instanceof Long) {
						outState.putString(SAVE_PREFIX + key, (String) value);
					} else if (value instanceof Boolean) {
						outState.putBoolean(SAVE_PREFIX + key, (Boolean) value);
					} else {
						outState.putString(SAVE_PREFIX + key, value.toString());
					}
				}
			}
		}
		super.onSaveInstanceState(outState);
	}

	public void addItem(MaterialSettingsItem item) {
		if (fragment != null)
			fragment.addItem(item);
	}

	public void saveAll() {
		if (fragment != null) {
			fragment.saveAll();
		}
	}

	public MaterialSettingsItem getItem(String keyName) {
		if (fragment != null) {
			return fragment.getItem(keyName);
		} else {
			return null;
		}
	}

	public StorageInterface getStorageInterface() {
		if (fragment != null) {
			return fragment.getStorageInterface();
		} else {
			return null;
		}
	}

	public MaterialSettingsFragment getFragment() {
		return fragment;
	}

	public FrameLayout getContentFrame(MaterialSettingsFragment.ContentFrames frame) {
		if (fragment != null)
			return fragment.getContentFrame(frame);
		else
			return null;
	}

	public StorageInterface initStorageInterface() {
		return null;
	}
}
