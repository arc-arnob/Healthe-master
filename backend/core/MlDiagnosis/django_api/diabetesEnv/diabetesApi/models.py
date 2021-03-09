from django.db import models

# Create your models here.
class diagnosis(models.Model):
    glucose = models.FloatField()
    insulin = models.FloatField()
    bmi = models.FloatField()
    age = models.IntegerField()

    def __str__(self):
        return self.glucose
