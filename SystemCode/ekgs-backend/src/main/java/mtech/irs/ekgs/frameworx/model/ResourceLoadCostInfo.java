package mtech.irs.ekgs.frameworx.model;

import java.util.Map;

import lombok.Data;

@Data
public class ResourceLoadCostInfo {

	private long id;
	
	private String label;
	
	private double resource = 1.0;
	
	private double load = 1.0;
	
	private double cost = 1.0;
	
	private double time = 1.0;
	
	public static ResourceLoadCostInfo of(Map<String,Object> node) {
		ResourceLoadCostInfo info = new ResourceLoadCostInfo();
		info.setId((Long)node.get("id"));
		info.setLabel((String)node.get("label"));
		Double resource = (Double)node.get("resource");
		if(resource != null && resource > 1) {
			info.setResource(resource);
		}
		Double load = (Double)node.get("load");
		if(load != null && load > 0) {
			info.setLoad(load);
		}
		return info;
	}
}
