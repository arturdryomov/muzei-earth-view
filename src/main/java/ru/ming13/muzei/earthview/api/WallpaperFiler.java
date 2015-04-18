/*
 * Copyright 2015 Artur Dryomov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ming13.muzei.earthview.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.Locale;
import java.util.Set;

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

	public void deleteFile(@NonNull String wallpaperId) {
		getFile(wallpaperId).delete();
	}

	public Uri getFileUri(@NonNull String wallpaperId, @NonNull Set<String> wallpaperSubscribers) {
		Uri fileUri = FileProvider.getUriForFile(context, context.getString(R.string.authority_files), getFile(wallpaperId));

		for (String wallpaperSubscriber : wallpaperSubscribers) {
			context.grantUriPermission(wallpaperSubscriber, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
		}

		return fileUri;
	}
}