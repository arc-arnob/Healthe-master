from django.urls import path
from . import views
from rest_framework import  routers

routers = routers.DefaultRouter()
routers.register('heartAttack_Model', views.AttackDiagnosisView)
urlpatterns = [
    path('status/', views.attackOrNot),
]