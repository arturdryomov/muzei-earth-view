#!/usr/bin/env python3
# -*- coding: utf8 -*-

import json
import os
import urllib.request


FILE_NAME_WALLPAPER_IDS = "wallpaper_ids.json"
FILE_NAME_WALLPAPERS = "wallpapers.json"


DEFAULT_COUNTRY = ""
DEFAULT_REGION = ""


def wallpaper(wallpaper_id):
    print(":: {}".format(wallpaper_id))

    # There are two image sources.
    #
    # * Chrome: https://chrome.google.com/webstore/detail/bhloflhklmhfpedakmangadcdofhnnoh
    # * Web: https://earthview.withgoogle.com/
    #
    # Both have ID-based API. IDs are bundled in the Chrome extension.

    # Chrome API is used to create universal Google Maps URLs since the Web API does not have zoom.
    chrome_api_url = "https://www.gstatic.com/prettyearth/assets/data/v3/{}.json".format(wallpaper_id)

    # Web API is used for attribution and file URLs since Chrome API inlines images as Base64.
    web_api_url = "https://earthview.withgoogle.com/_api/{}.json".format(wallpaper_id)

    print(":::: GET {}".format(chrome_api_url))
    with urllib.request.urlopen(chrome_api_url) as chrome_api_response:
        chrome_api_content = json.loads(chrome_api_response.read())

        print(":::: GET {}".format(web_api_url))
        with urllib.request.urlopen(web_api_url) as web_api_response:
            web_api_content = json.loads(web_api_response.read())

            return {
                "id": wallpaper_id,
                "country": web_api_content.get("country", DEFAULT_COUNTRY),
                "region": web_api_content.get("region", DEFAULT_REGION),
                "file_url": web_api_content["photoUrl"],
                # Reference: https://developers.google.com/maps/documentation/urls/guide#map-action
                "maps_url": "https://www.google.com/maps/@?api=1&map_action=map&basemap=satellite&center={lat},{lng}&zoom={zoom}".format(
                    lat = chrome_api_content["lat"],
                    lng = chrome_api_content["lng"],
                    # Undocumented behavior: Google Maps does not work with float zoom values.
                    zoom = round(chrome_api_content["zoom"])
                ),
                "attribution": web_api_content["attribution"].replace("©", "© ")
            }


if __name__ == "__main__":
    with open(os.path.abspath(os.path.join(os.path.dirname(__file__), FILE_NAME_WALLPAPER_IDS))) as wallpaper_ids_file:
        wallpaper_ids = json.load(wallpaper_ids_file)
        wallpapers_json = json.dumps(list(map(wallpaper, wallpaper_ids)), ensure_ascii=False, indent=4)

        with open(os.path.abspath(os.path.join(os.path.dirname(__file__), FILE_NAME_WALLPAPERS)), "w") as wallpapers_file:
            wallpapers_file.write(wallpapers_json)
