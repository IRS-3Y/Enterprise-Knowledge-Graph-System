package mtech.irs.ekgs.frameworx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mtech.irs.ekgs.frameworx.model.FrameworxNode;
import mtech.irs.ekgs.frameworx.model.Technology;
import mtech.irs.ekgs.frameworx.repository.FrameworxRepository;
import mtech.irs.ekgs.frameworx.repository.TechnologyRepository;

@Transactional(readOnly = true)
@Service
public class FrameworxServiceImpl implements FrameworxService{
	
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

}
