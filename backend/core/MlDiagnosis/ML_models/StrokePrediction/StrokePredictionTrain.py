# -*- coding: utf-8 -*-
"""
Created on Mon Mar  8 09:28:26 2021

@author: hp
"""
# In[1]
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import joblib, pickle
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import LabelEncoder, OneHotEncoder
from sklearn.preprocessing import  MinMaxScaler
from sklearn.feature_selection import SelectKBest, chi2
from sklearn.model_selection import train_test_split
from sklearn.ensemble import AdaBoostClassifier
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import GridSearchCV
from sklearn.neighbors import KNeighborsClassifier
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis
from sklearn.naive_bayes import GaussianNB
from sklearn.model_selection import KFold
from sklearn.model_selection import cross_val_score
# In[2]
pd.set_option('display.width', 100) 
pd.set_option('precision', 3)
pd.set_option('display.max_columns', None) 
# Findiing Stats of the data
dataframe = pd.read_csv("stroke-data.csv")
print(dataframe.head(20))

# In[3]
shape = dataframe.shape
print(shape)
types = dataframe.dtypes
print(types)

# In[4]
class_cnt = dataframe.groupby('stroke').size()
print(class_cnt) # 0    4861 -- 1     249, highly 

# In[5]
correlation = dataframe.corr(method = "pearson") 
print(correlation) # -1 -> Negative, 0 -> No Corr, 1 -> Positive'
# In[]
skew = dataframe.skew()
print(skew)


# In[]
# Visualization
dataframe.hist()
plt.show()

#2. Density Graph
dataframe.plot(kind="density",subplots= True, layout=(3,3), sharex = False)
plt.show()

# In[]
dataset_missing_vals = dataframe.isnull().sum()

# In[]
# Imputing bmi
imputer = SimpleImputer(missing_values = np.nan, strategy = 'mean')
# In[]

df_copy= dataframe
# In[]
df_copy.iloc[:,9:10] = imputer.fit_transform(df_copy.iloc[:, 9:10])
# In[]
df_copy_missing_vals = df_copy.isnull().sum()
# In[]
dataframe_v2 = df_copy

# In[]
# Reordering dataframe_v2
dataframe_v2 = dataframe_v2.drop(['id'],axis=1)


# In[]
# LabelEncoding
# def featureEncoding()
str_data =  dataframe_v2.select_dtypes(include=['object'])
int_data = dataframe_v2.select_dtypes(include = ['integer','float'])
label = LabelEncoder()
features = str_data.apply(label.fit_transform) 
features = features.join(int_data)
print(features.head())
# In[]
dataframe_v3 = features
X = dataframe_v3.iloc[:,0:10]
Y = dataframe_v3.iloc[:, 10]

# In[]
# Scaling
# def featureScaling()
#scaler = MinMaxScaler(feature_range=(0,1))
#rescaled_X = scaler.fit_transform(X)

# In[]
# Feature Selction
test = SelectKBest(score_func=chi2, k=5)
fit = test.fit(X, Y)
print(fit.scores_) #1 5 6 7 8

# In[]
X = fit.transform(X)

# In[]
x_train,x_test, y_train, y_test = train_test_split(X,Y)
model = GaussianNB()
model.fit(x_train, y_train)
predict = model.predict(x_test)
# In[]
test_score = model.score(x_test, y_test)
print("NBtest_score:", test_score)
train_score = model.score(x_train, y_train)
print("NBtrain_score:",train_score)
# In[]
from sklearn.model_selection import cross_validate
cv_results = cross_validate(model, x_train, y_train, cv=5 )
print(cv_results)

# In[]
conf_mtr = pd.crosstab(y_test, predict)
print(conf_mtr)
# In[]
from sklearn.metrics import classification_report
report = classification_report(y_test, predict)
print(report)
# In[]
with open('model_stroke.sav','wb') as file:
    pickle.dump(model, file)