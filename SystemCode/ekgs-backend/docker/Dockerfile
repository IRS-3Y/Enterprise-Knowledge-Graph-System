FROM openjdk:11.0.6-buster

# set environment
ENV BASEDIR /ekgs

# copy JAR file
WORKDIR ${BASEDIR}
COPY ekgs-backend-*.jar ${BASEDIR}/
RUN mv ${BASEDIR}/ekgs-backend-*.jar ${BASEDIR}/ekgs-backend.jar

ENTRYPOINT [ "java", "-jar", "ekgs-backend.jar" ]