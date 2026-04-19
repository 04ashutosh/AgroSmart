import json
import threading
from fastapi import FastAPI
from confluent_kafka import Consumer, Producer
import joblib

app = FastAPI()

KAFKA_BROKER = "localhost:9092"
PRODUCER_TOPIC = "recommendation-topic"
CONSUMER_TOPIC = "farm-data-topic"

producer = Producer({'bootstrap.servers': KAFKA_BROKER})

#Load Authentic Trained AI Model
try:
    ai_bundle = joblib.load('agrosmart_ai.pkl')
    print("AI Model loaded successfully into memory.")
except Exception as e:
    print(f"Error loading AI model (did you run train_model.py first?): {e}")

def get_real_ai_prediction(soil_type,crop_type,temp,humidity):
    try:
        # Encode inputs exactly like training
        soil_encoded = ai_bundle['encoder_soil'].transform([soil_type])[0]
        crop_encoded = ai_bundle['encoder_crop'].transform([crop_type])[0]

        # Run Mathematical Inference
        irrigation_pred = ai_bundle['model_irrigation'].predict([[soil_encoded,crop_encoded,temp,humidity]])[0]
        fertilizer_pred = ai_bundle['model_fertilizer'].predict([[soil_encoded,crop_encoded,temp,humidity]])[0]

        return irrigation_pred, fertilizer_pred
    except Exception as e:
        return "Unknown Irrigation", "Unknown Fertilizer - Please check Soil/Crop types"
    
def consume_loop():
    consumer = Consumer({
        'bootstrap.servers': KAFKA_BROKER,
        'group.id': 'python-ai-group',
        'auto.offset.reset': 'earliest'
    })
    consumer.subscribe([CONSUMER_TOPIC])

    while True:
        msg = consumer.poll(1.0)
        if msg is None or msg.error():
            continue

        data = json.loads(msg.value().decode('utf-8'))
        print(f"Applying AI Inference to real farm date: {data}")

        irrigation, fertilizer = get_real_ai_prediction(data['soilType'],
                                                        data['cropType'],
                                                        data['temperature'],
                                                        data['humidity'])
        
        recommendation = {
            "farm_id": data['farmId'],
            "irrigation_advice": irrigation,
            "fertilizer_suggestion": fertilizer
        }

        producer.produce(PRODUCER_TOPIC,value=json.dumps(recommendation).encode('utf-8'))
        producer.flush()
        print(f"Real prediction generated and pushed to Kafka: {recommendation}")

@app.on_event("startup")
def startup_event():
    thread = threading.Thread(target=consume_loop,daemon=True)
    thread.start()

@app.get("/")
def read_root():
    return {"status": "AgroSmart AI Model Online"}