package mtech.irs.ekgs.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class GraphQuery {

	private String cypher;
	
	private Map<String, Object> params = new HashMap<>();
}
