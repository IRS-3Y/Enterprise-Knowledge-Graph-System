#!/bin/sh

# wait for neo4j to start
logfile=/logs/debug.log
touch $logfile
chown neo4j:neo4j $logfile
while [ `cat $logfile | grep 'SERVER STARTED END' | wc -l` -eq 0 ]
do
  sleep 5
done

# run load data cypher
echo Loading EKGS data...
cp /ekgs/data/*.csv /var/lib/neo4j/import/
cat /ekgs/cypher/load_data.cypher | cypher-shell
echo EKGS data loaded