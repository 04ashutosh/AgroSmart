CREATE DATABASE IF NOT EXISTS agrosmart_db;
USE agrosmart_db;

CREATE TABLE users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE farms(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    latitude DOUBLE,
    longitude DOUBLE,
    soil_type VARCHAR(100),
    crop_type VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE recommendations(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    farm_id BIGINT,
    irrigation_advice TEXT,
    fertilizer_suggestion TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (farm_id) REFERENCES farms(id)
);