from django.core import mail
from helpinghands.settings import EMAIL_HOST_USER
import random
from .private import HOST, PREFIX, EMAIL_VERIFICATION_URI
from rest_framework import views, permissions, status
from rest_framework.response import Response
from organisation.models import Organisation
from volunteer.models import Volunteer
from django.http import HttpResponseRedirect, HttpResponse
from django.db import models
from django.shortcuts import render


class Helper:
    @staticmethod
    def send_verification_email(email):
        key = ''.join(random.choice('0123456789ABCDEF') for _ in range(32))
        subject = "Please Activate Your Helping Hands Account!"
        link = HOST + PREFIX + '/' + key
        from_email = EMAIL_HOST_USER
        to_list = [email]
        message = 'Please use the following link to activate your account.\n\n{}'.format(link)
        try:
            mail.send_mail(subject, message, from_email, to_list, fail_silently=False,)
            return 1, "Email sent!", key
        except KeyError as e:
            return 2, str(e)


class VerifyEmailActivateUser(views.APIView):

    def get(self, request, **kwargs):
        is_org = False
        is_vol = False
        org = None
        vol = None
        try:
            org = Organisation.objects.get(activation_key=kwargs.get('key'))
            is_org = True
        except models.ObjectDoesNotExist:
            try:
                is_vol = True
                vol = Volunteer.objects.get(activation_key=kwargs.get('key'))
            except models.ObjectDoesNotExist:
                return HttpResponse("User does not exist")

        if is_org:
            org.is_verified = True
            org.save()
            return HttpResponse("Activation Completed")

        if is_vol:
            vol.is_verified = True
            vol.save()
            return HttpResponse("Activation Completed")

        else:
            return Response({"Error": "Activation code invalid or expired"}, status=status.HTTP_304_NOT_MODIFIED)

