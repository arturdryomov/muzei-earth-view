package ru.ming13.muzei.earthview.model;

import com.google.gson.annotations.SerializedName;

public final class Location
{
	@SerializedName("locality")
	private String region;

	@SerializedName("country")
	private String country;

	public String getRegion() {
		return region;
	}

	public String getCountry() {
		return country;
	}
}