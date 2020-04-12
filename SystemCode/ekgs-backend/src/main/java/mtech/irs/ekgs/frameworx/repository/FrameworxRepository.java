package mtech.irs.ekgs.frameworx.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mtech.irs.ekgs.frameworx.model.Frameworx;
import mtech.irs.ekgs.frameworx.model.FrameworxNode;

@Repository
public interface FrameworxRepository extends Neo4jRepository<Frameworx, Long>{

	@Query(
		"MATCH (n) WHERE $label in labels(n) " +
		"RETURN id(n) as id, n.name as name, n.longName as longName"
	)
	List<FrameworxNode> findNodeByLabel(@Param("label") String label);
}
