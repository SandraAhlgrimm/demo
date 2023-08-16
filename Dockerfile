#############
# Build layer
#############
FROM mcr.microsoft.com/openjdk/jdk:17-mariner AS mavenBuild

    COPY pom.xml /tmp/
    COPY src /tmp/src/
    COPY mvnw /tmp/mvnw
    COPY .mvn /tmp/.mvn

    WORKDIR /tmp/

    RUN ./mvnw package

#--------------
# Runtime image
#--------------
FROM mcr.microsoft.com/openjdk/jdk:17-distroless

    COPY --from=mavenBuild /tmp/target/*.jar /example.jar
    CMD ["-jar", "/example.jar"]