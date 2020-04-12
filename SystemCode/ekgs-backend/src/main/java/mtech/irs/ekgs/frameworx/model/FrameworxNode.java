package mtech.irs.ekgs.frameworx.model;

import org.springframework.data.neo4j.annotation.QueryResult;

import lombok.Data;

/**
 * POJO for query common node information (e.g. id, name ...)
 * 
 * @author tanshyi
 */
@Data
@QueryResult
public class FrameworxNode {

	private Long id;
	
	private String name;
	
	private String longName;
}
