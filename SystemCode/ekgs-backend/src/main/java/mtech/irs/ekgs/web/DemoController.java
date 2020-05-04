package mtech.irs.ekgs.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mtech.irs.ekgs.frameworx.model.LoadDistributionRequest;
import mtech.irs.ekgs.frameworx.model.ResourceLoadCostInfo;
import mtech.irs.ekgs.frameworx.service.FrameworxService;

@RestController
@RequestMapping("/demo")
public class DemoController {
	
	@Autowired
	private FrameworxService frameworx;

	@GetMapping("/echo/{msg}")
	public String echo(@PathVariable String msg) {
		return msg;
	}
	
	@GetMapping("/resourceload/{relation}")
	public List<ResourceLoadCostInfo> getResourceLoad(@PathVariable String relation){
		return frameworx.findResourceLoad(relation);
	}
	
	@PostMapping("/resourceload")
	public Map<String,Object> postResourceLoad(@RequestBody List<ResourceLoadCostInfo> records){
		frameworx.updateResourceLoad(records);
		return Map.of("success", true);
	}
	
	@GetMapping("/currentload/{relation}")
	public Map<String,Object> currentLoad(@PathVariable String relation){
		return Map.of("load", frameworx.findCurrentLoad(relation));
	}
	
	@PostMapping("/distload")
	public Map<String,Object> distributeLoad(@RequestBody LoadDistributionRequest request){
		frameworx.distributeLoad(
				request.getRelationship(), 
				request.getLoad(),
				request.getStartNode(), 
				request.getEndNode());
		frameworx.computeCost(request.getRelationship());
		return Map.of("success", true);
	}
}
