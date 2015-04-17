package ru.ming13.muzei.earthview.api;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.Locale;

import ru.ming13.muzei.earthview.R;
import ru.ming13.muzei.earthview.util.Files;

final class WallpaperFiler
{
	private static final class Paths
	{
		private Paths() {
		}

		public static final String WALLPAPERS = "wallpapers";
	}

	private final Context context;

	public WallpaperFiler(@NonNull Context context) {
		this.context = context.getApplicationContext();
	}

	public void createFile(@NonNull String wallpaperId, @NonNull byte[] wallpaperBytes) {
		createDirectory();

		Files.write(getFile(wallpaperId), wallpaperBytes);
	}

	public void deleteFile(@NonNull String wallpaperId) {
		getFile(wallpaperId).delete();
	}

	public Uri getFileUri(@NonNull String wallpaperId) {
		return FileProvider.getUriForFile(context, context.getString(R.string.authority_files), getFile(wallpaperId));
	}

	private void createDirectory() {
		getDirectory().mkdirs();
	}

	private File getDirectory() {
		return new File(context.getFilesDir(), Paths.WALLPAPERS);
	}

	private File getFile(String wallpaperId) {
		return new File(getDirectory(), getFileName(wallpaperId));
	}

	private String getFileName(String wallpaperId) {
		return String.format(Locale.US, "%s.jpg", wallpaperId);
	}
}