-- SQL script to set up the database and table for JAVA_jdbc.basics

-- 1. Create database if it does not exist
CREATE DATABASE IF NOT EXISTS jdbcdemo;

-- 2. Switch to the created database
USE jdbcdemo;

-- 3. Create the students table
CREATE TABLE IF NOT EXISTS students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    gpa DOUBLE NOT NULL
);

-- 4. (Optional) Insert sample data
INSERT INTO students (name, email, gpa) VALUES 
('Alice Smith', 'alice@example.com', 3.85),
('Bob Jones', 'bob@example.com', 3.42)
ON DUPLICATE KEY UPDATE name=VALUES(name), gpa=VALUES(gpa);
