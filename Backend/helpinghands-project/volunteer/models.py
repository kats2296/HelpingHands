from django.db import models

# Create your models here.
from django.contrib.contenttypes.fields import GenericRelation
from organisation.models import Event


class Volunteer(models.Model):
    name = models.CharField(max_length=255, default="", null=True, blank=True)
    contact_number = models.CharField(max_length=10,  default="", null=True, blank=True)
    email = models.EmailField(max_length=255, primary_key=True)
    address = models.TextField(max_length=255, default="", null=True, blank=True)
    password = models.TextField(max_length=100, default="", null=True, blank=True)
    confirm_password = models.TextField(max_length=100, default="", null=True, blank=True)
    is_verified = models.BooleanField(default=False)
    events = GenericRelation(Event)


