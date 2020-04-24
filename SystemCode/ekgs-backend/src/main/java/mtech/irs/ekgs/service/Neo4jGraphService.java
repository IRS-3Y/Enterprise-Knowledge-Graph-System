package mtech.irs.ekgs.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class Neo4jGraphService implements GraphService{

	@Autowired
	private Session session;
	
	@Transactional
	@Override
	public List<Map<String,Object>> query(String cypher, Map<String, Object> params) {
		List<Map<String,Object>> list = new ArrayList<>();
		Result result = session.query(cypher, params == null? Map.of(): params);
		result.forEach(r -> list.add(r));
		return list;
	}
	
	@Transactional
	@Override
	public Optional<Map<String, Object>> queryUnique(String cypher, Map<String, Object> params) {
		return query(cypher, params).stream().findFirst();
	}
	
	@Transactional
	@Override
	public <T> Stream<T> query(String cypher, Map<String, Object> params, String resultKey, Class<T> resultType){
		return query(cypher, params).stream().map(r -> resultType.cast(r.get(resultKey)));
	}
	
	@Transactional
	@Override
	public <T> List<T> queryList(String cypher, Map<String, Object> params, String resultKey, Class<T> resultType) {
		return query(cypher, params, resultKey, resultType).collect(Collectors.toList());
	}
	
	@Transactional
	@Override
	public <T> List<T> query(String cypher, Map<String, Object> params, Class<T> resultType) {
		List<T> list = new ArrayList<>();
		Iterable<T> itr = session.query(resultType, cypher, params == null? Map.of(): params);
		itr.forEach(r -> list.add(r));
		return list;
	}

	@Transactional
	@Override
	public <T> Optional<T> queryUnique(String cypher, Map<String, Object> params, Class<T> resultType) {
		return query(cypher, params, resultType).stream().findFirst();
	}
}
