/*
 * Copyright 2015 Artur Dryomov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ming13.muzei.earthview.util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public final class Preferences
{
	private static final class Locations
	{
		private Locations() {
		}

		public static final String STATE = "state";
	}

	private static final class Keys
	{
		private Keys() {
		}

		public static final String WALLPAPER_SUBSCRIBERS = "wallpaper_subscribers";
	}

	private static final class Defaults
	{
		private Defaults() {
		}

		public static final Set<String> SET = new HashSet<>();
	}

	private final SharedPreferences preferences;

	public static Preferences of(@NonNull Context context) {
		return new Preferences(context);
	}

	private Preferences(Context context) {
		this.preferences = context.getSharedPreferences(Locations.STATE, Context.MODE_PRIVATE);
	}

	public Set<String> getWallpaperSubscribers() {
		return preferences.getStringSet(Keys.WALLPAPER_SUBSCRIBERS, Defaults.SET);
	}

	public void setWallpaperSubscribers(@NonNull Set<String> subscribers) {
		preferences.edit().putStringSet(Keys.WALLPAPER_SUBSCRIBERS, subscribers).apply();
	}
}
