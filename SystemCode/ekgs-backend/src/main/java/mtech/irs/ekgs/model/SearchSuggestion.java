package mtech.irs.ekgs.model;

import lombok.Data;

/**
 * Represents search bar suggestions based on current user input.
 * It could be intermediate search result.
 * 
 * @author tanshyi
 */
@Data
public class SearchSuggestion implements SearchResult{

	private String value;

	@Override
	public String getType() {
		return "suggestion";
	}
	
}
