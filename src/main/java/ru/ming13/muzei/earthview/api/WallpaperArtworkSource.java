package ru.ming13.muzei.earthview.api;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.ming13.muzei.earthview.model.Wallpaper;
import ru.ming13.muzei.earthview.util.RussianRoulette;
import ru.ming13.muzei.earthview.util.Strings;

public class WallpaperArtworkSource extends RemoteMuzeiArtSource
{
	private static final String NAME = "EarthViewArtSource";

	private static final class Options
	{
		private Options() {
		}

		public static final int UPDATE_TIME_IN_HOURS = 4;
	}

	private final WallpaperClient wallpaperClient;
	private final WallpaperOperator wallpaperOperator;
	private final WallpaperFiler wallpaperFiler;
	private final WallpaperIdsReader wallpaperIdsReader;

	public WallpaperArtworkSource() {
		super(NAME);

		this.wallpaperClient = new WallpaperClient();
		this.wallpaperOperator = new WallpaperOperator();
		this.wallpaperFiler = new WallpaperFiler(this);
		this.wallpaperIdsReader = new WallpaperIdsReader(this);

		setUpFeatures();
	}

	private void setUpFeatures() {
		setUserCommands(BUILTIN_COMMAND_ID_NEXT_ARTWORK);
	}

	@Override
	protected void onTryUpdate(int reason) throws RetryException {
		String currentWallpaperId = getCurrentWallpaperId();

		Wallpaper wallpaper = getNextWallpaper(currentWallpaperId);

		if (wallpaper == null) {
			throw new RetryException();
		}

		wallpaperFiler.createFile(wallpaper.getId(), wallpaperOperator.getBytes(wallpaper));

		Artwork wallpaperArtwork = new Artwork.Builder()
			.title(wallpaperOperator.getTitle(wallpaper))
			.imageUri(wallpaperFiler.getFileUri(wallpaper.getId()))
			.token(wallpaper.getId())
			.build();

		publishArtwork(wallpaperArtwork);

		if (!Strings.isEmpty(currentWallpaperId)) {
			wallpaperFiler.deleteFile(currentWallpaperId);
		}

		scheduleUpdate();
	}

	private String getCurrentWallpaperId() {
		Artwork currentWallpaperArtwork = getCurrentArtwork();

		if (currentWallpaperArtwork != null) {
			return currentWallpaperArtwork.getToken();
		} else {
			return null;
		}
	}

	private Wallpaper getNextWallpaper(String currentWallpaperId) {
		try {
			return wallpaperClient.getWallpaper(getRandomWallpaperId(currentWallpaperId));
		} catch (RuntimeException e) {
			return null;
		}
	}

	private String getRandomWallpaperId(String currentWallpaperId) {
		RussianRoulette russianRoulette = new RussianRoulette();

		List<String> wallpaperIds = wallpaperIdsReader.getIds();

		String randomWallpaperId = russianRoulette.pickElement(wallpaperIds);

		while (randomWallpaperId.equals(currentWallpaperId)) {
			randomWallpaperId = russianRoulette.pickElement(wallpaperIds);
		}

		return randomWallpaperId;
	}

	private void scheduleUpdate() {
		scheduleUpdate(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(Options.UPDATE_TIME_IN_HOURS));
	}
}
