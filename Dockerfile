# build image : `docker build -t brapicoordinatorselby/brapi-java-server ./`
# run container (dev): `docker run --name=brapi-test-server --network=bridge -p 8081:8081 -d brapicoordinatorselby/brapi-java-server`
# run container (prod): `docker run --name=brapi-test-server --restart always --network=brapi_net -d brapicoordinatorselby/brapi-java-server`

# FROM nimmis/java-centos:oracle-8-jre
FROM openjdk:8-jdk-alpine

EXPOSE 8787

RUN mkdir /home/rocket-chat-1/brapi

COPY target/brapi-Java-TestServer-0.1.0.jar src/main/resources/ /home/rocket-chat-1/brapi/

CMD java -cp "/home/rocket-chat-1/brapi/:/home/rocket-chat-1/brapi/brapi-Java-TestServer-0.1.0.jar" org.springframework.boot.loader.JarLauncher

ENTRYPOINT ["java","-jar","/brapi-Java-TestServer-0.1.0.jar"]