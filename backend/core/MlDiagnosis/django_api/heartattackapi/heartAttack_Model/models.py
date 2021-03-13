from django.db import models

# Create your models here.
class HeartReadings(models.Model):
    age   = models.FloatField()
    sex  = models.FloatField()  
    cp   = models.FloatField()
    trestbps   = models.FloatField()
    chol   = models.FloatField()
    fbs   = models.FloatField()
    restecg = models.FloatField()
    thalach   = models.FloatField()
    exang   = models.FloatField()
    oldpeak   = models.FloatField()
    slope   = models.FloatField()
    ca   = models.FloatField()
    thal = models.FloatField()
    
    def __str__(self):
        return self.age