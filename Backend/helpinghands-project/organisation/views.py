from django.shortcuts import render

# Create your views here.
from django.http import HttpResponse
import json
from django.db import models
from django.http import JsonResponse
from .models import Organisation, Event
from volunteer.models import Volunteer
from django.forms.models import model_to_dict
from helpinghands.helper import Helper
from rest_framework.response import Response
from rest_framework import views, permissions, status
import requests
from helpinghands.private import GOOGLE_API_KEY
import pandas as pd
import numpy as np
from sklearn.externals import joblib
import os
from helpinghands.settings import PROJECT_ROOT


class OrgSignup(views.APIView):

    def post(self, request, **kwargs):
        # form = UserCreationForm(request.POST)
        data = json.loads(request.body.decode('utf-8'))
        print(data)

        try:
            org = Organisation.objects.get(email=data['email'])
            if org.is_verified:
                org.name = data['name']
                org.contact_number = data['mobile']
                org.head_name = data['head_name']
                org.address = data['address']
                org.password = data['password']
                org.confirm_password = data['confirm_password']
                # org = Organisation(name, contact_number, head_name, address, password, confirm_password)
                print(org)

                org.save()
                return Response({"OrgSignupResponse": "Successfully signed up"}, status=status.HTTP_201_CREATED)

            else:
                return Response({"OrgSignupResponse": "Email not verified"}, status=status.HTTP_401_UNAUTHORIZED)
        except models.ObjectDoesNotExist:
            return Response({"OrgSignupResponse": "user with this email id does not exist"},
                            status=status.HTTP_400_BAD_REQUEST)


def create(request):
    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        all_orgs = Organisation.objects

        for orgs in all_orgs.all():
            if orgs.email == data['email']:
                return HttpResponse("Email already registered")

        character = data['position']

        print("pos" + character)

        if character == "organization":
            org = Organisation()
            org.email = data['email']

            message = Helper.send_verification_email(data['email'])
            if message[0] == 1:
                org.activation_key = message[2]
                org.save()
                return HttpResponse(message[1])
            else:
                return HttpResponse(message[1])
        elif character == "volunteer":

            vol = Volunteer()
            vol.email = data['email']

            message = Helper.send_verification_email(data['email'])
            if message[0] == 1:
                vol.activation_key = message[2]
                vol.save()
                return HttpResponse(message[1])
            else:
                return HttpResponse(message[1])


class OrgLogin(views.APIView):
    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))

        try:
            org = Organisation.objects.get(email=data['email'])
            if org.password == data['password']:
                return Response({"OrgLogin": "successful login"}, status.HTTP_200_OK)

            else:
                return Response({"OrgLogin": "incorrect password"}, status.HTTP_401_UNAUTHORIZED)

        except models.ObjectDoesNotExist:
            return Response({"VolLogin": "User with this email doesnt exist."}, status.HTTP_400_BAD_REQUEST)


class OrgCreateEvent(views.APIView):
    def post(self, request, **kwargs):

        data = json.loads(request.body.decode('utf-8'))
        email = data['email']
        event = Event()
        event.name = data['name']
        event.contact_number = data['mobile']
        event.email = email
        event.address = data['address']
        event.date = data['date']
        event.time = data['time']
        event.number_of_people = data['num_people']
        event.total_volunteers = data['total_volunteers']
        event.is_pickup_available = data['is_pickup_available']
        event.category = data['category']
        event.content_object = Organisation.objects.get(email=email)

        try:
            event.save()
            return Response({"OrgEventCreate": "event created"}, status.HTTP_201_CREATED)
        except:
            return Response({"OrgEventCreate": "event creation failed"}, status.HTTP_406_NOT_ACCEPTABLE)


class OrgGetAllEvents(views.APIView):

    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))

        try:
            org = Organisation.objects.get(email=data['email'])
            event = org.events.all()
            return JsonResponse({'events': list(event.values())})

        except models.ObjectDoesNotExist:
            return Response({"OrgLogin": "User with this email doesnt exist."}, status.HTTP_409_CONFLICT)


class OrgOngoignEvents(views.APIView):
    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))
        try:
            org = Organisation.objects.get(email=data['email'])
            event_list = org.events.all()
            ongoing_events = []
            for event in event_list:
                if not event.is_completed:
                    ongoing_events.append(model_to_dict(event))
            return JsonResponse({'ongoing_events': ongoing_events})

        except models.ObjectDoesNotExist:
            return Response({"OrgLogin": "User with this email doesnt exist."}, status.HTTP_409_CONFLICT)


class OrgPreviousEvents(views.APIView):
    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))
        try:
            org = Organisation.objects.get(email=data['email'])
            event_list = org.events.all()
            ongoing_events = []
            for event in event_list:
                if event.is_completed:
                    ongoing_events.append(model_to_dict(event))
            return JsonResponse({'previous_events': ongoing_events})

        except models.ObjectDoesNotExist:
            return Response({"OrgLogin": "User with this email doesnt exist."}, status.HTTP_409_CONFLICT)


class DistrictSuggestion(views.APIView):
    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))

        event = data['event']

        current_location = self.get_current_location()
        k = 3

        if event == "poverty":
            df_poverty = pd.read_csv(os.path.join(PROJECT_ROOT, "poverty_dataset.csv"))
            present_state_data = df_poverty.loc[df_poverty['STATNAME'] == current_location.upper()]
            min_list = present_state_data.nsmallest(k, 'P_URB_POP')
            districts = min_list['DISTNAME'].values
            return Response({"districts": districts})

        elif event == "education":
            df_literates = pd.read_csv(os.path.join(PROJECT_ROOT, "literacy_dataset.csv"))
            state_data = df_literates.loc[df_literates['INDIA/STATE/UT/DISTRICT'] == current_location.upper()]
            state_code = state_data['STATE CODE'].values
            present_state_literates_data = df_literates.loc[df_literates['STATE CODE'] == state_code[0]]
            present_state_rural_data = present_state_literates_data.loc[present_state_literates_data['TRU'] == "Rural"]
            min_list = present_state_rural_data.nsmallest(k, 'PL_Persons')
            districts = min_list['INDIA/STATE/UT/DISTRICT'].values
            return Response({"districts": districts})

        elif event == "health_care":
            df_healthcare_centres = pd.read_csv(os.path.join(PROJECT_ROOT, "healthcare_centres_dataset.csv"))
            present_state_data = df_healthcare_centres.loc[df_healthcare_centres['States/Union Territory']
                                                           == current_location]
            min_list = present_state_data.nsmallest(k, 'Total Number of HealthCare Units')
            districts = min_list['Name of the District'].values
            return Response({"districts": districts})

        elif event == "donation":
            df_poverty = pd.read_csv(os.path.join(PROJECT_ROOT, "poverty_dataset.csv"))
            present_state_data = df_poverty.loc[df_poverty['STATNAME'] == current_location.upper()]
            min_list = present_state_data.nsmallest(k, 'P_URB_POP')
            districts = min_list['DISTNAME'].values
            return Response({"districts": districts})

    @staticmethod
    def get_current_location():
        ip_api_url = "http://ip-api.com/json"
        response_ip_api = requests.get(ip_api_url)
        latitude = response_ip_api.json()['lat']
        longitude = response_ip_api.json()['lon']

        params = {"latlng": str(latitude) + "," + str(longitude), "key": GOOGLE_API_KEY}
        google_maps_url = "https://maps.googleapis.com/maps/api/geocode/json"
        response = requests.get(google_maps_url, params=params)
        location = response.json()['results'][0]['address_components'][1]['long_name']

        return location


class EventsSuggestion(views.APIView):
    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))

        month = data['month']
        district = data['district']

        event_dict = {1: 'poverty', 2: 'health_care', 3: 'education', 4: 'donations'}

        print("loading model")
        model = joblib.load(os.path.join(PROJECT_ROOT, "svm_model.pkl"))
        print("model loaded")

        df = pd.read_csv(os.path.join(PROJECT_ROOT, 'poverty_dataset.csv'))
        row = list(df.loc[df['DISTNAME'] == district].iloc[:, :9].values)
        district_code = row[0][2]

        df = pd.read_csv(os.path.join(PROJECT_ROOT, 'important_districts.csv'))
        important_districts = list(df.iloc[:, 2].values)

        index_dc = 0
        if district_code not in important_districts:
            district_code, index_dc = self.get_nearest_district(district_code, important_districts)
        X = self.generate_encoded_input(month, index_dc)
        X.reshape(1, -1)
        event_code = model.predict(X)

        return Response({"event": event_dict[event_code[0]]})

    def generate_encoded_input(self, month, index_dc):
        X = np.zeros((1, 112))
        X[0][month - 1] = 1
        X[0][index_dc] = 1
        return X

    def get_nearest_district(self, district_code, important_districts):
        min_dist = 4000
        nearest_dc = district_code
        index_dc = 0
        for dc in range(len(important_districts)):
            dist = abs(important_districts[dc] - district_code)
            if dist < min_dist:
                min_dist = dist
                nearest_dc = important_districts[dc]
                index_dc = dc

        return nearest_dc, index_dc

