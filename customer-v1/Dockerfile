FROM openjdk:17-jdk-slim
ARG user=admin
ARG group=pocgroup
ARG uid=1000
ARG gid=1000
RUN groupadd -g ${gid} ${group} && useradd -u ${uid} -G ${group} -s /bin/sh ${user}
USER admin:pocgroup
VOLUME /tmp
ARG JAR_FILE="customer-v1-0.0.1-SNAPSHOT.jar"
ADD target/${JAR_FILE} app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh","-c","java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
