# build image : `docker build -t brapicoordinatorselby/brapi-java-server ./`
# run container (dev): `docker run --name=brapi-test-server --network=bridge -p 8081:8081 -d brapicoordinatorselby/brapi-java-server`
# run container (prod): `docker run --name=brapi-test-server --restart always --network=brapi_net -d brapicoordinatorselby/brapi-java-server`

# FROM nimmis/java-centos:oracle-8-jre
FROM openjdk:8-jdk-alpine

EXPOSE 8787

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} brapi.jar
ENTRYPOINT ["java","-jar","/brapi.jar"]