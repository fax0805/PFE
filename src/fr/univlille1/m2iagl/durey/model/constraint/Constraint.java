package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.univlille1.m2iagl.durey.model.InstanceRelation;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.RelationName;

public abstract class Constraint {
	
	private RelationName relationName1;
	private char[] relation1;
	
	private RelationName relationName2;
	private char[] relation2;
	
	public Constraint(RelationName relationName1, char[] relation1, RelationName relationName2, char[] relation2){
		this.relationName1 = relationName1;
		this.relation1 = relation1;
		this.relationName2 = relationName2;
		this.relation2 = relation2;
	}
	
	public List<Integer> getPosOfVariableIntoLeftRelation(char variable){
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<relation1.length;i++){
			if(relation1[i] == variable){
				list.add(i);
			}
		}
		return list;
	}
	
	
	public RelationName getLeftRelationName(){
		return relationName1;
	}
	
	public char[] getLeftVariables(){
		return relation1;
	}
	
	public char getLeftVariable(int i){
		return relation1[i];
	}
	
	public RelationName getRightRelationName(){
		return relationName2;
	}
	
	public char[] getRightVariables(){
		return relation2;
	}
	
	public char getRightVariable(int i){
		return relation2[i];
	}
	
	public abstract InstanceRelation getUnsatisfiedInstanceRelation(InstanceSchema instanceSchema);
	
	public abstract boolean isSatisfy(InstanceSchema instanceSchema);
	
	public abstract InstanceRelation createInstanceRelationForMatching(InstanceSchema instanceSchema);
	
	public String toString(){
		return relationName1 + "(" + Arrays.toString(relation1) + ") -> " + relationName2 + "(" + Arrays.toString(relation2) + ")"; 
	}
	

}
