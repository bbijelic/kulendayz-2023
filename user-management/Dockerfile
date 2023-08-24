FROM openjdk:17-oraclelinux8
ADD /target/user-management-*.jar service.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/service.jar"]
