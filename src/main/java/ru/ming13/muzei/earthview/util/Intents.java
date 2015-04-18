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

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Locale;

public class Intents
{
	private static final class Uris
	{
		private Uris() {
		}

		public static final String GOOGLE_MAPS = "https://maps.google.com/maps?q=loc:%f,%f(%s)&z=%d&t=k";
	}

	private Intents() {
	}

	public static Intent getGoogleMapsIntent(@NonNull String title, double latitude, double longitude, int zoom) {
		String uri = String.format(Locale.US, Uris.GOOGLE_MAPS, latitude, longitude, title, zoom);

		return new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	}
}
