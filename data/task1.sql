-- Write your Task 1 answers in this file

DROP DATABASE IF EXISTS bedandbreakfast;

CREATE DATABASE bedandbreakfast;

USE bedandbreakfast;

CREATE TABLE users (

    email VARCHAR(128),
    name VARCHAR(128) NOT NULL,

    CONSTRAINT pk_email PRIMARY KEY(email)
);

CREATE TABLE bookings  (

	booking_id CHAR(8),
    listing_id VARCHAR(20) NOT NULL,
    duration INT NOT NULL,
    email VARCHAR(128) NOT NULL,
    
    CONSTRAINT pk_booking_id PRIMARY KEY(booking_id),
    FOREIGN KEY (email) REFERENCES users(email)
);

CREATE TABLE reviews (

    id INT AUTO_INCREMENT,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    listing_id VARCHAR(20) NOT NULL,
    reviewer_name VARCHAR(64) NOT NULL,
    comments TEXT,

    CONSTRAINT id PRIMARY KEY(id)
);

INSERT INTO users(email, name)
	VALUES("fred@gmail.com", "Fred Flintstone"),
		("barney@gmail.com", "Barney Rubble"),
		("fry@planetexpress.com", "Philip J Fry"),
		("hlmer@gmail.com", "Homer Simpson");


GRANT ALL PRIVILEGES ON bedandbreakfast.* TO fred@'%';
FLUSH PRIVILEGES;