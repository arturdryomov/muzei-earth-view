package ru.ming13.muzei.earthview.api;

import retrofit.http.GET;
import retrofit.http.Path;
import ru.ming13.muzei.earthview.model.Wallpaper;

interface WallpaperApi
{
	@GET("/{id}.json")
	Wallpaper getWallpaper(@Path("id") String wallpaperId);
}
