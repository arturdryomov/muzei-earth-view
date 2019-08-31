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