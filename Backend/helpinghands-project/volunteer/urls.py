from django.contrib import admin
from django.urls import path, include
from . import views

urlpatterns = [
    path('create/', views.VolCreate.as_view(), name='create'),
    path('signup/', views.VolSignup.as_view(), name='signup'),
    path('login/', views.VolLogin.as_view(), name='login'),
    path('event/volunteer/', views.volunteer_event, name='create_event'),
    path('event/ongoing/', views.GetAllOngoignEvents.as_view(), name='create_event')
]
