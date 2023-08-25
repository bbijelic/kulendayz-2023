FROM openjdk:17-oraclelinux8
ADD /target/auth-service-*.jar service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service.jar"]
