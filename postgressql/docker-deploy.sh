#!/usr/bin/env bash
set -e

DOCKER_TAG=postgres:latest

# Constrói a imagem customizada do PostgreSQL
docker build --tag ${DOCKER_TAG} .

# Envia a imagem construída para o repositório Registry
# docker push ${DOCKER_TAG}

# docker-compose up

# Executa o deploy da imagem Docker no Cluster Swarm
# docker stack deploy --compose-file docker-compose.local.yml --with-registry-auth INFRA

