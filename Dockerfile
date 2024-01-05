#
# Build stage
#
FROM arm32v7/adoptopenjdk AS build
ENV HOME=/usr/app
RUN mkdir -p $HOME
WORKDIR $HOME
ADD . $HOME
RUN --mount=type=cache,target=/root/.m2 ./mvnw -f $HOME/pom.xml clean package

#
# Package stage
#
FROM arm32v7/adoptopenjdk
ARG JAR_FILE=/usr/app/target/*.jar
COPY --from=build $JAR_FILE /app/runner.jar
EXPOSE 8080
WORKDIR /app
ENTRYPOINT java -jar ./runner.jar