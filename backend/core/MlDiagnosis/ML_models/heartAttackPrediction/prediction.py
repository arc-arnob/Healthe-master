# -*- coding: utf-8 -*-
"""
Created on Sat Mar 13 10:15:25 2021

@author: arc
"""
import numpy as np
import pandas as pd
import joblib, pickle
from sklearn.linear_model import LogisticRegression
from os.path import dirname, join, abspath

class heartAttackModel():
    def __init__(self,model_file='model_hear_LG.sav'):
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
    def predict(self, dataframe):
        data_np = np.array(dataframe)
        data_np = np.reshape(data_np,(-1,13))
        print(dataframe)
        outcome = self.predict_outcome(data_np)
        probability = self.predict_proba(data_np)
        arr = list()
        arr.append(outcome[0])
        arr.append(probability[0])
        return arr