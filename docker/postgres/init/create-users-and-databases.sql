-- Create the admin users
CREATE ROLE user_mgmt_admin LOGIN PASSWORD 'user_mgmt_admin' SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;
CREATE ROLE auth_admin LOGIN PASSWORD 'auth_admin' SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;
CREATE ROLE ib_int_admin LOGIN PASSWORD 'ib_int_admin' SUPERUSER INHERIT CREATEDB CREATEROLE REPLICATION;

-- Create the databases
CREATE DATABASE user_mgmt WITH OWNER = user_mgmt_admin ENCODING = 'UTF-8' TABLESPACE = pg_default CONNECTION LIMIT = -1;
CREATE DATABASE auth WITH OWNER = auth_admin ENCODING = 'UTF-8' TABLESPACE = pg_default CONNECTION LIMIT = -1;
CREATE DATABASE ib_int WITH OWNER = ib_int_admin ENCODING = 'UTF-8' TABLESPACE = pg_default CONNECTION LIMIT = -1;

-- Create the standard users
CREATE USER user_mgmt_user WITH PASSWORD 'user_mgmt_user' NOSUPERUSER;
CREATE USER auth_user WITH PASSWORD 'auth_user' NOSUPERUSER;
CREATE USER ib_int_user WITH PASSWORD 'ib_int_user' NOSUPERUSER;

-- Grant public schema access
\connect user_mgmt
GRANT ALL ON SCHEMA public TO user_mgmt_admin;
GRANT ALL ON SCHEMA public TO user_mgmt_user;

\connect auth
GRANT ALL ON SCHEMA public TO auth_admin;
GRANT ALL ON SCHEMA public TO auth_user;

\connect ib_int
GRANT ALL ON SCHEMA public TO ib_int_admin;
GRANT ALL ON SCHEMA public TO ib_int_user;
