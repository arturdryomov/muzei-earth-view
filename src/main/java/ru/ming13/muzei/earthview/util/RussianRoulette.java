package ru.ming13.muzei.earthview.util;

import java.util.List;
import java.util.Random;

public final class RussianRoulette
{
	private final Random random;

	public RussianRoulette() {
		this.random = new Random();
	}

	public <T> T pickElement(List<T> elements) {
		return elements.get(random.nextInt(elements.size()));
	}
}
