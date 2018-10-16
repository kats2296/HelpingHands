from django.shortcuts import render

# Create your views here.
from django.contrib.auth.models import User
from django.contrib import auth
from django.http import HttpResponse
from django.contrib.auth.forms import UserCreationForm
import json
from django.db import models
from django.http import JsonResponse
from .models import Organisation, Event


def signup(request):

    if request.method == 'POST':
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
                return HttpResponse("successfull")

            else:
                return HttpResponse("email not verified")
        except models.ObjectDoesNotExist:
            return HttpResponse("user with this email id does not exist")


def create(request):

    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        org = Organisation()
        org.email = data['email']
        org.save()
        return HttpResponse("success")


def login(request):

    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        try:
            org = Organisation.objects.get(email=data['email'])
            if org.password == data['password']:
                return HttpResponse("successful login")

            else:
                return HttpResponse("incorrect password")

        except models.ObjectDoesNotExist:
            return HttpResponse("User with this email doesnt exist.")


def create_event(request):

    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        event = Event()
        event.name = data['name']
        event.contact_number = data['mobile']
        event.email = data['email']
        event.address = data['address']
        event.date = data['date']
        event.time = data['time']
        event.number_of_people = data['num_people']
        event.total_volunteers = data['total_volunteers']
        event.is_pickup_available = data['is_pickup_available']
        event.category = data['category']

        event.content_object = Organisation.objects.get(email=data['email'])

        event.save()
        return HttpResponse("event created")


def get_all_events(request):
    if request.method == 'POST':
        data = json.loads(request.body.decode('utf-8'))

        try:
            org = Organisation.objects.get(email=data['email'])
            event = org.events.all()
            return JsonResponse({'events': list(event.values())})

        except models.ObjectDoesNotExist:
            return HttpResponse("user with this email doesn't exist")




