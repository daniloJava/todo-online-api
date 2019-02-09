#!/usr/bin/env bash
set -e

# Clears the previous build directory and packages the project.
mvn clean package -Pdevelopment -DskipTests

# Constructs the Docker image of the project from Dockerfile with profile "development".
mvn dockerfile:build -Pdevelopment

# Submits the Docker image generated to the Registry repository with profile "development".
# mvn dockerfile:push -Pdevelopment

# Performs the deploy of the Docker image in the Cluster Swarm
docker stack deploy --compose-file todo_online_docker-compose.local.yml --with-registry-auth APP

