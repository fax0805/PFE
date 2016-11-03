package fr.univlille1.m2iagl.durey.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InstanceSchema {

	
	
	private Schema schema;
	
	Map<RelationName, List<InstanceRelation>> values;
	
	public InstanceSchema(Schema schema){
		this.schema = schema;
		values = new HashMap<>();
		
		fillKeys();
	}
	
	public InstanceSchema(InstanceSchema instanceSchema){
		this.schema = instanceSchema.schema;
		this.values = instanceSchema.values;
	}
	
	private void fillKeys(){
		for(RelationName relationName : schema.visibleKeySet()){
			values.put(relationName, new ArrayList<InstanceRelation>());
		}
		
		for(RelationName relationName : schema.invisibleKeySet()){
			values.put(relationName, new ArrayList<InstanceRelation>());			
		}
	}
	
	public void add(InstanceRelation instanceRelation){
		values.get(instanceRelation.getRelationName()).add(instanceRelation);
	}
	
	public Set<RelationName> keySet(){
		return values.keySet();
	}
	
	public List<InstanceRelation> get(RelationName relationName){
		return values.get(relationName);
	}
}
