import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder
import joblib
import random

# 1. Generate Synthetic Agricultural Data
data = []
soil_types = ['Loamy', 'Clay', 'Sandy']
crop_types = ['Wheat', 'Corn', 'Rice', 'Soybeans']

for _ in range(500):
    soil = random.choice(soil_types)
    crop = random.choice(crop_types)
    temp = round(random.uniform(10.0,40.0),1)
    humidity = round(random.uniform(30.0,90.0),1)

    #Simple logic mapping for realistic outputs
    if temp>30 and humidity<50: irrigation = "High Irrigation (10L/day)"
    elif temp<20 or humidity>70: irrigation = "Low Irrigation (2L/day)"
    else: irrigation = "Moderate Irrigation (5L/day)"

    if soil=='Sandy': fert = "High Nitrogen (NPK 20-10-10)"
    elif soil == 'Clay': fert = "Phosphorus Rich (NPK 10-20-10)"
    else: fert = "Balanced Output (NPK 10-10-10)"

    data.append([soil,crop,temp,humidity,irrigation,fert])

df = pd.DataFrame(data, columns=['Soil','Crop','Temp','Humidity','IrrigationAdvice','FertilizerAdvice'])

# 2. Encode strings to integers to Scikit-Learn can understand them
le_soil = LabelEncoder()
le_crop = LabelEncoder()
df['Soil'] = le_soil.fit_transform(df['Soil'])
df['Crop'] = le_crop.fit_transform(df['Crop'])

X = df[['Soil','Crop','Temp','Humidity']]
y_irrigation = df['IrrigationAdvice']
y_fertilizer = df['FertilizerAdvice']

#3. Train the Model
model_irr = RandomForestClassifier()
model_fert = RandomForestClassifier()

model_irr.fit(X,y_irrigation)
model_fert.fit(X,y_fertilizer)

# 4. Save the Model and Encoders natively
joblib.dump({
    'model_irrigation': model_irr,
    'model_fertilizer': model_fert,
    'encoder_soil': le_soil,
    'encoder_crop': le_crop
}, 'agrosmart_ai.pkl')

print("Authentic AI Model Trained and Saved successfully as agrosmart_ai.pkl!")