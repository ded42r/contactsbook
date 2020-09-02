CREATE TABLE phones
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    number        VARCHAR(12) UNIQUE NOT NULL,
    phone_type_id INT                NOT NULL,
    contact_id    INT                NOT NULL,
    FOREIGN KEY (phone_type_id) REFERENCES phone_types (id)
)