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
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import ru.ming13.muzei.earthview.util.Assets;

final class WallpaperIdsReader
{
	private final Assets assets;

	private final Gson jsonReader;

	public WallpaperIdsReader(@NonNull Context context) {
		this.assets = new Assets(context);

		this.jsonReader = new Gson();
	}

	public List<String> readIds() {
		InputStream wallpaperIdsStream = assets.getStream(Assets.Files.WALLPAPERS);

		return Arrays.asList(jsonReader.fromJson(new InputStreamReader(wallpaperIdsStream), String[].class));
	}
}
