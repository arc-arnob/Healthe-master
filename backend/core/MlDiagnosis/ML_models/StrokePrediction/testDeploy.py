# -*- coding: utf-8 -*-
"""
Created on Mon Mar  8 21:23:34 2021

@author: hp
"""

import importlib
import StrokeDeploy
import numpy as np
dataframe_instance = []
msg = ["marital status","age","hypertension","heart","glucose"]
for i in range(10):
    read = (float(input()))
    dataframe_instance.append(read)
#data_np = np.array(dataframe_instance)
print(dataframe_instance)
#data_np = np.reshape(data_np,(-1,5))    
model = StrokeDeploy.StrokeModel('model_stroke.sav')
print(model.predict(dataframe_instance))

