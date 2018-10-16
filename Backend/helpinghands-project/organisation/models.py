from django.db import models

# Create your models here.
from django.contrib.contenttypes.fields import GenericForeignKey
from django.contrib.contenttypes.models import ContentType
from django.contrib.contenttypes.fields import GenericRelation


class Event(models.Model):
    name = models.CharField(max_length=255)
    contact_number = models.TextField(max_length=255)
    email = models.CharField(max_length=255)
    address = models.TextField(max_length=255)
    date = models.TextField(max_length=255)
    time = models.TextField(max_length=255)
    total_volunteers = models.IntegerField()
    volunteers_volunteered = models.IntegerField(default=0)
    volunteers_left = models.IntegerField(default=0)
    number_of_people = models.IntegerField()
    is_pickup_available = models.BooleanField(default=False)
    category = models.CharField(max_length=255)
    is_completed = models.BooleanField(default=False)
    # organisation = models.ForeignKey(Organisation, on_delete=models.CASCADE)
    # volunteer = models.ForeignKey(Volunteer, on_delete=models.PROTECT, blank=True)
    content_type = models.ForeignKey(ContentType, on_delete=models.CASCADE, default=None)
    object_id = models.PositiveIntegerField(default=None)
    content_object = GenericForeignKey('content_type', 'object_id')


class Organisation(models.Model):
    name = models.CharField(max_length=255, default="", null=True, blank=True)
    contact_number = models.TextField(max_length=10, default="", null=True,blank=True)
    email = models.CharField(max_length=255, default="")
    head_name = models.CharField(max_length=255, default="", null=True, blank=True)
    address = models.TextField(max_length=255, default="", null=True, blank=True)
    password = models.TextField(max_length=100, default="", null=True, blank=True)
    confirm_password = models.TextField(max_length=100, default="", null=True, blank=True)
    is_verified = models.BooleanField(default=False)
    events = GenericRelation(Event)


