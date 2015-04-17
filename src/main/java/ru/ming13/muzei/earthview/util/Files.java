package ru.ming13.muzei.earthview.util;

import android.support.annotation.NonNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class Files
{
	private Files() {
	}

	public static void write(@NonNull File file, @NonNull byte[] bytes) {
		try {
			OutputStream fileStream = new BufferedOutputStream(new FileOutputStream(file));

			fileStream.write(bytes, 0, bytes.length);

			fileStream.flush();
			fileStream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}