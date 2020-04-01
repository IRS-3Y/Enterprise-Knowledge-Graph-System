package mtech.irs.ekgs.service;

import mtech.irs.ekgs.model.SearchInput;
import mtech.irs.ekgs.model.SearchResults;

/**
 * Provides searching functionality based on user input
 * 
 * @author tanshyi
 */
public interface SearchService {
	
	SearchResults search(SearchInput input);

}
