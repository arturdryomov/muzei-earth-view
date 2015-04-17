package ru.ming13.muzei.earthview.api;

import android.support.annotation.NonNull;

import retrofit.RestAdapter;
import ru.ming13.muzei.earthview.model.Wallpaper;

final class WallpaperClient
{
	private final WallpaperApi api;

	public WallpaperClient() {
		this.api = createApi();
	}

	private WallpaperApi createApi() {
		RestAdapter apiAdapter = new RestAdapter.Builder()
			.setEndpoint(WallpaperUrls.ENDPOINT)
			.build();

		return apiAdapter.create(WallpaperApi.class);
	}

	public Wallpaper getWallpaper(@NonNull String wallpaperId) {
		return api.getWallpaper(wallpaperId);
	}
}