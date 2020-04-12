package mtech.irs.ekgs.frameworx.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mtech.irs.ekgs.frameworx.model.FrameworxNode;
import mtech.irs.ekgs.frameworx.model.Technology;
import mtech.irs.ekgs.frameworx.service.FrameworxService;

@RestController
@RequestMapping("/frameworx")
public class FrameworxController {

	@Autowired
	private FrameworxService frameworxService;
	
	@GetMapping("/technology")
	public Iterable<Technology> getTechnologies(){
		return frameworxService.findTechnologyAll();
	}
	
	@GetMapping("/label/{label}")
	public List<FrameworxNode> getNodes(@PathVariable String label){
		return frameworxService.findNodes(label);
	}
}
