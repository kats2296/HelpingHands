import requests
from constants import *


def get_location():
    ip_api_url = "http://ip-api.com/json"
    response_ip_api = requests.get(ip_api_url)
    latitude = response_ip_api.json()['lat']
    longitude = response_ip_api.json()['lon']

    params = {"latlng": str(latitude)+","+str(longitude), "key": GOOGLE_API_KEY}
    google_maps_url = "https://maps.googleapis.com/maps/api/geocode/json"
    response = requests.get(google_maps_url, params=params)
    location = response.json()['results'][0]['address_components'][1]['long_name']

    return location


def get_lat_lng(district):

    google_geocode_url = "https://maps.googleapis.com/maps/api/geocode/json"
    params = {"address": district, "key": GOOGLE_API_KEY}

    response = requests.get(google_geocode_url, params=params)
    print (response.json()['results'][0]['geometry']['location'])