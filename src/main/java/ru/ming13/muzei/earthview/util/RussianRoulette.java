package ru.ming13.muzei.earthview.util;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Random;

public final class RussianRoulette
{
	private final Random random;

	public RussianRoulette() {
		this.random = new Random();
	}

	public <T> T fire(@NonNull List<T> bullets) {
		return bullets.get(random.nextInt(bullets.size()));
	}
}
