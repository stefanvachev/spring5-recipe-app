FROM ubuntu_python_java_bash
COPY /target/spring5-recipe-app-0.0.1-SNAPSHOT.jar /apps/java
EXPOSE 8080
ENTRYPOINT ["java","-jar","/apps/java/spring5-recipe-app-0.0.1-SNAPSHOT.jar"]