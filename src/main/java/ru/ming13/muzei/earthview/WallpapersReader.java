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

package ru.ming13.muzei.earthview;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

final class WallpapersReader
{
	private static final String ASSET_PATH = "wallpapers.json";

	private final Context context;
	private final Gson gson;

	WallpapersReader(Context context) {
		this.context = context.getApplicationContext();
		this.gson = new Gson();
	}

	List<Wallpaper> readWallpapers() {
		try {
			InputStream stream = context.getAssets().open(ASSET_PATH);

			return Arrays.asList(gson.fromJson(new BufferedReader(new InputStreamReader(stream)), Wallpaper[].class));
		} catch (IOException e) {
			throw new RuntimeException("Assets reading failed.", e);
		}
	}
}
