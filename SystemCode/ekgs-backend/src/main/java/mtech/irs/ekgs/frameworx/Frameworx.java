package mtech.irs.ekgs.frameworx;

import java.util.stream.Stream;

import mtech.irs.ekgs.frameworx.service.FrameworxService;
import mtech.irs.ekgs.model.SearchResults;
import mtech.irs.ekgs.util.AppContextUtils;

/**
 * Static helper methods
 * 
 * @author tanshyi
 */
abstract public class Frameworx {
	
	private static final String[] SEARCH_NODE_LABELS = new String[] {"People", "Process", "Technology"};
	
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
}
