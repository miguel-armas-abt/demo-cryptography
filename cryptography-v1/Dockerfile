FROM eclipse-temurin:17-jdk-jammy

ARG user=admin
ARG group=demogroup
ARG uid=1000
ARG gid=1000

RUN groupadd -g ${gid} ${group} && useradd -u ${uid} -G ${group} -s /bin/sh ${user}

WORKDIR /app
ARG JAR_FILE="cryptography-v1-0.0.1-SNAPSHOT.jar"
COPY --chown=${uid}:${gid} target/${JAR_FILE} /app/app.jar
VOLUME /tmp

USER ${user}:${group}
ENV JAVA_OPTS=""

ADD target/${JAR_FILE} app.jar

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
