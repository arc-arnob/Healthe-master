from rest_framework import serializers
from .models import diagnosis

class diagnosisSerializer(serializers.ModelSerializer):
    class Meta:
        model = diagnosis
        fields='__all__' #this will showup in json
        