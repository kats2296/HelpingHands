from django.contrib import admin
from django.urls import path, include
from . import views

urlpatterns = [
    path('create/', views.create, name='create'),
    path('signup/', views.OrgSignup.as_view(), name='signup'),
    path('login/', views.OrgLogin.as_view(), name='login'),
    path('event/create/', views.OrgCreateEvent.as_view(), name='create_event'),
    path('events/all/', views.OrgGetAllEvents.as_view(), name='get_all_events'),
    path('events/ongoing/', views.OrgOngoignEvents.as_view(), name='get_org_ongoing_event'),
    path('events/previous/', views.OrgPreviousEvents.as_view()),
    path('districts/suggested/', views.DistrictSuggestion.as_view()),
    path('events/suggested/', views.EventsSuggestion.as_view()),
    path('districts/latlng/', views.GetLatLng.as_view()),
]
