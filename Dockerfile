FROM openjdk:21-jdk

EXPOSE 8080
ADD target/spring-qa-assessment spring-qa-assessment
ENTRYPOINT ["java","jar","/spring-qa-assessment"]