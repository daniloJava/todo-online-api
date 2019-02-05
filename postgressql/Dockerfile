#============================================================================================
# FROM [nome da imagem]:[versão/tag da imagem]
# Referência: https://docs.docker.com/engine/reference/builder/#from
# 
#============================================================================================
FROM postgres:latest

#============================================================================================
# LABEL maintainer=[nome e e-mail do mantenedor da imagem]
# Referência: https://docs.docker.com/engine/reference/builder/#label
#
# Indica o responsável/autor por manter a imagem.
#============================================================================================
LABEL maintainer="Danilo Manoel Oliveira da Silva <danilo.manoel_oliveira@hotmail.com>"

#============================================================================================
# ENV [nome da variável de ambiente]
# Referência: https://docs.docker.com/engine/reference/builder/#env
#
# Abaixo estão as variáveis utilizadas para configurar o servidor do PostgreSQL:
#
# LANG
# Define o locale padrão que será utilizado pelo PostgreSQL.
#
# PGDATA
# Essa variável de ambiente pode ser usada para definir outro local, como um subdiretório, 
# para armazenar os arquivos do banco de dados. O padrão é '/var/lib/postgresql/data', mas 
# se o volume de dados usado for um ponto de montagem File System, o time do Postgres 
# recomenda criar um subdiretório (por exemplo, /var/lib/postgresql/data/pgdata) para conter 
# os dados. 
#
# POSTGRES_CONFIG
# Diretório temporário para armazenar arquivos que poderão ser descartados posteriormente.
#
# POSTGRES_DB
# Usada para definir um nome para o banco de dados padrão que é criado ao iniciar a imagem 
# pela primeira vez. Se não especificado, o valor da variável POSTGRES_USER será usado como 
# nome do banco.
#
# POSTGRES_USER
# Usada em conjunto com a variável POSTGRES_PASSWORD para definir um usuário e senha. Esta 
# variável criará um usuário com privilégios administrativo (superusuário). Se não informada, 
# o usuário padrão "postgres" será usado.
#
# POSTGRES_PASSWORD
# Usada em conjunto com a variável POSTGRES_USER para definir um usuário e senha. Esta 
# variável é recomendada para permitir o acesso a partir de outros hosts, obrigando o usuário 
# a conectar-se no banco com uma senha.
#============================================================================================
ENV \
    PGDATA=/var/lib/postgresql/data/pgdata \
    POSTGRES_CONFIG=/tmp \
    POSTGRES_DB=todo \
    POSTGRES_USER=postgres \
    POSTGRES_PASSWORD=postgres

#============================================================================================
# COPY [arquivo a ser copiado] [destino do arquivo copiado]
# Referência: https://docs.docker.com/engine/reference/builder/#copy
#
# Abaixo estão os arquivos utilizados para configurar o servidor do PostgreSQL:
#
# - "build/init-config.sh"
# Arquivo responsável pela execução de algumas configurações no ambiente Postgres, como por 
# exemplo a sobrescrita do arquivo "postgresql.conf" padrão por um outro customizado, já que 
# esse arquivo é gerado em Runtime.
#
# - "build/init-user-db.sh"
# Arquivo para criar um usuário e banco de dados no PostgreSQL. Esse script é apenas para 
# demonstrar o procedimento necessário para criar objetos de banco (DDL) ao executar a imagem 
# pela primeira vez.
#
# - "build/postgresql.conf"
# Arquivo de configuirações do PostgreSQL. Nele é possível definir a quantidade máxima de 
# conexões permitidas, timeout das conexões, lista de IPs que poderão acessar o banco, 
# mecanismo de rotação dos logs gerados, entre outros recursos. 
#============================================================================================
COPY [ "build/init-config.sh", "build/init-user-db.sh", "/docker-entrypoint-initdb.d/" ]
COPY build/postgresql.conf $POSTGRES_CONFIG/postgresql.conf

#============================================================================================
# RUN [comandos a serem executados]
# Referência: https://docs.docker.com/engine/reference/builder/#run
# 
# A instrução RUN executará qualquer comando sobre uma nova camada da imagem atual e 
# confirmará os resultados. A imagem resultante será usada para o próximo passo no Dockerfile.
#============================================================================================
RUN \
    apt-get -y update \
    && rm -rf /var/lib/apt/lists/* 
