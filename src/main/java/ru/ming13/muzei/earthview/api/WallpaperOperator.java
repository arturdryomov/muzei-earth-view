package ru.ming13.muzei.earthview.api;

import android.support.annotation.NonNull;
import android.util.Base64;

import ru.ming13.muzei.earthview.model.Wallpaper;
import ru.ming13.muzei.earthview.util.DataUris;
import ru.ming13.muzei.earthview.util.Strings;

final class WallpaperOperator
{
	public byte[] getBytes(@NonNull Wallpaper wallpaper) {
		String bytesBase64 = DataUris.getData(wallpaper.getDataUri());

		return Base64.decode(bytesBase64, Base64.DEFAULT);
	}

	public String getTitle(@NonNull Wallpaper wallpaper) {
		if (Strings.isEmpty(wallpaper.getLocation().getRegion())) {
			return wallpaper.getLocation().getCountry();
		}

		return String.format("%s, %s", wallpaper.getLocation().getRegion(), wallpaper.getLocation().getCountry());
	}
}