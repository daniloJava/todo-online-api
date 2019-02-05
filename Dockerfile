#============================================================================================
# FROM [nome da imagem]:[versão/tag da imagem]
# Referência: https://docs.docker.com/engine/reference/builder/#from
#============================================================================================
FROM debian:stretch

#============================================================================================
# LABEL maintainer=[nome e e-mail do mantenedor da imagem]
# Referência: https://docs.docker.com/engine/reference/builder/#label
#============================================================================================
LABEL maintainer="Danilo Manoel Oliveira da Silva <danilo.manoel_oliveira@hotmail.com>"

#============================================================================================
# ARG <nome do argumento>[=<valor padrão>]
# Referência: https://docs.docker.com/engine/reference/builder/#arg
#
# A instrução ARG define uma variável que os usuários podem passar no tempo de compilação 
# para o construtor com o comando docker build.
#============================================================================================
ARG JAR_FILE
ARG PORT
ARG PROXY_URL

#============================================================================================
# ENV [nome da variável de ambiente]
# Referência: https://docs.docker.com/engine/reference/builder/#env
# 
# Variáveis de ambiente com o path da aplicação dentro do container.
#============================================================================================
ENV \
    SPRING_PROFILES_ACTIVE=docker \
    JAVA_OPTS='-Xms64m -Xmx64m' \
    DEBUG_OPTS= \
    http_proxy=${PROXY_URL} \
    https_proxy=${PROXY_URL}

RUN \
    apt -y update \
    && apt-get -y install openjdk-8-jre-headless curl --no-install-recommends \
    && rm -rf /var/lib/apt/lists/*

ENV \
    http_proxy=  \
    https_proxy=

VOLUME /tmp

EXPOSE 3088
#============================================================================================
# ADD [arquivo a ser copiado] [destino do arquivo copiado]
# Referência: https://docs.docker.com/engine/reference/builder/#add
#
# Adiciona o arquivo da aplicação, cuja nomenclatura é obtida a partir da variável ${JAR_FILE},
# para dentro do container sob o nome app.jar.
#============================================================================================
ADD target/${JAR_FILE} /app.jar

#============================================================================================
# ENTRYPOINT [executável seguido dos parâmetros]
# Referência: https://docs.docker.com/engine/reference/builder/#entrypoint
# 
# Inicia o container como um executável a partir da inicialização da aplicação. Essa instrução 
# é muito útil quando o container está em modo Swarm (Cluster de containers), pois caso a 
# aplicação caia, o container cai junto, indicando ao cluster aplicar a política de restart 
# configurada para a aplicação.
#============================================================================================
ENTRYPOINT exec java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar

#============================================================================================
# HEALTHCHECK --interval=[duração em segundos] --timeout=[duração em segundos]
# Referência: https://docs.docker.com/engine/reference/builder/#healthcheck
# 
# Diz ao Docker como testar um container para verificar se ele ainda está funcionando. Isso 
# pode detectar casos como um servidor web que está preso em um loop infinito e incapaz de 
# lidar com novas conexões, mesmo que o processo do servidor ainda esteja em execução.
#============================================================================================
# HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:3002/actuator/health || exit 1

