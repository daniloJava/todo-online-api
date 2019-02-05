#!/bin/bash
set -e

##
# Sobrescreve o arquivo de configuração do PostgreSQL gerado em runtime no diretório 
# definido pela variável $PGDATA ("/var/lib/postgresql/data/" valor padrão), permitindo 
# customizar os parâmetros de inicialização do PostgreSQL.
##
cat $POSTGRES_CONFIG/postgresql.conf > $PGDATA/postgresql.conf

