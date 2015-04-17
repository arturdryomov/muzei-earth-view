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
