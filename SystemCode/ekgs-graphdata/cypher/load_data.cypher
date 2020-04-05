//load nodes
CALL apoc.load.csv('ekgs_nodes.csv') YIELD map 
CALL apoc.create.node([map.label1, map.label2], {
  nodeID: toInteger(map.nodeID),
  name: map.name,
  longName: map.longName,
  leaf: map.leaf,
  stem: map.stem,
  domain: map.domain,
  category: map.category,
  shortDescription: map.shortDescription,
  longDescription: map.longDescription,
  cost: toFloat(map.cost),
  time: toFloat(map.time),
  quality: map.quality,
  resource: toFloat(map.resource)
}) YIELD node 
RETURN count(node);

//load relationships
CALL apoc.load.csv('ekgs_relationships.csv') YIELD map 
MATCH (n {nodeID:toInteger(map._start)}), (p {nodeID:toInteger(map._end)})
CALL apoc.create.relationship(n, map.type, {
  relationCost: toFloat(map.relationCost),
  relationTime: toFloat(map.relationTime),
  relationQuality: map.relationQuality,
  processStream: map.processStream
}, p) YIELD rel AS relationship
RETURN count(relationship);
