FROM anapsix/alpine-java:8_server-jre
WORKDIR /
ADD target/scala-2.13/telegram-assembly-0.1.0-SNAPSHOT.jar ./telegram.jar

CMD java -jar telegram.jar