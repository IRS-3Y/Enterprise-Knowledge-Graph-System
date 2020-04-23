package mtech.irs.ekgs.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mtech.irs.ekgs.model.GraphQuery;
import mtech.irs.ekgs.service.GraphService;

@RestController
@RequestMapping("/graph")
public class GraphController {
	
	@Autowired
	private GraphService service;

	@PostMapping("/query")
	public List<Map<String, Object>> query(@RequestBody GraphQuery query){
		return service.query(query.getCypher(), query.getParams());
	}
}
