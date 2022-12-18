FROM maven:3.8.4-openjdk-17-slim AS build
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml verify clean -q --fail-never
COPY src /home/app/src
RUN mvn -f /home/app/pom.xml -DskipTests=true package
FROM quay.io/keycloak/keycloak:20.0.0
COPY --from=build /home/app/target/keycloak-1.0-SNAPSHOT.jar /opt/keycloak/providers/my-jar.jar
COPY ./my-theme/ /opt/keycloak/themes/new-theme/
RUN /opt/keycloak/bin/kc.sh build --db=postgres --http-relative-path=/auth
