#!/usr/bin/env bash
set -e

DOCKER_TAG=postgres

# Builds the custom PostgreSQL image
docker build --tag ${DOCKER_TAG} .

# Sends the constructed image to the Registry repository
# docker push ${DOCKER_TAG}

# docker-compose up

# Performs the deploy of the Docker image in the Cluster Swarm
docker stack deploy --compose-file docker-compose.local.yml --with-registry-auth APP

