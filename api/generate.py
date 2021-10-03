#!/usr/bin/env python3
# -*- coding: utf8 -*-
import dataclasses
import json
import os
import typing
import urllib.request


FILE_NAME_WALLPAPERS = "wallpapers.json"


@dataclasses.dataclass(frozen=True)
class Wallpaper:
    slug: str


@dataclasses.dataclass(frozen=True)
class WallpaperDetails:
    id: str
    country: str
    region: str
    file_url: str
    maps_url: str
    attribution: str

    def __lt__(self, other) -> bool:
        other_wallpaper_details: WallpaperDetails = other

        return int(self.id) < int(other_wallpaper_details.id)


def fetch_wallpapers() -> typing.Iterable[Wallpaper]:
    with urllib.request.urlopen("https://earthview.withgoogle.com/_api/photos.json") as wallpapers_response:
        wallpapers_content: typing.List[typing.Dict[str, typing.Any]] = json.loads(wallpapers_response.read())

        return map(lambda wallpaper: Wallpaper(slug=str(wallpaper["slug"])), wallpapers_content)


def fetch_wallpaper_details(wallpaper: Wallpaper) -> WallpaperDetails:
    with urllib.request.urlopen(f"https://earthview.withgoogle.com/_api/{wallpaper.slug}.json") as wallpaper_response:
        wallpaper_content: typing.Dict[str, typing.Any] = json.load(wallpaper_response)

        return WallpaperDetails(
            id=wallpaper_content["id"],
            country=wallpaper_content.get("country", ""),
            region=wallpaper_content.get("region", ""),
            file_url=wallpaper_content["photoUrl"],
            maps_url=wallpaper_content["mapsLink"],
            attribution=wallpaper_content["attribution"].replace("©", "© "),
        )


def convert_wallpaper_details(wallpaper: WallpaperDetails) -> typing.Dict[str, str]:
    return {
        "id": wallpaper.id,
        "country": wallpaper.country,
        "region": wallpaper.region,
        "file_url": wallpaper.file_url,
        "maps_url": wallpaper.maps_url,
        "attribution": wallpaper.attribution,
    }


def generate_wallpapers() -> None:
    wallpapers: typing.Iterable[Wallpaper] = fetch_wallpapers()
    wallpapers: typing.Iterable[WallpaperDetails] = map(lambda w: fetch_wallpaper_details(w), wallpapers)
    wallpapers: typing.List[WallpaperDetails] = sorted(wallpapers)
    wallpapers: typing.Iterable[typing.Dict[str, str]] = map(lambda w: convert_wallpaper_details(w), wallpapers)

    with open(os.path.abspath(os.path.join(os.path.dirname(__file__), FILE_NAME_WALLPAPERS)), "w") as wallpapers_file:
        wallpapers_file.write(json.dumps(list(wallpapers), ensure_ascii=False, indent=4))


if __name__ == "__main__":
    generate_wallpapers()
