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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kenumir on 2015-03-16.
 */
@SuppressWarnings("unused")
public class SimpleStorageInterface extends StorageInterface {

	private HashMap<String, Object> mem = new HashMap<>();

	public SimpleStorageInterface() {

	}

	@Override
	public Map<String, ?> getAll() {
		return mem;
	}

	@Override
	public void save(String key, Boolean value) {
		mem.put(key, value);
	}

	@Override
	public boolean load(String key, Boolean defaultValue) {
		if (mem.containsKey(key))
			return (boolean) mem.get(key);
		return defaultValue;
	}

	@Override
	public void save(String key, String value) {
		mem.put(key, String.valueOf(value));
	}

	@Override
	public String load(String key, String defaultValue) {
		if (mem.containsKey(key))
			return (String) mem.get(key);
		return defaultValue;
	}

	@Override
	public void save(String key, Integer value) {
		mem.put(key, value);
	}

	@Override
	public Integer load(String key, Integer defaultValue) {
		if (mem.containsKey(key))
			return (Integer) mem.get(key);
		return defaultValue;
	}

	@Override
	public void save(String key, Float value) {
		mem.put(key, value);
	}

	@Override
	public Float load(String key, Float defaultValue) {
		if (mem.containsKey(key))
			return (Float) mem.get(key);
		return defaultValue;
	}

	@Override
	public void save(String key, Long value) {
		mem.put(key, value);
	}

	@Override
	public Long load(String key, Long defaultValue) {
		if (mem.containsKey(key))
			return (Long) mem.get(key);
		return defaultValue;
	}

}
