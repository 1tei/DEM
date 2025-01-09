BEGIN;

CREATE TABLE dem.energy (
    user_id UUID,
    datetime TIMESTAMP,
    amount INT,
    PRIMARY KEY (user_id, datetime),  -- Composite Primary Key
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES dem.users(user_id) ON UPDATE CASCADE ON DELETE RESTRICT
);

COMMIT;