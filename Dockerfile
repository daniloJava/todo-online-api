#============================================================================================
# FROM [Image Reference]:[version/image tag]
# Reference: https://docs.docker.com/engine/reference/builder/#from
#============================================================================================
FROM debian:stretch

#============================================================================================
# LABEL maintainer=[Name and e-mail]
# Reference: https://docs.docker.com/engine/reference/builder/#label
#============================================================================================
LABEL maintainer="Danilo Manoel Oliveira da Silva <danilo.manoel_oliveira@hotmail.com>"

#============================================================================================
# ARG <name argument>[=<default value>]
# Reference: https://docs.docker.com/engine/reference/builder/#arg
#============================================================================================
ARG JAR_FILE
ARG PORT
ARG PROXY_URL

#============================================================================================
# ENV [name variable]
# Reference: https://docs.docker.com/engine/reference/builder/#env
# 
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
# ADD [Folder to be copy] [destination of copied file]
# Reference: https://docs.docker.com/engine/reference/builder/#add
#
#============================================================================================
ADD target/${JAR_FILE} /app.jar

#============================================================================================
# ENTRYPOINT [executable followed by the parameters]
# Reference: https://docs.docker.com/engine/reference/builder/#entrypoint
# 
#============================================================================================
ENTRYPOINT exec java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar
