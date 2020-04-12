package mtech.irs.ekgs.frameworx.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import mtech.irs.ekgs.frameworx.model.Technology;

@Repository
public interface TechnologyRepository extends Neo4jRepository<Technology, Long>{

}
