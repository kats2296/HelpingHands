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
            return Response({"OrgSignupResponse": "user with this email id does not exist"}, status=status.HTTP_400_BAD_REQUEST)


def create(request):

    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        all_orgs = Organisation.objects

        for orgs in all_orgs.all():
            if orgs.email == data['email']:
                return HttpResponse("Email already registered")

        character = data['position']

        print("pos"+character)

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
