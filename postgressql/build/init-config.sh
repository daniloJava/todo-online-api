#!/bin/bash
set -e

##
# Overwrite runtime-generated PostgreSQL configuration file in directory
# defined by the variable $ PGDATA ("/ var / lib / postgresql / data /" default value), allowing
# customize the PostgreSQL startup parameters.
##
cat $POSTGRES_CONFIG/postgresql.conf > $PGDATA/postgresql.conf

