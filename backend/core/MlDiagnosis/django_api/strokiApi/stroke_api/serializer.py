from rest_framework import serializers
from .models import stroke

class diagnosisSerializer(serializers.ModelSerializer):
    class Meta:
        model = stroke
        fields='__all__' #this will showup in json
        