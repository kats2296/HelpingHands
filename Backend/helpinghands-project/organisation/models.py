from django.db import models

# Create your models here.


class Organisation(models.Model):
    name = models.CharField(max_length=255, default="", null=True, blank=True)
    contact_number = models.TextField(max_length=10, default="", null=True,blank=True)
    email = models.CharField(max_length=255, default="")
    head_name = models.CharField(max_length=255, default="", null=True, blank=True)
    address = models.TextField(max_length=255, default="", null=True, blank=True)
    password = models.TextField(max_length=100, default="", null=True, blank=True)
    confirm_password = models.TextField(max_length=100, default="", null=True, blank=True)
    is_verified = models.BooleanField(default=False)
