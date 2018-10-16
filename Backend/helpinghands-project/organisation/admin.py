from django.contrib import admin

# Register your models here.

from .models import Organisation, Event

admin.site.register(Organisation)
admin.site.register(Event)
