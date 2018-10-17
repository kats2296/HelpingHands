"""helpinghands URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include
from django.conf.urls import url
from .helper import VerifyEmailActivateUser
from oauth2_provider.contrib.rest_framework import OAuth2Authentication
from rest_framework.documentation import include_docs_urls
from rest_framework.permissions import AllowAny


urlpatterns = [
    url(r'^', include_docs_urls(title='Helping Hands API',
                                authentication_classes=(OAuth2Authentication,),
                                permission_classes=(AllowAny,))),
    path('admin/', admin.site.urls),
    path('organisation/', include('organisation.urls')),
    path('volunteer/', include('volunteer.urls')),
    url(r'^account/activation/(?P<key>[\w.-]+)$', VerifyEmailActivateUser.as_view(),
        name='email_receive')
]
