from django.shortcuts import render

# Create your views here.

from django.contrib.auth.models import User
from django.contrib import auth
from django.http import HttpResponse
from django.contrib.auth.forms import UserCreationForm
import json
from django.db import models

from .models import Volunteer


def signup(request):

    if request.method == 'POST':
        # form = UserCreationForm(request.POST)
        data = json.loads(request.body.decode('utf-8'))
        # print(data)

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
                return HttpResponse("successful")

            else:
                return HttpResponse("email not verified")

        except models.ObjectDoesNotExist:
            return HttpResponse("user with this email id does not exist")


def create(request):

    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        volunteer = Volunteer()
        volunteer.email = data['email']
        volunteer.save()
        return HttpResponse("success")


def login(request):

    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        try:
            volunteer = Volunteer.objects.get(email=data['email'])
            if volunteer.password == data['password']:
                return HttpResponse("successful login")

            else:
                return HttpResponse("incorrect password")

        except models.ObjectDoesNotExist:
            return HttpResponse("User with this email doesnt exist.")


def volunteer_event(request):
    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))
        return HttpResponse("event created")




