from django.shortcuts import render

# Create your views here.

import json
from django.db import models
from helpinghands.helper import Helper
from .models import Volunteer
from rest_framework import views, permissions, status
from django.http import HttpResponse
from rest_framework.response import Response
from organisation.models import Organisation, Event
from django.forms.models import model_to_dict
from django.http import JsonResponse


class VolSignup(views.APIView):
    def post(self, request, **kwargs):
        # form = UserCreationForm(request.POST)
        data = json.loads(request.body.decode('utf-8'))
        print(data)

        try:
            volunteer = Volunteer.objects.get(email=data['email'])
            if volunteer.is_verified:
                volunteer.name = data['name']
                volunteer.contact_number = data['mobile']
                volunteer.address = data['address']
                volunteer.password = data['password']
                volunteer.confirm_password = data['confirm_password']
                # print(volunteer)
                volunteer.save()
                return Response({"VolSignupResponse": "Successfully signed up"}, status=status.HTTP_201_CREATED)

            else:
                return Response({"VolSignupResponse": "Email not verified"}, status=status.HTTP_401_UNAUTHORIZED)
        except models.ObjectDoesNotExist:
            return Response({"VolSignupResponse": "user with this email id does not exist"},
                            status=status.HTTP_400_BAD_REQUEST)


class VolCreate(views.APIView):
    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))

        volunteer = Volunteer()
        volunteer.email = data['email']
        message = Helper.send_verification_email(data['email'])
        if message[0] == 1:
            volunteer.activation_key = message[2]
            volunteer.save()
            return HttpResponse(message[1])
        else:
            return HttpResponse(message[1])


class VolLogin(views.APIView):
    def post(self, request, **kwargs):
        data = json.loads(request.body.decode('utf-8'))

        try:
            volunteer = Volunteer.objects.get(email=data['email'])
            if volunteer.password == data['password']:
                return Response({"VolLogin": "successful login"}, status.HTTP_200_OK)

            else:
                return Response({"VolLogin": "incorrect password"}, status.HTTP_401_UNAUTHORIZED)

        except models.ObjectDoesNotExist:
            return Response({"VolLogin": "User with this email doesnt exist."}, status.HTTP_400_BAD_REQUEST)


def volunteer_event(request):
    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))
        return HttpResponse("event created")


class GetAllOngoignEvents(views.APIView):
    def get(self, request, **kwargs):
        all_orgs = Organisation.objects.all()
        ongoing_events = []
        for orgs in all_orgs:
            event_list = orgs.events.all()
            for event in event_list:
                if not event.is_completed:
                    ongoing_events.append(model_to_dict(event))

        return JsonResponse({'all_ongoing_events': ongoing_events})








