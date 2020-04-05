FROM neo4j:3.5.17

# set environment
ENV EKGS_ROOT /ekgs

# copy custom scripts and data
COPY cypher/*.cypher ${EKGS_ROOT}/cypher/
COPY data/*.csv ${EKGS_ROOT}/data/
COPY init.sh ${EKGS_ROOT}/
COPY init/*.sh ${EKGS_ROOT}/init/
RUN mkdir ${EKGS_ROOT}/init_history
COPY neo4j.conf ${EKGS_ROOT}/

# install neo4j plugins
COPY plugins/*.jar /var/lib/neo4j/plugins/
RUN cd /var/lib/neo4j/plugins \
 && wget https://github.com/neo4j-contrib/neo4j-apoc-procedures/releases/download/3.5.0.9/apoc-3.5.0.9-all.jar \
 && chown neo4j:neo4j ./*.jar

# update neo4j conf file
RUN cat ${EKGS_ROOT}/neo4j.conf >> /var/lib/neo4j/conf/neo4j.conf
