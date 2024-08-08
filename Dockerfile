# Utiliser une image de base Java officielle
FROM openjdk:17-jdk-slim

# Ajouter un label pour décrire l'image
LABEL maintainer="votre_email@example.com"

# Créer un répertoire pour l'application
RUN mkdir /app

# Définir le répertoire de travail pour toutes les instructions RUN, CMD, ENTRYPOINT, COPY et ADD
WORKDIR /app

# Copier le fichier jar de l'application dans le conteneur
COPY target/myapp-0.0.1-SNAPSHOT.jar /app/myapp.jar

# Exposer le port que l'application utilise
EXPOSE 8080

# Définir la commande pour exécuter l'application
CMD ["java", "-jar", "myapp.jar"]
