package ru.ming13.muzei.earthview.model;

import com.google.gson.annotations.SerializedName;

public final class Wallpaper
{
	@SerializedName("id")
	private String id;

	@SerializedName("dataUri")
	private String dataUri;

	@SerializedName("geocode")
	private Location location;

	public String getId() {
		return id;
	}

	public String getDataUri() {
		return dataUri;
	}

	public Location getLocation() {
		return location;
	}
}