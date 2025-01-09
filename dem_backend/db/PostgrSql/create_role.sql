-- Role: dem
-- DROP ROLE dem;
--right click on database open query tool
CREATE USER dem WITH PASSWORD '123';
CREATE DATABASE dem
    WITH
    OWNER = dem
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
--open dem database
CREATE SCHEMA dem AUTHORIZATION dem;


COMMENT ON DATABASE dem
    IS 'Database for Delivery Energy Marketplace';

GRANT ALL PRIVILEGES ON DATABASE dem TO dem;
GRANT ALL PRIVILEGES ON TABLE dem.wallets TO dem;
GRANT ALL PRIVILEGES ON TABLE dem.users TO dem;
GRANT ALL PRIVILEGES ON TABLE dem.transaction_ledger TO dem;
GRANT ALL PRIVILEGES ON TABLE dem.iot_devices TO dem;
GRANT ALL PRIVILEGES ON TABLE dem.energy TO dem;



ALTER USER dem CREATEDB;