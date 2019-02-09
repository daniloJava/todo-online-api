#!/bin/bash
set -e

##
# Script used for user and database creation when creating the service in the Docker environment,
# being able to extend its use to execute DDLs and DMLs.
##
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    DROP DATABASE IF EXISTS todo;
    CREATE DATABASE todo;
    GRANT ALL PRIVILEGES ON DATABASE todo TO postgres;
EOSQL
