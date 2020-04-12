package mtech.irs.ekgs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableNeo4jRepositories("mtech.irs.ekgs")
@EnableTransactionManagement
public class Neo4jConfiguration {

}
