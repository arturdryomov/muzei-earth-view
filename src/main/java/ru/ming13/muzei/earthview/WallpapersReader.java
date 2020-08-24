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
