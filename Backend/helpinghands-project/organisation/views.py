from django.shortcuts import render

# Create your views here.
from django.contrib.auth.models import User
from django.contrib import auth
from django.http import HttpResponse
from django.contrib.auth.forms import UserCreationForm
import json

from .models import Organisation


def signup(request):

    if request.method == 'POST':
        # form = UserCreationForm(request.POST)
        data = json.loads(request.body.decode('utf-8'))
        print(data)

        org = Organisation()
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
