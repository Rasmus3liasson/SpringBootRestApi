/*Script for my database*/

DROP DATABASE IF EXISTS location;
CREATE DATABASE location CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;;

USE location;

CREATE TABLE IF NOT EXISTS category(
                                       id INTEGER AUTO_INCREMENT PRIMARY KEY ,
                                       name VARCHAR(100) NOT NULL,
    symbol VARCHAR(40) NOT NULL,
    description VARCHAR(255) NOT NULL
    );




CREATE TABLE IF NOT EXISTS place (
                                     place_id INT PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL ,
    user_id INT NOT NULL,
    status ENUM('private', 'public') DEFAULT 'public',
    last_modified DATETIME,
    description VARCHAR(255),
    coordinates POINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category(id)
    );

INSERT INTO category (name, symbol, description) VALUES
                                                     ('Resturant', '🍔', 'Where you can order food'),
                                                     ('Sport', '⚽', 'Sport related'),
                                                     ('Attraction', '🏛️', 'Defined as a attraction that a person might want to witness');

INSERT INTO place (name, category_id, user_id, description, coordinates)
VALUES
    ('Joans', 1, 101, 'Oriental resturant located in the city', POINT(12.9714, 77.5946)),
    ('Nobelstadion', 2, 102, 'Stadium for football', POINT(12.9352, 77.6245)),
    ('Nobel Museumet', 3, 103, 'Museum for Alfred Nobel', POINT(12.9081, 77.6476)),
    ('Ciaw Ciaw', 1, 101, 'Best pizzeria in town', POINT(12.9667, 77.5875));



SELECT * FROM place;