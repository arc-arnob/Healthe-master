# -*- coding: utf-8 -*-
"""
Created on Sat Mar 13 10:26:55 2021

@author: hp
"""

import importlib
import prediction
import numpy as np
dataframe_instance = []
msg = ["marital status","age","hypertension","heart","glucose"]
for i in range(13):
    read = (float(input()))
    dataframe_instance.append(read)
#data_np = np.array(dataframe_instance)
print(dataframe_instance)
#data_np = np.reshape(data_np,(-1,5))    
model = prediction.heartAttackModel('model_heart_LG_V1.sav')
print(model.predict(dataframe_instance))

