FROM openjdk:11.0-jre-slim-sid

MAINTAINER Rana Das [rana.pratap.das@gmail.com]

COPY ./target/crossword-0.0.1-SNAPSHOT.jar /usr/src/cross/
WORKDIR /usr/src/cross
EXPOSE 8080
CMD ["java", "-jar", "crossword-0.0.1-SNAPSHOT.jar"]