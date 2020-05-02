package mtech.irs.ekgs.app;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AppSettings {
	
	private GraphSettings graph = new GraphSettings();
	
	public void merge(AppSettings settings) {
		if(settings != null) {
			getGraph().merge(settings.getGraph());
		}
	}

	@Data
	public static class GraphSettings {
		
		// Neo4j connection host (seen at front-end), use "" to follow same host as front-end
		private String host = "";
		
		// Neo4j connection port (seen at front-end)
		private Integer port = 7687;
		
		public void merge(GraphSettings settings) {
			if(settings != null) {
				this.host = settings.getHost() == null? this.host: settings.getHost();
				this.port = settings.getPort() == null? this.port: settings.getPort();
			}
		}
	}
}
