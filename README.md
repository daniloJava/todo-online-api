# todo-online-api

Simple API for task control, task CRUD separated by groups and users.
### Stack

 - Java 8
 - Spring boot
 - Spring data
 - Spring Security
 - Maven
 - Postgres
 - lombok

### Installation

To build the application has two forms.
##### 1. Deploy by Docker
In the project root folder, there is a file called docker-compose.yml. That contains the deploy of two images referring to the postgres, database used in the project, and the application itself, these images are available in the 
[Docker HUB repository ](https://hub.docker.com/r/danilojava/todo-online/tags)
To run via docker compose, you need to be with the docker-compose.yml file and run the following command:
```sh
$ docker-compose up
```

If Docker Swarm is installed on the machine. We can use the file todo_online_docker-compose.prod.yml, but first you need to download the images

```sh
$ docker pull danilojava/todo-online:todo-online-api
$ docker pull danilojava/todo-online:postgres
```
And then run the command:
```sh
$ docker stack deploy --compose-file todo_online_docker-compose.prod.yml --with-registry-auth APP
```
If the operating system is linux, there is a deploy-prod.sh file that already executes the above commands to deploy. To execute it simply enter the project folder and execute:
```sh
$ ./deploy-prod.sh
```

##### Deploy by local
If it is not possible to download the image from the [Docker HUB repository ](https://hub.docker.com/r/danilojava/todo-online/tags), we can create the image locally. with the following commands from the root folder of the project:

First we need to upload the database, from the root folder of the project, enter the folder postgressql:
```sh
$ cd ./postgres
$ docker build --tag postgres .
```
back to the project root folder
```sh
$ cd ../
$ mvn clean package -Pdevelopment -DskipTests
$ mvn dockerfile:build -Pdevelopment
$ docker stack deploy --compose-file todo_online_docker-compose.local.yml --with-registry-auth APP
```

Or if it's on Linux, we can use the deploy-local.sh file
```sh
$ ./deploy-local.sh
```

### API
From the application port to 3088, we can access the local application through http://127.0.0.1:3088/task/swagger-ui.html#/ and know all Andpoints available in the API.

To access via Rest, in the Header you need to get the token in the following link:
http://127.0.0.1:3088/task/auth/signin
passing on the body:
{"username": "admin", "password": "password"}
or
{"username": "user", "password": "password"}
with the following response:
{"username": "user", "token": "...."}

When accessing some URL passes in the Header the Followed Authorization of Bearer and the Token generated.


