from django.contrib import admin
from django.urls import path, include
from . import views

urlpatterns = [
    path('create/', views.create, name='create'),
    path('signup/', views.signup, name='signup'),
    path('login/', views.login, name='login'),
    path('event/create/', views.create_event, name='create_event'),
    path('events/all/', views.get_all_events)
]
