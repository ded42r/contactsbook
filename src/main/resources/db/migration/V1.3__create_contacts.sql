CREATE TABLE contacts
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NULL,
    address    VARCHAR(512) NULL

);
CREATE INDEX f_name_idx ON contacts (first_name);
CREATE INDEX l_name_idx ON contacts (last_name);
