package mtech.irs.ekgs.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Holds list of {@link SearchResult}
 * 
 * @author tanshyi
 */
@Data
public class SearchResults {

	private List<SearchResult> results = new ArrayList<>();
	
	public void add(SearchResult result) {
		results.add(result);
	}
	
	public void addSuggestion(String value) {
		SearchSuggestion result  = new SearchSuggestion();
		result.setValue(value);
		results.add(result);
	}
	
	public void addAction(String name, Map<String, Object> params) {
		ActionSearchResult result = new ActionSearchResult();
		result.setName(name);
		result.setParams(params);
		results.add(result);
	}
}
