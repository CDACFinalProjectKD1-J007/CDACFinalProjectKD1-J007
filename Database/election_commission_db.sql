-- Switch to or create the database
CREATE DATABASE IF NOT EXISTS election_commission_db;
USE election_commission_db;

-- Create the `users` table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    district VARCHAR(255),
    role ENUM ('admin', 'voter'),
    gender ENUM('male', 'female', 'other'),
    phone_number VARCHAR(15),
    address VARCHAR(255),
    state VARCHAR(255),
    dob DATE,
    voter_number VARCHAR(255) UNIQUE,
    status VARCHAR(255) COMMENT 'e.g., Active, Inactive',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the `admins` table
CREATE TABLE admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    name VARCHAR(255),
    state VARCHAR(255),
    district VARCHAR(255),
    permissions TEXT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create the `audit_logs` table
CREATE TABLE audit_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    action VARCHAR(255),
    user_id INT,
    ip_address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL
);

-- Create the `voters` table
CREATE TABLE voters (
    voter_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    profile_photo_url VARCHAR(255) COMMENT 'URL of the profile photo',
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create the `voter_card_applications` table
CREATE TABLE voter_card_applications (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    status ENUM('Pending', 'Approved', 'Rejected'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create the `delivery_status` table
CREATE TABLE delivery_status (
    delivery_id INT PRIMARY KEY AUTO_INCREMENT,
    application_id INT,
    status ENUM('In Progress', 'Delivered'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (application_id) REFERENCES voter_card_applications(application_id) ON DELETE CASCADE
);

-- Create the `documents` table
CREATE TABLE documents (
    document_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    aadhar_card_url VARCHAR(255),
    pan_card_url VARCHAR(255),
    birth_certificate_url VARCHAR(255),
    a4_photo_url VARCHAR(255),
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create the `complaints` table
CREATE TABLE complaints (
    complaint_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    complaint_type VARCHAR(255),
    description TEXT,
    status ENUM('Forwarded', 'Completed'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Create the `complaint_management` table
CREATE TABLE complaint_management (
    management_id INT PRIMARY KEY AUTO_INCREMENT,
    complaint_id INT,
    assigned_to INT,
    action_taken TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (complaint_id) REFERENCES complaints(complaint_id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES admins(admin_id) ON DELETE SET NULL
);

-- Create the `faq` table
CREATE TABLE faq (
    faq_id INT PRIMARY KEY AUTO_INCREMENT,
    question TEXT,
    answer TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
