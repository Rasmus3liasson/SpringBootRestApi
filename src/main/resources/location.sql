/*Script for my database*/

DROP DATABASE IF EXISTS location;
CREATE DATABASE location CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;;



USE location;

CREATE TABLE IF NOT EXISTS category(
                                       id INTEGER AUTO_INCREMENT PRIMARY KEY ,
                                       name VARCHAR(100) NOT NULL,
                                       symbol VARCHAR(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                       description VARCHAR(255) NOT NULL,
                                       CHECK (
                                               symbol REGEXP '^[\\x{1F300}-\\x{1F6FF}\\x{1F700}-\\x{1F77F}\\x{1F780}-\\x{1F7FF}\\x{1F800}-\\x{1F8FF}\\x{1F900}-\\x{1F9FF}\\x{2600}-\\x{26FF}]$'

                                           )


);

CREATE TABLE IF NOT EXISTS place (
                                     place_id INT PRIMARY KEY AUTO_INCREMENT,
                                     name VARCHAR(100) NOT NULL,
                                     category_id INT NOT NULL ,
                                     user_id VARCHAR(255) NOT NULL,
                                     status ENUM('private', 'public') DEFAULT 'public',
                                     last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                     description VARCHAR(255),
                                     latitude DOUBLE NOT NULL,
                                     longitude DOUBLE NOT NULL,
                                     coordinates VARCHAR(255),
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     FOREIGN KEY (category_id) REFERENCES category(id)
);

INSERT INTO category (name, symbol, description) VALUES
                                                     ('Resturant', '😀', 'Where you can order food'),
                                                     ('Sport', '⚽', 'Sport related'),


                                                     ('Attraction', '🕍', 'Defined as a attraction that a person might want to witness');


CREATE TRIGGER before_place_insert
    BEFORE INSERT ON place
    FOR EACH ROW
BEGIN
    IF NEW.status IS NULL THEN
        SET NEW.status = 'public';
    END IF;

    SET NEW.coordinates = ST_AsText(POINT(NEW.latitude, NEW.longitude));
END;


CREATE TRIGGER before_place_update
    BEFORE UPDATE ON place
    FOR EACH ROW
BEGIN
    SET NEW.last_modified = CURRENT_TIMESTAMP;
END;

INSERT INTO place (name, category_id, user_id, status, description, latitude,longitude)
VALUES
    ('Joans', 1, 'admin',status, 'Oriental resturant located in the city', 59.326650007053516, 14.523355400136957),
    ('Nobelstadion', 2, 'hej',status, 'Stadium for football', 59.3359257007357, 14.52599816033588),
    ('Nobel Museumet', 3, 'nils','private', 'Museum for Alfred Nobel', 59.34035786915067, 14.534666054112524),
    ('Ciaw Ciaw', 1, 'admin',status, 'Best pizzeria in town', 59.329544957467895, 14.56236226945378),
    ('Big Ben', 3, 'admin', 'public', 'Iconic clock tower in London', 51.5007292, -0.1246254);

SELECT * FROM place