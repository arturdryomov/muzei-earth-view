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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Strings
{
	private static final class Defaults
	{
		private Defaults() {
		}

		public static final String EMPTY = "";
	}

	private static final class Results
	{
		private Results() {
		}

		public static final int NOT_FOUND = -1;
	}

	private Strings() {
	}

	public static boolean isEmpty(@Nullable String string) {
		return (string == null) || (string.isEmpty());
	}

	public static String substringAfter(@NonNull String string, @NonNull String separator) {
		if (string.isEmpty()) {
			return string;
		}

		int separatorPosition = string.indexOf(separator);

		if (separatorPosition == Results.NOT_FOUND) {
			return Defaults.EMPTY;
		}

		return string.substring(separatorPosition + separator.length());
	}
}