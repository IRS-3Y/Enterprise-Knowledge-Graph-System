package mtech.irs.ekgs.util;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import mtech.irs.ekgs.service.GraphService;

abstract public class GraphUtils {
	
	private static Impl impl;

	public static GraphService service() {
		return impl.getService();
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
