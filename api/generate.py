#!/usr/bin/env python3
# -*- coding: utf8 -*-

import json
import os
import requests


# IDs are bundled in the Chrome extension.
FILE_NAME_IDS = "ids.json"
FILE_NAME_WALLPAPERS = "wallpapers.json"


def content(id):
    print(":: Generating [{}]".format(id))

    # Web API does not contain zoom. It has Google Maps URL though but it is not crossplatform.
    # Reference: https://developers.google.com/maps/documentation/urls/guide#map-action
    chrome_api_url = "https://www.gstatic.com/prettyearth/assets/data/v2/{}.json".format(id)
    print(":::: Fetching {}".format(chrome_api_url))
    chrome_api_content = requests.get(chrome_api_url).json()

    web_api_url = "https://earthview.withgoogle.com/_api/{}.json".format(id)
    print(":::: Fetching {}".format(web_api_url))
    web_api_content = requests.get(web_api_url).json()

    return {
        "id": id,
        "country": web_api_content["country"],
        "region": web_api_content["region"],
        "file_url": web_api_content["photoUrl"],
        "maps_url": "https://www.google.com/maps/@?api=1&map_action=map&basemap=satellite&center={lat},{lng}&zoom={zoom}".format(
            lat = chrome_api_content["lat"],
            lng = chrome_api_content["lng"],
            zoom = chrome_api_content["zoom"]
        ),
        "attribution": web_api_content["attribution"].replace("©", "© ")
    }


if __name__ == "__main__":
    with open(os.path.abspath(os.path.join(os.path.dirname(__file__), FILE_NAME_IDS))) as ids_file:
        wallpapers_json = json.dumps(list(map(content, json.load(ids_file))), ensure_ascii=False, indent=4)

        with open(os.path.abspath(os.path.join(os.path.dirname(__file__), FILE_NAME_WALLPAPERS)), "w") as wallpapers_file:
            wallpapers_file.write(wallpapers_json)
