package ru.ming13.muzei.earthview.api;

import android.support.annotation.NonNull;
import android.util.Base64;

import java.util.Locale;

import ru.ming13.muzei.earthview.model.Wallpaper;
import ru.ming13.muzei.earthview.util.DataUris;
import ru.ming13.muzei.earthview.util.Strings;

final class WallpaperOperator
{
	public byte[] getBytes(@NonNull Wallpaper wallpaper) {
		return Base64.decode(DataUris.getData(wallpaper.getDataUri()), Base64.DEFAULT);
	}

	public String getTitle(@NonNull Wallpaper wallpaper) {
		if (Strings.isEmpty(wallpaper.getArea().getRegion())) {
			return wallpaper.getArea().getCountry();
		}

		return String.format(Locale.US, "%s, %s", wallpaper.getArea().getRegion(), wallpaper.getArea().getCountry());
	}

	public String getDescription(@NonNull Wallpaper wallpaper) {
		return wallpaper.getAttribution();
	}
}