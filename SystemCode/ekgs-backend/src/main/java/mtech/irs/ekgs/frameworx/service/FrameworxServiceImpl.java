package mtech.irs.ekgs.frameworx.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mtech.irs.ekgs.frameworx.model.FrameworxNode;
import mtech.irs.ekgs.frameworx.model.ResourceLoadCostInfo;
import mtech.irs.ekgs.frameworx.model.Technology;
import mtech.irs.ekgs.frameworx.repository.FrameworxRepository;
import mtech.irs.ekgs.frameworx.repository.TechnologyRepository;
import mtech.irs.ekgs.kie.KieSystem;
import mtech.irs.ekgs.service.GraphService;

@Transactional(readOnly = true)
@Service
public class FrameworxServiceImpl implements FrameworxService{
	
	@Autowired
	private KieSystem kie;
	
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
	public double findCurrentLoad(String relation) {
		final String cypher = 
				"MATCH (n)-[:" + relation + "]-() " + 
				"RETURN max(n.load) as load";
		return graphService.query(cypher, null, "load", Double.class).findFirst().orElse(0d);
	}
	
	@Transactional
	@Override
	public void distributeLoad(String relation, double load, String startNode, String endNode) {
		resetLoadAll(relation);
		long startId = findNodeId(startNode);
		long endId = findNodeId(endNode);
		setLoad(startId, load);
		List<Long> children = updateDownstreamLoad(relation, startId);
		Set<Long> space = new HashSet<>(children);
		while(!space.isEmpty()) {
			long curId = space.stream().findFirst().orElseThrow();
			space.remove(curId);
			if(curId == endId) {
				continue;
			}else if(!isLoadReady(relation, curId)) {
				continue;
			}
			children = updateDownstreamLoad(relation, curId);
			space.addAll(children);
		}
	}
	
	protected boolean isLoadReady(String relation, long nodeId) {
		final String cypher = 
				"MATCH (n)<-[r:" + relation + "]-(m) " + 
				"WHERE ID(n) = $nodeId AND r.relationLoad = 0 " + 
				"RETURN count(m) = 0 as loaded";
		return graphService.query(cypher, Map.of("nodeId", nodeId), "loaded", Boolean.class).findFirst().orElse(false);
	}
	
	protected List<Long> updateDownstreamLoad(String relation, long nodeId) {
		final String cypher = 
				"MATCH (n)-[r:" + relation + "]->(m) " + 
				"WHERE ID(n) = $nodeId " +
				"WITH n, sum(m.loadDistribution) as sumLD " + 
				"MATCH (n)-[r:" + relation + "]->(m) " + 
				"SET r.relationLoad = n.load * m.loadDistribution / sumLD " + 
				"SET m.load = m.load + r.relationLoad " + 
				"RETURN ID(m) as id";
		return graphService.query(cypher, Map.of("nodeId", nodeId), "id", Long.class).collect(Collectors.toList());
	}
	
	protected void setLoad(long nodeId, double load) {
		final String cypher = "MATCH (n) WHERE ID(n) = $nodeId SET n.load = $load";
		graphService.query(cypher, Map.of("nodeId", nodeId, "load", load));
	}
	
	protected void resetLoadAll(String relation) {
		final String cypher = "MATCH (n)-[r:" + relation + "]-() SET n.load = 0.0, r.relationLoad = 0.0";
		graphService.query(cypher, null);
	}
	
	protected long findNodeId(String nodePattern) {
		final String cypher = "MATCH (n" + nodePattern + ") RETURN ID(n) as id";
		return graphService.query(cypher, null, "id", Long.class).findFirst().orElseThrow();
	}

	@Transactional
	@Override
	public void computeCost(String relation) {
		findResourceLoadInfo(relation).forEach(info -> {
			//use rules to compute cost
			kie.newStatelessSession().execute(info);
			updateCostAndTime(relation, info.getId(), info.getCost(), info.getTime());
		});
	}
	
	protected Stream<ResourceLoadCostInfo> findResourceLoadInfo(String relation){
		final String cypher = 
				"MATCH (n)<-[:" + relation + "]-() " + 
				"WITH distinct n, labels(n)[0] as l " + 
				"RETURN ID(n) as id, n.longName as name, l as label, n.resource as resource, n.loadDistribution as loadWeight, n.load as load";
		return graphService.query(cypher, null).stream().map(ResourceLoadCostInfo::of);
	}
	
	protected void updateCostAndTime(String relation, long nodeId, double cost, double time) {
		final String cypher = 
				"MATCH (n)<-[r:" + relation + "]-() " + 
				"WHERE ID(n) = $nodeId " + 
				"SET n.cost = $cost , n.time = $time " +
				"SET r.relationCost = $cost , r.relationTime = $time ";
		graphService.query(cypher, Map.of("nodeId", nodeId, "cost", cost, "time", time));
	}

	@Override
	public List<ResourceLoadCostInfo> findResourceLoad(String relation) {
		return findResourceLoadInfo(relation).collect(Collectors.toList());
	}
	
	@Transactional
	@Override
	public void updateResourceLoad(List<ResourceLoadCostInfo> records) {
		records.forEach(info -> {
			final String cypher = 
					"MATCH (n) " + 
					"WHERE ID(n) = $nodeId " + 
					"SET n.resource = $resource , n.loadDistribution = $loadWeight";
			graphService.query(cypher, Map.of("nodeId", info.getId(), "resource", info.getResource(), "loadWeight", info.getLoadWeight()));
		});
	}
}
