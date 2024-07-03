FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="culinary-user-service"
LABEL org.opencontainers.image.authors="wiktor czetyrbok"
LABEL org.opencontainers.image.version="0.0.1-SNAPSHOT"
LABEL org.opencontainers.image.description="Culinary application backend service"
LABEL org.opencontainers.image.licenses="MIT"

LABEL build_version=""
LABEL maintainer=""

ENV VERSION="0.0.1-SNAPSHOT"

#ENV SERVER_PORT=8080
#
#ENV SPRING_DATASOURCE_URL=jdbc:h2:mem:city
#ENV SPRING_DATASOURCE_DRIVERCLASSNAME=org.h2.Driver
#ENV SPRING_DATASOURCE_USERNAME=sa
#ENV SPRING_DATASOURCE_PASSWORD=password
#
#ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.H2Dialect
#ENV SPRING_JPA_GENERATE_DDL=true
#ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
#
#
#ENV ISA_CITIZEN_SERVICE_URL=http://citizen-service:8080/


EXPOSE 8080


COPY build/libs/culinary-user-service-${VERSION}.jar /opt/culinary-user-service/culinary-user-service.jar

CMD ["java", "-jar", "/opt/culinary-user-service/culinary-user-service.jar"]
