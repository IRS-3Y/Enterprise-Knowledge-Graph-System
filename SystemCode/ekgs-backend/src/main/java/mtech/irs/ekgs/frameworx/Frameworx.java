package mtech.irs.ekgs.frameworx;

import java.util.Map;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mtech.irs.ekgs.frameworx.service.FrameworxService;
import mtech.irs.ekgs.model.SearchInput;
import mtech.irs.ekgs.model.SearchResults;
import mtech.irs.ekgs.util.AppContextUtils;

/**
 * Static helper methods
 * 
 * @author tanshyi
 */
abstract public class Frameworx {
	
	private static final String[] SEARCH_NODE_LABELS = new String[] {"People", "Process", "Technology"};
	
	private static final Logger logger = LoggerFactory.getLogger(Frameworx.class);
	
	private static FrameworxService service;
	
	public static FrameworxService service() {
		if(service == null) {
			service = AppContextUtils.getBean(FrameworxService.class);
		}
		return service;
	}
	
	/**
	 * Populate search suggestions with node labels applicable to node scan
	 * @param results
	 * @param prefix
	 */
	public static void searchSuggestionForNodeScan(SearchResults results, String prefix) {
		Stream.of(SEARCH_NODE_LABELS).forEach(l -> {
			results.addSuggestion(prefix + l);
		});
	}
	
	/**
	 * Populate search suggestions for node scan with specific node label
	 * @param results
	 * @param prefix
	 * @param label
	 */
	public static void searchSuggestionForNodeScan(SearchResults results, String prefix, String label) {
		service().findNodeNames(label).forEach(n -> {
			results.addSuggestion(prefix + label + " " + n);
		});
	}
	
	/**
	 * Populate search result for plotting node scan graph, if input matches one of the node
	 * @param results
	 * @param input
	 */
	public static void searchResultForNodeScan(SearchResults results, SearchInput input) {
		final String value = input.getValue();
		String label = Stream.of(SEARCH_NODE_LABELS)
				.filter(l -> value.contains(l + " "))
				.findFirst()
				.orElse(null);
		if(label != null) {
			String nodeName = value.substring(value.indexOf(label) + label.length()).trim();
			service().findNodeNames(label).stream()
				.filter(n -> n.equalsIgnoreCase(nodeName))
				.findFirst()
				.ifPresent(n -> {
					results.addAction("view", Map.of("graph", cypherForNodeScan(label, n)));
				});
		}
	}
	
	public static String cypherForNodeScan(String label, String name) {
		String cypher = "MATCH(n:" + label + "{longName:'" + name + "'})-[r]->(m) RETURN *";
		logger.debug("Cypher: {}", cypher);
		return cypher;
	}
}
