from django.urls import path
from . import views
from rest_framework import  routers

routers = routers.DefaultRouter()
routers.register('stroke_api', views.StrokeDiagnosisView)
urlpatterns = [
    path('status/', views.strokeOrNot),
]
