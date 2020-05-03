package mtech.irs.ekgs.frameworx.model;

import lombok.Data;

@Data
public class LoadDistributionRequest {

	private String relationship;
	
	private double load;
	
	private String startNode;
	
	private String endNode;
}
