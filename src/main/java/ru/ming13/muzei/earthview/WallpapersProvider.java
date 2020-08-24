package ru.ming13.muzei.earthview;

import android.net.Uri;

import com.google.android.apps.muzei.api.provider.Artwork;
import com.google.android.apps.muzei.api.provider.MuzeiArtProvider;

import java.util.ArrayList;
import java.util.List;

public final class WallpapersProvider extends MuzeiArtProvider
{
	@Override
	public void onLoadRequested(boolean initial) {
		List<Wallpaper> wallpapers = new WallpapersReader(getContext()).readWallpapers();
		List<Artwork> artworks = new ArrayList<>(wallpapers.size());

		for (Wallpaper wallpaper : wallpapers) {
			Artwork artwork = new Artwork.Builder()
				.token(wallpaper.id)
				.title(wallpaper.region.isEmpty() ? wallpaper.country : wallpaper.region)
				.byline(wallpaper.region.isEmpty() ? null : wallpaper.country)
				.persistentUri(Uri.parse(wallpaper.fileUrl))
				.webUri(Uri.parse(wallpaper.mapsUrl))
				.attribution(wallpaper.attribution)
				.build();

			artworks.add(artwork);
		}

		setArtwork(artworks);
	}
}
