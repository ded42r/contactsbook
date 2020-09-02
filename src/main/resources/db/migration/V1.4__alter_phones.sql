ALTER TABLE phones
    ADD FOREIGN KEY (contact_id)
        REFERENCES contacts (id)