from django.shortcuts import render
from rest_framework import viewsets
from .models import  diagnosis
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
from .prediction import DiabetesModel
from . import dto
# Create your views here.

class DiagnosisView(viewsets.ModelViewSet):
    queryset = diagnosis.objects.all()
    serializer_class = diagnosisSerializer

@api_view(["POST"])
def diabeticOrNot(Request):
        try:
            mydata = Request.data
            dataframe_instance = np.array(list(mydata.values()))
            dataframe_instance = np.reshape(dataframe_instance,(-1,4))
            print(dataframe_instance) 
            model = DiabetesModel('modelv3.sav')
            outcome = model.predict_outcome(dataframe_instance)
            proba = model.predict_proba(dataframe_instance)
            print(outcome[0])
            print(proba[0])
            arr = list()
            arr.append(int(outcome[0]))
            arr.append(float(proba[0])*100)
            return JsonResponse(arr, safe=False)
        except ValueError as e:
            return Response(e.args[0], status.HTTP_400_BAD_REQUEST)
