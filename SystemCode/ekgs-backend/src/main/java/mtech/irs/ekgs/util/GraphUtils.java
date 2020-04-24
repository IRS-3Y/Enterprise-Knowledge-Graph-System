package mtech.irs.ekgs.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import mtech.irs.ekgs.service.GraphService;

abstract public class GraphUtils {
	
	private static Impl impl;

	public static GraphService service() {
		return impl.getService();
	}
	
	public static List<String> findRelationTypesAll(){
		final String cypher = "MATCH ()-[r]->() RETURN distinct type(r) as t";
		return service().queryList(cypher, null, "t", String.class);
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
