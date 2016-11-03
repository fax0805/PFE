package fr.univlille1.m2iagl.durey.model;

import java.util.Map;
import java.util.Set;

public class Schema {
	
	Map<RelationName, Relation> visibleRelations;
	Map<RelationName, Relation> invisibleRelations;

	
	public Schema(Map<RelationName, Relation> visibleRelations, Map<RelationName, Relation> invisibleRelations){
		this.visibleRelations = visibleRelations;
		this.invisibleRelations = invisibleRelations;
	}
	
	public Set<RelationName> visibleKeySet(){
		return visibleRelations.keySet();
	}
	
	public Relation getVisibleRelation(RelationName relationName){
		return visibleRelations.get(relationName);
	}
	
	public Set<RelationName> invisibleKeySet(){
		return invisibleRelations.keySet();
	}
	
	public Relation getInvisibleRelation(RelationName relationName){
		return invisibleRelations.get(relationName);
	}
}
