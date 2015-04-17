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