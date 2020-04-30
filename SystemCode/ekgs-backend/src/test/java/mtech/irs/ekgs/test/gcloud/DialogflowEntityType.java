package mtech.irs.ekgs.test.gcloud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.util.Assert;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DialogflowEntityType {

	private String name;
	
	private boolean allowFuzzyExtraction;
	
	private boolean automatedExpansion;
	
	@JsonProperty("isRegexp")
	private boolean regexp;
	
	@JsonProperty("isEnum")
	private boolean enumm;
	
	@JsonProperty("isOverridable")
	private boolean overridable;
	
	private List<Map<String,Object>> entries = new ArrayList<>();
	
	public void addEntry(String value, String ... synonyms) {
		Assert.hasLength(value, "value cannot be empty");
		List<String> synonymsList = new ArrayList<>();
		synonymsList.add(value);
		if(synonyms != null) {
			Stream.of(synonyms).forEach(s -> synonymsList.add(s));
		}
		Map<String,Object> entry = new HashMap<>();
		entry.put("value", value);
		entry.put("synonyms", synonymsList);
		entries.add(entry);
	}
	
}
