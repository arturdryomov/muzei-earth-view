package ru.ming13.muzei.earthview.util;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Locale;

public class Intents
{
	private static final class Uris
	{
		private Uris() {
		}

		public static final String GOOGLE_MAPS = "https://maps.google.com/maps?q=loc:%f,%f(%s)&z=%d&t=k";
	}

	private Intents() {
	}

	public static Intent getGoogleMapsIntent(@NonNull String title, double latitude, double longitude, int zoom) {
		String uri = String.format(Locale.US, Uris.GOOGLE_MAPS, latitude, longitude, title, zoom);

		return new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
	}
}
