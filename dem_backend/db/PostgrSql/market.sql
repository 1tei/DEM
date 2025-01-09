BEGIN;

CREATE TABLE dem.market (
    user_id UUID,
    region TEXT,
    energija INT,
    cena DECIMAL(10, 2),
    PRIMARY KEY (user_id)
);

COMMIT;

GRANT ALL PRIVILEGES ON TABLE dem.market TO dem;
