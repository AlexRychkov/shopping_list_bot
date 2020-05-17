FROM anapsix/alpine-java:8_server-jre
WORKDIR /
ADD target/scala-2.13/backend-assembly-0.1.0-SNAPSHOT.jar ./backend.jar

CMD java -jar backend.jar