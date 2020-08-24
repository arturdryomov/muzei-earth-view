package ru.ming13.muzei.earthview;

import com.google.gson.annotations.SerializedName;

final class Wallpaper
{
	@SerializedName("id")
	String id;

	@SerializedName("country")
	String country;

	@SerializedName("region")
	String region;

	@SerializedName("file_url")
	String fileUrl;

	@SerializedName("maps_url")
	String mapsUrl;

	@SerializedName("attribution")
	String attribution;
}
