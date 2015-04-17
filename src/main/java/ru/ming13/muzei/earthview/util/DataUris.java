package ru.ming13.muzei.earthview.util;

import android.support.annotation.NonNull;

public final class DataUris
{
	// RFC 2397
	// data:[<MIME>][;charset=<encoding>][;base64],<data>

	private DataUris() {
	}

	public static String getData(@NonNull String dataUri) {
		return Strings.substringAfter(dataUri, ",");
	}
}