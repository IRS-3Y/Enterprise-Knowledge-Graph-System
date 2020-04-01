package mtech.irs.ekgs.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Holds list of {@link SearchResult}
 * 
 * @author tanshyi
 */
@Data
public class SearchResults {

	private List<SearchResult> results = new ArrayList<>();
}
