#!/bin/bash
set -e

##
# Script utilizado para criação de usuário e banco de dados ao criar o serviço no ambiente Docker, 
# podendo extender seu uso para execução de DDLs e DMLs.
##
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    GRANT ALL PRIVILEGES ON DATABASE todo TO postgres;
EOSQL
