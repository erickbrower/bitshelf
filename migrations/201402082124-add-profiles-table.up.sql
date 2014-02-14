CREATE TABLE profiles
(id SERIAL PRIMARY KEY,
 email VARCHAR(250) UNIQUE,
 first_name VARCHAR(30),
 last_name VARCHAR(30),
 birthdate DATE,
 timezone VARCHAR(30),
 users_id INTEGER NOT NULL REFERENCES users(id),
 created_at TIMESTAMP DEFAULT NOW(),
 updated_at TIMESTAMP);
