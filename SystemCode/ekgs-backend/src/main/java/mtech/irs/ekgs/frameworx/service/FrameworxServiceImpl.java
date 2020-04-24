package mtech.irs.ekgs.frameworx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mtech.irs.ekgs.frameworx.model.FrameworxNode;
import mtech.irs.ekgs.frameworx.model.Technology;
import mtech.irs.ekgs.frameworx.repository.FrameworxRepository;
import mtech.irs.ekgs.frameworx.repository.TechnologyRepository;
import mtech.irs.ekgs.service.GraphService;

@Transactional(readOnly = true)
@Service
public class FrameworxServiceImpl implements FrameworxService{
	
	@Autowired
	private GraphService graphService;
	
	@Autowired
	private FrameworxRepository frameworxRepository;

	@Autowired
	private TechnologyRepository technologyRepository;

	@Override
	public Iterable<Technology> findTechnologyAll() {
		return technologyRepository.findAll();
	}
	
	@Override
	public List<FrameworxNode> findNodes(String label) {
		return frameworxRepository.findNodeByLabel(label);
	}

	@Override
	public List<String> findNodeNames(String label) {
		/*
		return findNodes(label).stream()
				.map(FrameworxNode::getLongName)
				.collect(Collectors.toList());
		*/
		String cypher = "MATCH (n:" + label + ") RETURN n.longName as name";
		return graphService.queryList(cypher, null, "name", String.class);
	}

}
