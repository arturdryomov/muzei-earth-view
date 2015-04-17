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

	private final Gson wallpaperIdsReader;

	public WallpaperIdsReader(@NonNull Context context) {
		this.assets = new Assets(context);

		this.wallpaperIdsReader = new Gson();
	}

	public List<String> getIds() {
		InputStream wallpaperIdsStream = assets.getStream(Assets.Names.WALLPAPERS);

		return Arrays.asList(wallpaperIdsReader.fromJson(new InputStreamReader(wallpaperIdsStream), String[].class));
	}
}
