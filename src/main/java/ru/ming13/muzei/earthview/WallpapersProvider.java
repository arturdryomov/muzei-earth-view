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

import android.net.Uri;

import com.google.android.apps.muzei.api.provider.Artwork;
import com.google.android.apps.muzei.api.provider.MuzeiArtProvider;

import java.util.ArrayList;
import java.util.List;

public final class WallpapersProvider extends MuzeiArtProvider
{
	@Override
	protected void onLoadRequested(boolean initial) {
		List<Wallpaper> wallpapers = new WallpapersReader(getContext()).readWallpapers();
		List<Artwork> artworks = new ArrayList<>(wallpapers.size());

		for (Wallpaper wallpaper : wallpapers) {
			Artwork artwork = new Artwork.Builder()
				.token(wallpaper.id)
				.title(wallpaper.region.isEmpty() ? wallpaper.country : wallpaper.region)
				.byline(wallpaper.region.isEmpty() ? null : wallpaper.country)
				.persistentUri(Uri.parse(wallpaper.fileUrl))
				.webUri(Uri.parse(wallpaper.mapsUrl))
				.attribution(wallpaper.attribution)
				.build();

			artworks.add(artwork);
		}

		setArtwork(artworks);
	}
}
