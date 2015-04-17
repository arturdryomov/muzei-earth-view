package ru.ming13.muzei.earthview.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.Set;

public final class Preferences
{
	private static final class Locations
	{
		private Locations() {
		}

		public static final String WALLPAPER_SUBSCRIBERS = "wallpaper_subscribers";
	}

	private static final class Keys
	{
		private Keys() {
		}

		public static final String WALLPAPER_SUBSCRIBERS = "wallpaper_subscribers";
	}

	private static final class Defaults
	{
		private Defaults() {
		}

		public static final Set<String> SET = new HashSet<>();
	}

	private final SharedPreferences preferences;

	public static Preferences of(@NonNull Context context) {
		return new Preferences(context);
	}

	private Preferences(Context context) {
		this.preferences = context.getSharedPreferences(Locations.WALLPAPER_SUBSCRIBERS, Context.MODE_PRIVATE);
	}

	public Set<String> getWallpaperSubscribers() {
		return preferences.getStringSet(Keys.WALLPAPER_SUBSCRIBERS, Defaults.SET);
	}

	public void setWallpaperSubscribers(@NonNull Set<String> subscribers) {
		preferences.edit().putStringSet(Keys.WALLPAPER_SUBSCRIBERS, subscribers).apply();
	}
}
