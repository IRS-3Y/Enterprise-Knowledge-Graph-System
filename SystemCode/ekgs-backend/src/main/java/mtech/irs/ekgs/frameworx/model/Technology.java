package mtech.irs.ekgs.frameworx.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import lombok.Data;

@Data
@NodeEntity
public class Technology {

	@Id @GeneratedValue
    private Long id;
	
	private String name;
	
	private String longName;
}
