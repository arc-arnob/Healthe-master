from rest_framework import serializers
from .models import HeartReadings

class diagnosisSerializer(serializers.ModelSerializer):
    class Meta:
        model = HeartReadings
        fields='__all__' #this will showup in json
        