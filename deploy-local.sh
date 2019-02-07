#!/usr/bin/env bash
set -e

# Limpa o diretório de build anterior e empacota o projeto.
mvn clean package -Pdevelopment -DskipTests

# Constrói a imagem Docker do projeto a partir do Dockerfile com profile "development".
mvn dockerfile:build -Pdevelopment

# Submete a imagem Docker gerada para o repositório Registry com profile "development".
# mvn dockerfile:push -Pdevelopment

# Executa o deploy da imagem Docker no Cluster Swarm
# docker stack deploy --compose-file todo_online_docker-compose.local.yml --with-registry-auth SSP

