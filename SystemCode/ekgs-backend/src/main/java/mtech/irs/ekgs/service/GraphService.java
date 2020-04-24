package mtech.irs.ekgs.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Define generic graph operations
 * 
 * @author tanshyi
 */
public interface GraphService {

	List<Map<String,Object>> query(String cypher, Map<String, Object> params);
	
	Optional<Map<String,Object>> queryUnique(String cypher, Map<String, Object> params);
	
	<T> Stream<T> query(String cypher, Map<String, Object> params, String resultKey, Class<T> resultType);
	
	<T> List<T> queryList(String cypher, Map<String, Object> params, String resultKey, Class<T> resultType);
	
	<T> List<T> query(String cypher, Map<String, Object> params, Class<T> resultType);
	
	<T> Optional<T> queryUnique(String cypher, Map<String, Object> params, Class<T> resultType);
}
