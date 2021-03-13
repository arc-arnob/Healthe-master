from django.shortcuts import render
from rest_framework import viewsets
from .models import HeartReadings
from rest_framework.decorators import api_view
from django.core import serializers
from rest_framework.response import Response
from rest_framework.request import  Request
from rest_framework import status
from django.http import JsonResponse
from rest_framework.parsers import JSONParser
from . serializer import diagnosisSerializer
import pickle, joblib
import json
import numpy as np
from sklearn import preprocessing
import pandas as pd
from .prediction import heartAttackModel

# Create your views here.
class AttackDiagnosisView(viewsets.ModelViewSet):
    queryset = HeartReadings.objects.all()
    serializer_class = diagnosisSerializer

@api_view(["POST"])
def attackOrNot(Request):
        try:
            mydata = Request.data
            dataframe_instance = list(mydata.values())
            print("dataframe in view is: ", dataframe_instance) 
            model = heartAttackModel('model_heart_LG_V1.sav')
            print("model loaded bitch")
            res = model.predict(dataframe_instance)
            arr = list()
            arr.append(int(res[0]))
            arr.append(float(res[1])*100)
            return JsonResponse(arr, safe=False)
        except ValueError as e:
            print(e)
            return Response(e.args[0], status.HTTP_400_BAD_REQUEST)