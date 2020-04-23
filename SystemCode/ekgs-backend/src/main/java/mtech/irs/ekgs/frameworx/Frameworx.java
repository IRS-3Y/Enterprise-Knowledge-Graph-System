package mtech.irs.ekgs.frameworx;

import java.util.Arrays;
import java.util.List;
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
	
	private static final String[] SEARCH_PROCESS_STREAMS = new String[] {"Request_to_Answer"};
	
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
					results.addAction("view", Map.of(
							"graph", cypherForNodeScan(label, n, 3),
							"description", descForNodeScan(n, 3)));
				});
		}
	}
	
	public static String cypherForNodeScan(String label, String name, int depth) {
		String cypher = 
				"MATCH(n:" + label + "{longName:'" + name + "'}) " + 
				"CALL apoc.path.spanningTree(n, {minLevel: 1, maxLevel: " + depth + "}) " + 
				"YIELD path " + 
				"RETURN path";
		logger.debug("Cypher: {}", cypher);
		return cypher;
	}
	
	public static List<String> descForNodeScan(String name, int depth) {
		return Arrays.asList(
				"Please refer to the graph for the 360-degree scan for node (" + name + ") with depth limit " + depth + ".",
				"If you wish to view more information about this node, please query again using a higher depth limit ranging from 1 to 3.");
	}
	
	public static void searchSuggestionForProcessStreamScan(SearchResults results, String prefix) {
		Stream.of(SEARCH_PROCESS_STREAMS).forEach(l -> {
			results.addSuggestion(prefix + l);
		});
	}
	
	public static void searchResultForProcessStreamScan(SearchResults results, SearchInput input) {
		final String value = input.getValue();
		String streamName = Stream.of(SEARCH_PROCESS_STREAMS)
				.filter(l -> value.contains(l))
				.findFirst()
				.orElse(null);
		if(streamName != null) {
			results.addAction("view", Map.of(
					"graph", cypherForProcessStreamScan(streamName),
					"description", descForProcessStreamScan(streamName)));
		}
	}
	
	public static String cypherForProcessStreamScan(String streamName) {
		String cypher = "MATCH p=()-[r:" + streamName + "]->() RETURN p";
		logger.debug("Cypher: {}", cypher);
		return cypher;
	}
	
	public static List<String> descForProcessStreamScan(String streamName) {
		return Arrays.asList(
				"Please refer to the graph for the end-to-end business process group Customer Centric end-to-end process.",
				"If you wish to view certain sub-process in this graph, please use Relationship scan for that specific sub process within this process group.");
	}
	
	public static void searchResultForShortestPath(SearchResults results, SearchInput input) {
		final String value = input.getValue();
		if(value.contains("time")) {
			results.addAction("view", Map.of("graph", cypherForShortestPath("relationTime")));
		}else if(value.contains("cost")) {
			results.addAction("view", Map.of("graph", cypherForShortestPath("relationCost")));
		}
	}
	
	public static String cypherForShortestPath(String weightProperty) {
		String cypher = 
				"MATCH (start {name:'Customer1'}), (end {name:'CIR1'}) " + 
				"CALL algo.shortestPath.stream(start, end, '" + weightProperty + "',{direction:'OUTGOING'}) " + 
				"YIELD nodeId, cost " + 
				"RETURN algo.asNode(nodeId)";
		logger.debug("Cypher: {}", cypher);
		return cypher;
	}
}
