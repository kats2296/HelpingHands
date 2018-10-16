from django.contrib import admin
from django.urls import path, include
from . import views

urlpatterns = [
    path('create/', views.create, name='create'),
    path('signup/', views.signup, name='signup'),
    path('login/', views.login, name='login'),
    path('event/volunteer/', views.volunteer_event, name='create_event')
]
