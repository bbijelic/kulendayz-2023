FROM openjdk:17-oraclelinux8
ADD /target/infobip-integration-*.jar service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service.jar"]
