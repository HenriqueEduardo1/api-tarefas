# Etapa de build
FROM ubuntu:latest AS build

# Instala pacotes necessários
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas o arquivo pom.xml para baixar dependências do Maven primeiro
COPY pom.xml .

# Baixa as dependências e armazena em cache
RUN mvn dependency:go-offline -B

# Copia o código-fonte do projeto
COPY src ./src

# Compila o projeto
RUN mvn clean install -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim

# Exposição da porta
EXPOSE 8080

# Copia o jar compilado da etapa anterior
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar app.jar

# Comando de entrada
ENTRYPOINT ["java", "-jar", "app.jar"]
