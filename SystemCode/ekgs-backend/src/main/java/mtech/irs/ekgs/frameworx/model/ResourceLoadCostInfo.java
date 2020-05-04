package mtech.irs.ekgs.frameworx.model;

import java.util.Map;

import lombok.Data;

@Data
public class ResourceLoadCostInfo {

	private long id;
	
	private String label;
	
	private String name;
	
	private double resource = 1.0;
	
	private double loadWeight = 1.0;
	
	private double load = 1.0;
	
	private double cost = 1.0;
	
	private double time = 1.0;
	
	public static ResourceLoadCostInfo of(Map<String,Object> node) {
		ResourceLoadCostInfo info = new ResourceLoadCostInfo();
		info.setId((Long)node.get("id"));
		info.setLabel((String)node.get("label"));
		info.setName((String)node.get("name"));
		Double resource = (Double)node.get("resource");
		if(resource != null && resource > 1) {
			info.setResource(resource);
		}
		Double loadWeight = (Double)node.get("loadWeight");
		if(loadWeight != null && loadWeight > 1) {
			info.setLoadWeight(loadWeight);
		}
		Double load = (Double)node.get("load");
		if(load != null && load > 0) {
			info.setLoad(load);
		}
		return info;
	}
}
