package mtech.irs.ekgs.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

/**
 * Search result which instructs a front-end action to be performed
 * 
 * @author tanshyi
 */
@Data
public class ActionSearchResult implements SearchResult{

	private String name;
	
	private Map<String, Object> params = new HashMap<>();

	@Override
	public String getType() {
		return "action";
	}
}
