#!/usr/bin/env bash
set -e

# Clears the previous build directory and packages the project.
docker pull danilojava/todo-online:todo-online-api

# Constructs the Docker image of the project from Dockerfile with profile "development".
docker pull danilojava/todo-online:postgres

# Submits the Docker image generated to the Registry repository with profile "development".
# mvn dockerfile:push -Pdevelopment

# Performs the deploy of the Docker image in the Cluster Swarm
docker stack deploy --compose-file todo_online_docker-compose.prod.yml --with-registry-auth APP

