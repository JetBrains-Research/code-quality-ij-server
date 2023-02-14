FROM openjdk:17-jdk-slim

RUN apt-get update
RUN apt-get install -y python3-pip
RUN apt-get install unzip

RUN mkdir data
WORKDIR server
COPY . ./

RUN ./gradlew build

CMD ["./gradlew", ":ij-starter:runIde", "-Pconfig=/server/ij-starter/src/main/resources/docker-server-config.json"]