#============================================================================================
# FROM [Image Reference]:[version/image tag]
# Reference: https://docs.docker.com/engine/reference/builder/#from
#============================================================================================
FROM postgres:latest

#============================================================================================
# LABEL maintainer=[Name and e-mail]
# Reference: https://docs.docker.com/engine/reference/builder/#label
#============================================================================================
LABEL maintainer="Danilo Manoel Oliveira da Silva <danilo.manoel_oliveira@hotmail.com>"

#============================================================================================
# ENV [name variable]
# Reference: https://docs.docker.com/engine/reference/builder/#env
# 
# Below are the variables used to configure the PostgreSQL server:
#
# LANG
# Defines the default locale that will be used by PostgreSQL.
#
# PGDATA
# This environment variable can be used to define another location, such as a subdirectory,
# to store the database files. The default is '/ var / lib / postgresql / data', but
# If the volume of data used is a File System mount point, the Postgres
# recommends creating a subdirectory (for example, / var / lib / postgresql / data / pgdata) to contain
# the data. 
#
# POSTGRES_CONFIG
# Temporary directory to store files that can be discarded later.
#
# POSTGRES_DB
# Used to define a name for the default database that is created at the start of the image
# for the first time. If not specified, the value of the POSTGRES_USER variable will be used as
# Bank name.
#
# POSTGRES_USER
# Used in conjunction with the POSTGRES_PASSWORD variable to set a user and password. It is
# variable will create a user with administrative (superuser) privileges. If not informed,
# the default user "postgres" will be used.
#
# POSTGRES_PASSWORD
# Used in conjunction with the POSTGRES_USER variable to set a user and password. It is
# variable is recommended to allow access from other hosts, forcing the user
# to connect to the bank with a password.
#============================================================================================
ENV \
    PGDATA=/var/lib/postgresql/data/pgdata \
    POSTGRES_CONFIG=/tmp \
    POSTGRES_DB=todo \
    POSTGRES_USER=postgres \
    POSTGRES_PASSWORD=postgres

#============================================================================================
# ADD [Folder to be copy] [destination of copied file]
# Reference: https://docs.docker.com/engine/reference/builder/#add
#
# Below are the files used to configure the PostgreSQL server:
#
# - "build/init-config.sh"
# File responsible for running some settings in the Postgres environment, such as
# example the overwriting of the default "postgresql.conf" file by another custom, since
# this file is generated in Runtime.
#
# - "build/init-user-db.sh"
# File to create a user and database in PostgreSQL. This script is only for
# Demonstrate the procedure required to create bank objects (DDL) when executing the image
# for the first time.
#
# - "build/postgresql.conf"
# PostgreSQL config file. It is possible to set the maximum quantity of
# allowed connections, connections timeout, list of IPs that can access the database,
# mechanism of rotation of generated logs, among other resources.
#============================================================================================
COPY [ "build/init-config.sh", "build/init-user-db.sh", "/docker-entrypoint-initdb.d/" ]
COPY build/postgresql.conf $POSTGRES_CONFIG/postgresql.conf

#============================================================================================
# RUN [commands to be executed]
# Reference: https://docs.docker.com/engine/reference/builder/#run
# 
# The RUN instruction will execute any command over a new layer of the current image and
# will confirm the results. The resulting image will be used for the next step in Dockerfile.
#============================================================================================
RUN \
    apt-get -y update \
    && rm -rf /var/lib/apt/lists/* 
