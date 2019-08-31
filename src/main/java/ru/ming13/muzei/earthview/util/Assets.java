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

package ru.ming13.muzei.earthview.util;

import android.content.Context;
import androidx.annotation.NonNull;

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

	public Assets(@NonNull Context context) {
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