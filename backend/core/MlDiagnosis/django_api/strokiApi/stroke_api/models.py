from django.db import models

# Create your models here.
class stroke(models.Model):
    gender  = models.FloatField()
    ever_married  = models.FloatField()
    work_type  = models.FloatField()
    Residence_type  = models.FloatField()
    smoking_status  = models.FloatField() 
    age  = models.FloatField()
    hypertension= models.FloatField()
    heart_disease  = models.FloatField()
    avg_glucose_level = models.FloatField()    
    bmi = models.FloatField()
    
    def __str__(self):
        return self.gender