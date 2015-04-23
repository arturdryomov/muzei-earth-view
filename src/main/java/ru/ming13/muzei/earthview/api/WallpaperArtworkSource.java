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

import android.content.ComponentName;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.ming13.muzei.earthview.model.Wallpaper;
import ru.ming13.muzei.earthview.util.RussianRoulette;
import ru.ming13.muzei.earthview.util.Strings;

public class WallpaperArtworkSource extends RemoteMuzeiArtSource
{
	private static final String NAME = "EarthViewArtSource";

	private static final class Configuration
	{
		private Configuration() {
		}

		public static final int UPDATE_TIME_IN_HOURS = 24;
	}

	private WallpaperClient wallpaperClient;
	private WallpaperOperator wallpaperOperator;
	private WallpaperFiler wallpaperFiler;
	private WallpaperIdsReader wallpaperIdsReader;
	private WallpaperSubscribers wallpaperSubscribers;

	public WallpaperArtworkSource() {
		super(NAME);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		this.wallpaperClient = new WallpaperClient();
		this.wallpaperOperator = new WallpaperOperator();
		this.wallpaperFiler = new WallpaperFiler(this);
		this.wallpaperIdsReader = new WallpaperIdsReader(this);
		this.wallpaperSubscribers = new WallpaperSubscribers(this);

		setUpFeatures();
	}

	private void setUpFeatures() {
		setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);
	}

	@Override
	protected void onTryUpdate(int reason) throws RetryException {
		String currentWallpaperId = getCurrentWallpaperId();

		Wallpaper wallpaper = getNextWallpaper(currentWallpaperId);

		if (wallpaper == null) {
			throw new RetryException();
		}

		wallpaperFiler.createFile(wallpaper.getId(), wallpaperOperator.getBytes(wallpaper));

		Artwork wallpaperArtwork = new Artwork.Builder()
			.title(wallpaperOperator.getTitle(wallpaper))
			.byline(wallpaperOperator.getDescription(wallpaper))
			.viewIntent(wallpaperOperator.getIntent(wallpaper))
			.imageUri(wallpaperFiler.getFileUri(wallpaper.getId(), wallpaperSubscribers.get()))
			.token(wallpaper.getId())
			.build();

		publishArtwork(wallpaperArtwork);

		if (!Strings.isEmpty(currentWallpaperId)) {
			wallpaperFiler.deleteFile(currentWallpaperId);
		}

		scheduleUpdate();
	}

	private String getCurrentWallpaperId() {
		Artwork currentWallpaperArtwork = getCurrentArtwork();

		if (currentWallpaperArtwork != null) {
			return currentWallpaperArtwork.getToken();
		} else {
			return null;
		}
	}

	private Wallpaper getNextWallpaper(String currentWallpaperId) {
		return wallpaperClient.getWallpaper(getRandomWallpaperId(currentWallpaperId));
	}

	private String getRandomWallpaperId(String currentWallpaperId) {
		RussianRoulette russianRoulette = new RussianRoulette();

		List<String> wallpaperIds = wallpaperIdsReader.readIds();

		String randomWallpaperId = russianRoulette.fire(wallpaperIds);

		while (randomWallpaperId.equals(currentWallpaperId)) {
			randomWallpaperId = russianRoulette.fire(wallpaperIds);
		}

		return randomWallpaperId;
	}

	private void scheduleUpdate() {
		scheduleUpdate(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(Configuration.UPDATE_TIME_IN_HOURS));
	}

	@Override
	protected void onSubscriberAdded(ComponentName subscriber) {
		super.onSubscriberAdded(subscriber);

		wallpaperSubscribers.plus(subscriber.getPackageName());
	}

	@Override
	protected void onSubscriberRemoved(ComponentName subscriber) {
		super.onSubscriberRemoved(subscriber);

		wallpaperSubscribers.minus(subscriber.getPackageName());
	}
}
