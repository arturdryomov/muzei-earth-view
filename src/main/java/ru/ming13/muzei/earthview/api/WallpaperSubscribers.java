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

package ru.ming13.muzei.earthview.api;

import android.content.Context;
import androidx.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

import ru.ming13.muzei.earthview.util.Preferences;

final class WallpaperSubscribers
{
	private static final class DefaultSubscribers
	{
		private DefaultSubscribers() {
		}

		public static final String MUZEI = "net.nurik.roman.muzei";
	}

	private final Preferences preferences;

	private final Set<String> subscribers;

	public WallpaperSubscribers(@NonNull Context context) {
		this.preferences = Preferences.of(context);

		this.subscribers = preferences.getWallpaperSubscribers();
	}

	public void plus(@NonNull String subscriber) {
		subscribers.add(subscriber);

		preferences.setWallpaperSubscribers(subscribers);
	}

	public void minus(@NonNull String subscriber) {
		subscribers.remove(subscriber);

		preferences.setWallpaperSubscribers(subscribers);
	}

	public Set<String> get() {
		Set<String> subscribers = new HashSet<>(this.subscribers);
		subscribers.add(DefaultSubscribers.MUZEI);

		return subscribers;
	}
}
