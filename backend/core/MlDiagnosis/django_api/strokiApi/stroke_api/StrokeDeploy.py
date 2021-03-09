# -*- coding: utf-8 -*-
"""
Created on Mon Mar  8 20:36:09 2021

@author: hp
"""

# -*- coding: utf-8 -*-
"""
Created on Tue Apr 14 18:31:57 2020

@author: Arnob
"""
# Creating a Module

import numpy as np

import pandas as pd
import joblib, pickle
from sklearn.linear_model import LogisticRegression
from sklearn import metrics
from os.path import dirname, join, abspath
from sklearn.preprocessing import  MinMaxScaler, minmax_scale
from sklearn.preprocessing import LabelEncoder, OneHotEncoder


class StrokeModel():
    def __init__(self,model_file='model_stroke.sav'):
        model_file = join(dirname(abspath(__file__)), model_file)
        with open(model_file,'rb') as model_file:
            self.classifier  = pickle.load(model_file)
            self.data = None
    # reading is an array to be passed from GUI
    def predict_outcome(self,reading):
        self.data = reading
        if self.data is not None:
            pred = self.classifier.predict(self.data)
            return pred
        else:
            return None
    def predict_proba(self,reading):
        self.data = reading
        if self.data is not None:
            prob = self.classifier.predict_proba(self.data)[:,1]
            return prob
        else:
            return None
        
    def featureScalingAndReducing(self, reading):
        self.data = reading
        if self.data is not None:
            #dataframe = featureEncoding(reading)
            #X = pd.DataFrame(reading, columns=['gender','never_married','work_type','Residence_type','smoking_status','age', 'hypertension', 'heart_disease','avg_glucose_level','bmi'])
            #print("dataframe recieved", X)
            #scaler = MinMaxScaler(feature_range=(0,1))
            #dataframe = scaler.fit_transform(reading.reshape(-1,1))
            
            #dataframe = minmax_scale(reading.T).T
            
            #print("df after min max scaling",dataframe)
            # convert df to np array for ease
            #dataframe = dataframe.reshape(1,10)
            dataPd = pd.DataFrame(reading, columns=['gender','never_married','work_type','Residence_type','smoking_status','age', 'hypertension', 'heart_disease','avg_glucose_level','bmi'])
            columns=['gender','work_type','Residence_type','smoking_status','bmi'] 
            dataPd.drop(columns, axis=1, inplace=True)
            print("df after col deletion",dataPd)
            return dataPd.to_numpy()
        else:
            return None;
            
    def featureEncoding(self, reading): # dataframe has to pandas dataframe
        dataframe = pd.DataFrame(reading, columns=['gender','ever_married','work_type','Residence_type','smoking_status','age', 'hypertension heart_disease','avg_glucose_level','bmi'])
        str_data =  dataframe.select_dtypes(include=['object'])
        int_data = dataframe.select_dtypes(include = ['integer','float'])
        label = LabelEncoder()
        features = str_data.apply(label.fit_transform) 
        features = features.join(int_data)
        return features
    
    def predict(self, dataframe):
        #dataframe_instance = list(map(float, [glucose, insulin, bmi, age]))
        data_np = np.array(dataframe)
        data_np = np.reshape(data_np,(-1,10))
        print(data_np)
        #dataframe = self.featureScalingAndReducing(data_np)
        print(dataframe)
        outcome = self.predict_outcome(data_np)
        probability = self.predict_proba(data_np)
        arr = list()
        arr.append(outcome[0])
        arr.append(probability[0])
        return arr