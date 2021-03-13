# -*- coding: utf-8 -*-
"""
Created on Sat Mar 13 08:51:47 2021

@author: arc
"""

# In[]
# Libraries
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sns
import pickle

import os
print(os.listdir())

import warnings
warnings.filterwarnings('ignore')

# In[]
pd.set_option('display.width', 100) 
pd.set_option('precision', 3)
pd.set_option('display.max_columns', None) 
dataset = pd.read_csv("heart.csv")
print(dataset.head())
# In[]


from sklearn.model_selection import train_test_split

predictors = dataset.drop("target",axis=1)
target = dataset["target"]

X_train,X_test,Y_train,Y_test = train_test_split(predictors,target,test_size=0.20,random_state=0)

# In[]
from sklearn.metrics import accuracy_score

# In[]
from sklearn.linear_model import LogisticRegression

lr = LogisticRegression()

lr.fit(X_train,Y_train)

Y_pred_lr = lr.predict(X_test)
# In[]

score_lr = round(accuracy_score(Y_pred_lr,Y_test)*100,2)

print("The accuracy score achieved using Logistic Regression is: "+str(score_lr)+" %")

# In[]
from sklearn.metrics import confusion_matrix
cm = confusion_matrix(Y_test, Y_pred_lr)
print(cm)
from sklearn import metrics
print(metrics.classification_report(Y_pred_lr,Y_test)) # 77.0%


# In[]
# Saving
with open('model_heart_LG_V1.sav','wb') as file:
    pickle.dump(lr, file)