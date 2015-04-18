package ru.ming13.muzei.earthview.api;

import android.content.ComponentName;

import com.google.android.apps.muzei.api.Artwork;
import com.google.android.apps.muzei.api.RemoteMuzeiArtSource;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import ru.ming13.muzei.earthview.model.Wallpaper;
import ru.ming13.muzei.earthview.util.Preferences;
import ru.ming13.muzei.earthview.util.RussianRoulette;
import ru.ming13.muzei.earthview.util.Strings;

public class WallpaperArtworkSource extends RemoteMuzeiArtSource
{
	private static final String NAME = "EarthViewArtSource";

	private static final class Configuration
	{
		private Configuration() {
		}

		public static final int UPDATE_TIME_IN_HOURS = 1;
	}

	private WallpaperClient wallpaperClient;
	private WallpaperOperator wallpaperOperator;
	private WallpaperFiler wallpaperFiler;
	private WallpaperIdsReader wallpaperIdsReader;

	private Set<String> wallpaperSubscribers;

	public WallpaperArtworkSource() {
		super(NAME);
	}

	@Override
	public void onCreate() {
		super.onCreate();

		this.wallpaperClient = new WallpaperClient();
		this.wallpaperOperator = new WallpaperOperator();
		this.wallpaperFiler = new WallpaperFiler(this);
		this.wallpaperIdsReader = new WallpaperIdsReader(this);

		this.wallpaperSubscribers = Preferences.of(this).getWallpaperSubscribers();

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
			.byline(wallpaperOperator.getDescription(wallpaper))
			.imageUri(wallpaperFiler.getFileUri(wallpaper.getId(), wallpaperSubscribers))
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
		return wallpaperClient.getWallpaper(getRandomWallpaperId(currentWallpaperId));
	}

	private String getRandomWallpaperId(String currentWallpaperId) {
		RussianRoulette russianRoulette = new RussianRoulette();

		List<String> wallpaperIds = wallpaperIdsReader.readIds();

		String randomWallpaperId = russianRoulette.fire(wallpaperIds);

		while (randomWallpaperId.equals(currentWallpaperId)) {
			randomWallpaperId = russianRoulette.fire(wallpaperIds);
		}

		return randomWallpaperId;
	}

	private void scheduleUpdate() {
		scheduleUpdate(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(Configuration.UPDATE_TIME_IN_HOURS));
	}

	@Override
	protected void onSubscriberAdded(ComponentName subscriber) {
		super.onSubscriberAdded(subscriber);

		wallpaperSubscribers.add(subscriber.getPackageName());

		Preferences.of(this).setWallpaperSubscribers(wallpaperSubscribers);
	}

	@Override
	protected void onSubscriberRemoved(ComponentName subscriber) {
		super.onSubscriberRemoved(subscriber);

		wallpaperSubscribers.remove(subscriber.getPackageName());

		Preferences.of(this).setWallpaperSubscribers(wallpaperSubscribers);
	}
}
