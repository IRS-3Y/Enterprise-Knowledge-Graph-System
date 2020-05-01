FROM openjdk:11.0.6-buster

# set environment
ENV BASEDIR /ekgs
ENV GOOGLE_APPLICATION_CREDENTIALS /ekgs/dialogflow-key.json

# copy JAR file
WORKDIR ${BASEDIR}
COPY ekgs-backend-*.jar ${BASEDIR}/
RUN mv ${BASEDIR}/ekgs-backend-*.jar ${BASEDIR}/ekgs-backend.jar

# copy Dialogflow key
COPY dialogflow-key.json ${BASEDIR}/

ENTRYPOINT [ "java", "-jar", "ekgs-backend.jar" ]