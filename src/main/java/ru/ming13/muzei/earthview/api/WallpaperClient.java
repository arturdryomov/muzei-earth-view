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

import androidx.annotation.NonNull;

import retrofit2.Retrofit;
import ru.ming13.muzei.earthview.model.Wallpaper;

final class WallpaperClient
{
	private final WallpaperApi api;

	public WallpaperClient() {
		this.api = createApi();
	}

	private WallpaperApi createApi() {
		Retrofit retrofit = new Retrofit.Builder()
			.baseUrl(WallpaperUrls.ENDPOINT)
			.build();

		return retrofit.create(WallpaperApi.class);
	}

	public Wallpaper getWallpaper(@NonNull String wallpaperId) {
		try {
			return api.getWallpaper(wallpaperId);
		} catch (RuntimeException e) {
			return null;
		}
	}
}