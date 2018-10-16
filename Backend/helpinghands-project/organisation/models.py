from django.db import models

# Create your models here.


class Organisation(models.Model):
    name = models.CharField(max_length=255)
    contact_number = models.CharField(max_length=10)
    head_name = models.CharField(max_length=255)
    address = models.CharField(max_length=255)
    password = models.CharField(max_length=100)
    confirm_password = models.CharField(max_length=100)