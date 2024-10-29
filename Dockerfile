FROM eclipse-temurin:17.0.6_10-jre

LABEL org.opencontainers.image.title="culinary-user-service"
LABEL org.opencontainers.image.authors="wiktor czetyrbok"
LABEL org.opencontainers.image.version="0.0.1-SNAPSHOT"
LABEL org.opencontainers.image.description="Culinary application backend service"
LABEL org.opencontainers.image.licenses="MIT"

EXPOSE 8080

ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

COPY build/libs/culinary-user-service-0.0.1-SNAPSHOT.jar /opt/culinary-user-service/culinary-user-service.jar

CMD ["java", "-jar", "/opt/culinary-user-service/culinary-user-service.jar"]
