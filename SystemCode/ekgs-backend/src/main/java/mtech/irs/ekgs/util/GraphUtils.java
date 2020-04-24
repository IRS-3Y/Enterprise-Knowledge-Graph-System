package mtech.irs.ekgs.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import mtech.irs.ekgs.service.GraphService;

abstract public class GraphUtils {
	
	private static Impl impl;

	public static GraphService service() {
		return impl.getService();
	}
	
	public static List<String> findRelationTypesAll(){
		final String cypher = "CALL db.relationshipTypes YIELD relationshipType as t";
		return service().queryList(cypher, null, "t", String.class);
	}
	
	public static List<String> findNodePropValues(String nodeLabel, String propName, String propRegx, boolean trimValue){
		StringBuilder cypher = new StringBuilder("MATCH (n");
		if(StringUtils.isNotEmpty(nodeLabel)) {
			cypher.append(":").append(nodeLabel);
		}
		cypher.append(") ");
		if(StringUtils.isNotEmpty(propRegx)) {
			cypher.append("WHERE n.").append(propName).append(" =~ '").append(propRegx).append("' ");
		}
		cypher.append("RETURN DISTINCT ");
		if(trimValue) {
			cypher.append("trim(n.").append(propName).append(") as v");
		}else {
			cypher.append("n.").append(propName).append(" as v");
		}
		return service().queryList(cypher.toString(), null, "v", String.class);
	}
	
	public static List<String> findRelationPropValues(String relationType, String propName, String propRegx, boolean trimValue){
		StringBuilder cypher = new StringBuilder("MATCH ()-[r");
		if(StringUtils.isNotEmpty(relationType)) {
			cypher.append(":").append(relationType);
		}
		cypher.append("]->() ");
		if(StringUtils.isNotEmpty(propRegx)) {
			cypher.append("WHERE r.").append(propName).append(" =~ '").append(propRegx).append("' ");
		}
		cypher.append("RETURN DISTINCT ");
		if(trimValue) {
			cypher.append("trim(r.").append(propName).append(") as v");
		}else {
			cypher.append("r.").append(propName).append(" as v");
		}
		return service().queryList(cypher.toString(), null, "v", String.class);
	}
	
	public static class Impl {
		
		@Autowired
		private GraphService service;
		
		public GraphService getService() {
			return service;
		}

		@PostConstruct
		public void init() {
			impl = this;
		}
	}
}
