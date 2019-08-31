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

import android.content.Intent;
import android.util.Base64;
import androidx.annotation.NonNull;

import java.util.Locale;

import ru.ming13.muzei.earthview.model.Wallpaper;
import ru.ming13.muzei.earthview.util.DataUris;
import ru.ming13.muzei.earthview.util.Intents;
import ru.ming13.muzei.earthview.util.Strings;

final class WallpaperOperator
{
	public String getTitle(@NonNull Wallpaper wallpaper) {
		if (Strings.isEmpty(wallpaper.getArea().getRegion())) {
			return wallpaper.getArea().getCountry();
		}

		return String.format(Locale.US, "%s, %s", wallpaper.getArea().getRegion(), wallpaper.getArea().getCountry());
	}

	public String getDescription(@NonNull Wallpaper wallpaper) {
		return wallpaper.getAttribution().replace("\u00a9", "\u00a9 ");
	}

	public byte[] getBytes(@NonNull Wallpaper wallpaper) {
		return Base64.decode(DataUris.getData(wallpaper.getDataUri()), Base64.DEFAULT);
	}

	public Intent getIntent(@NonNull Wallpaper wallpaper) {
		return Intents.getGoogleMapsIntent(getTitle(wallpaper), wallpaper.getLatitude(), wallpaper.getLongitude(), wallpaper.getZoom());
	}
}