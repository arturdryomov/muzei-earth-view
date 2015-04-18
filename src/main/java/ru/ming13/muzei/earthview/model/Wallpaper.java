package ru.ming13.muzei.earthview.model;

import com.google.gson.annotations.SerializedName;

public final class Wallpaper
{
	@SerializedName("id")
	private String id;

	@SerializedName("dataUri")
	private String dataUri;

	@SerializedName("attribution")
	private String attribution;

	@SerializedName("geocode")
	private Area area;

	@SerializedName("lat")
	private double latitude;

	@SerializedName("lng")
	private double longitude;

	@SerializedName("zoom")
	private int zoom;

	public String getId() {
		return id;
	}

	public String getDataUri() {
		return dataUri;
	}

	public String getAttribution() {
		return attribution;
	}

	public Area getArea() {
		return area;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public int getZoom() {
		return zoom;
	}
}