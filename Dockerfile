FROM ubuntu:20.04

# Install OpenJDK-8
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk && \
    apt-get install -y ant && \
    apt-get clean;

# Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

# Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk-amd64/
RUN export JAVA_HOME

RUN apt-get -y install python3-pip
RUN pip3 install --upgrade pip

EXPOSE 8080:8080

COPY . server
WORKDIR /server

CMD ["./gradlew", ":ij-starter:runIde", "-Pconfig=./ij-starter/src/main/resources"]
