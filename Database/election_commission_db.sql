-- Switch to or create the database\CREATE DATABASE IF NOT EXISTS election_commission_db;
USE election_commission_db;

-- Create the `users` table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    district VARCHAR(255),
    role ENUM ('voter','admin')
);

-- Create the `admins` table
CREATE TABLE admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(255),
    state VARCHAR(255),
    district VARCHAR(255),
    permissions TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the `audit_logs` table
CREATE TABLE audit_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    action VARCHAR(255),
    user_id INT,
    admin_id INT,
    ip_address VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (admin_id) REFERENCES admins(admin_id)
);

-- Create the `voters` table
CREATE TABLE voters (
    voter_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(255),
    gender ENUM('male', 'female', 'other'),
    phone_number VARCHAR(15),
    address VARCHAR(255),
    state VARCHAR(255),
    district VARCHAR(255),
    dob DATE,
    voter_number VARCHAR(255),
    status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the `voter_card_applications` table
CREATE TABLE voter_card_applications (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the `delivery_status` table
CREATE TABLE delivery_status (
    delivery_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT,
    admin_id INT,
    status VARCHAR(255),
    FOREIGN KEY (application_id) REFERENCES voter_card_applications(application_id),
    FOREIGN KEY (admin_id) REFERENCES admins(admin_id)
);

-- Create the `voter_profiles` table
CREATE TABLE voter_profiles (
    profile_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    voter_id INT,
    profile_photo_url VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (voter_id) REFERENCES voters(voter_id)
);

-- Create the `documents` table
CREATE TABLE documents (
    document_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    aadhar_card_url VARCHAR(255),
    pan_card_url VARCHAR(255),
    birth_certificate_url VARCHAR(255),
    a4_photo_url VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the `complaints` table
CREATE TABLE complaints (
    complaint_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    complaint_type VARCHAR(255),
    description TEXT,
    status VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Create the `complaint_management` table
CREATE TABLE complaint_management (
    management_id INT PRIMARY KEY AUTO_INCREMENT,
    complaint_id INT,
    assigned_to INT,
    action_taken TEXT,
    FOREIGN KEY (complaint_id) REFERENCES complaints(complaint_id),
    FOREIGN KEY (assigned_to) REFERENCES admins(admin_id)
);

-- Create the `timestamps` table
CREATE TABLE timestamps (
    timestamp_id INT PRIMARY KEY AUTO_INCREMENT,
    table_name VARCHAR(255),
    record_id INT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

-- Create the `faq` table
CREATE TABLE faq (
    faq_id INT PRIMARY KEY AUTO_INCREMENT,
    question TEXT,
    answer TEXT
);
