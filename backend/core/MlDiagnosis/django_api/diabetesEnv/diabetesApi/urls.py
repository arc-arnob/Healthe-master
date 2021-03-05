from django.urls import path
from . import views
from rest_framework import  routers

routers = routers.DefaultRouter()
routers.register('diabetesApi', views.DiagnosisView)
urlpatterns = [
    path('status/', views.diabeticOrNot),
]
