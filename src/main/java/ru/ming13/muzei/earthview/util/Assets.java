package ru.ming13.muzei.earthview.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

public final class Assets
{
	public static final class Files
	{
		private Files() {
		}

		public static final String WALLPAPERS = "wallpapers.json";
	}

	private final Context context;

	public Assets(Context context) {
		this.context = context.getApplicationContext();
	}

	public InputStream getStream(@NonNull String fileName) {
		try {
			return context.getAssets().open(fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}