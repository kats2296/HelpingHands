import requests


def get_location():
    ip_api_url = "http://ip-api.com/json"
    response_ip_api = requests.get(ip_api_url)
    latitude = response_ip_api.json()['lat']
    longitude = response_ip_api.json()['lon']

    GOOGLE_API_KEY = "AIzaSyDoQyE4OsQTAO8QDzgGYvnR79YJZ51HIK8"
    params = {"latlng": str(latitude)+","+str(longitude), "key": GOOGLE_API_KEY}
    google_maps_url = "https://maps.googleapis.com/maps/api/geocode/json"
    response = requests.get(google_maps_url, params=params)
    location = response.json()['results'][0]['address_components'][1]['long_name']

    return location


# ipStack = IpStack()
# print(ipStack.get_location())


