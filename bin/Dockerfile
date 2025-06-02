FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Instala Maven (caso não use wrapper)
RUN apk add --no-cache maven

# Copia dependências (pode ser ignorado em dev)
COPY . /app

# Usa o Maven wrapper
CMD ["./mvnw", "spring-boot:run"]
