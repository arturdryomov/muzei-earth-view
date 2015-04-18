/*
 * Copyright 2015 Artur Dryomov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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