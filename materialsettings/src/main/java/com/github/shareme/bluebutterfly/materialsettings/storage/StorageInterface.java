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

import java.util.Map;

/**
 * Created by Kenumir on 2015-03-16.
 */
@SuppressWarnings("unused")
public abstract class StorageInterface {

	public StorageInterface() {}

	public abstract void save(String key, Boolean value);
	public abstract boolean load(String key, Boolean defaultValue);

	public abstract void save(String key, String value);
	public abstract String load(String key, String defaultValue);

	public abstract void save(String key, Integer value);
	public abstract Integer load(String key, Integer defaultValue);

	public abstract void save(String key, Float value);
	public abstract Float load(String key, Float defaultValue);

	public abstract void save(String key, Long value);
	public abstract Long load(String key, Long defaultValue);

	public abstract Map<String, ?> getAll();

}
