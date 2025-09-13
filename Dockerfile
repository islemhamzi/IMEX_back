# Utilisez l'image officielle Java comme image parent
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR de l'application dans le conteneur
COPY target/Authentication-0.0.1-SNAPSHOT.jar /app/Authentication-0.0.1-SNAPSHOT.jar

# Exposer le port 8080
EXPOSE 8080

# Définir les variables d'environnement
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/Imex_finale
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=root
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true

# Spécifier la commande pour exécuter le fichier JAR
ENTRYPOINT ["java", "-jar", "/app/Authentication-0.0.1-SNAPSHOT.jar"]

