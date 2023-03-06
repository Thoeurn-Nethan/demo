FROM openjdk:11

ADD target/*.jar  test.jar

ENTRYPOINT ["java","-jar","test.jar"]



