package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.durey.model.RelationName;

public class ConstraintElementGroup {
	Map<RelationName, ConstraintElement []> constraintElements;
	
	public ConstraintElementGroup(Map<RelationName, ConstraintElement []> constraintElements){
		this.constraintElements = constraintElements;
	}
	
	public void put(RelationName relationName, ConstraintElement[] constraintElements){
		this.constraintElements.put(relationName, constraintElements);
	}
	
	public Set<RelationName> keySet(){
		return constraintElements.keySet();
	}
	
	public ConstraintElement[] get(RelationName relationName){
		return constraintElements.get(relationName);
	}
	
	public int getSizeOf(RelationName relationName){
		return constraintElements.get(relationName).length;
	}
	
	public char[] getVariablesOf(RelationName relationName, int pos){
		return getAt(relationName, pos).getVariables();
	}
	
	public ConstraintElement getAt(RelationName relationName, int i){
		return get(relationName)[i];
	}
	
	public int size(){
		return keySet().size();
	}
	
	@Override
	public String toString(){
		String s = "{";
		List<RelationName> keyList = new ArrayList<RelationName>(constraintElements.keySet());
		for(int i =0;i<keyList.size() - 1;i++){
			RelationName relationName = keyList.get(i);
			s += constraintElementArrayToString(constraintElements.get(relationName)) + ", ";
		}
		
		return s + constraintElementArrayToString(constraintElements.get(keyList.get(keyList.size() - 1))) +  "}";
	}
	
	public String constraintElementArrayToString(ConstraintElement[] constraintElementsArray){
		String s = "";
		
		
		for(int j=0;j<constraintElementsArray.length - 1;j++){
			s += constraintElementsArray[j].toString() + ", ";
		}
		
		s += constraintElementsArray[constraintElementsArray.length - 1].toString();
		
		return s;
		
		
		
	}
}
