package mtech.irs.ekgs.frameworx.service;

import java.util.List;

import mtech.irs.ekgs.frameworx.model.FrameworxNode;
import mtech.irs.ekgs.frameworx.model.ResourceLoadCostInfo;
import mtech.irs.ekgs.frameworx.model.Technology;

public interface FrameworxService {

	Iterable<Technology> findTechnologyAll();
	
	List<FrameworxNode> findNodes(String label);
	
	double findCurrentLoad(String relation);
	
	void distributeLoad(String relation, double load, String startNode, String endNode);
	
	void computeCost(String relation);
	
	List<ResourceLoadCostInfo> findResourceLoad(String relation);
	
	void updateResourceLoad(List<ResourceLoadCostInfo> records);
	
}
