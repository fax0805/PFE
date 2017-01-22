package fr.univlille1.m2iagl.durey.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author Antonin
 *
 * Classe représentant un Schéma
 */
public class Schema {
	
	Map<RelationName, Relation> visibleRelations;
	Map<RelationName, Relation> invisibleRelations;
	
	public Schema(){
		visibleRelations = new HashMap<RelationName, Relation>();
		invisibleRelations = new HashMap<RelationName, Relation>();
	}

	
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
	
	public Relation getRelation(RelationName relationName){
		Relation relation = visibleRelations.get(relationName);
		return relation != null ? relation : invisibleRelations.get(relationName);
	}
	
	public void addVisibleRelation(RelationName relationName, Relation relation){
		visibleRelations.put(relationName, relation);
	}
	
	public void addInvisibleRelation(RelationName relationName, Relation relation){
		invisibleRelations.put(relationName, relation);
	}
	
	@Override
	public String toString(){
		String s = "Visible {";
		List<RelationName> visibleKeys = new ArrayList<RelationName>(visibleRelations.keySet());
		for(int i=0;i<visibleKeys.size()-1;i++){
			RelationName relationName = visibleKeys.get(i);
			s += relationName + " : " + visibleRelations.get(relationName).getArity() + ", ";
		}
		if(visibleKeys.size() > 0)
			s += visibleKeys.get(visibleKeys.size()-1) + " : " + visibleRelations.get(visibleKeys.get(visibleKeys.size()-1)).getArity() + "}";
		
		s += "}, Invisible {";
		List<RelationName> invisibleKeys = new ArrayList<RelationName>(invisibleRelations.keySet());
		for(int i=0;i<invisibleKeys.size()-1;i++){
			RelationName relationName = invisibleKeys.get(i);
			s += relationName + " : " + invisibleRelations.get(relationName).getArity() + ", ";
		}
		
		if(invisibleKeys.size() > 0)
			s += invisibleKeys.get(invisibleKeys.size()-1) + " : " + invisibleRelations.get(invisibleKeys.get(invisibleKeys.size()-1)).getArity() + "}";
		
		
		
		return s;

	}
}
