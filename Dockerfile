# --- Stage 1: build the Angular app ----------------------------------------
FROM node:20-alpine AS frontend-builder
WORKDIR /frontend
COPY frontend/package.json frontend/package-lock.json ./
RUN npm ci
COPY frontend/ ./
RUN npm run build

# --- Stage 2: build the Spring Boot fat JAR with Angular files bundled in ---
FROM maven:3.9-eclipse-temurin-21 AS backend-builder
WORKDIR /build
COPY backend/pom.xml ./pom.xml
# Prime the Maven cache so later source changes don't re-download everything.
RUN mvn -q -B -DskipTests dependency:go-offline
COPY backend/src ./src
# Drop the Angular production build into Spring Boot's static/ directory
# so Spring serves it at the same origin as the API.
COPY --from=frontend-builder /frontend/dist/document-search/browser ./src/main/resources/static
RUN mvn -q -B -DskipTests package

# --- Stage 3: small runtime image ------------------------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=backend-builder /build/target/document-search.jar app.jar
# Render injects PORT at runtime; Spring reads it via application.yml.
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java -XX:MaxRAMPercentage=75 -jar app.jar"]
