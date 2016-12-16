package fr.univlille1.m2iagl.durey.model.constraint;

import fr.univlille1.m2iagl.durey.model.RelationName;

public class ConstraintElement {

	
	private RelationName relationName;
	private char[] variables;
	
	public ConstraintElement(RelationName relationName, char[] variables){
		this.relationName = relationName;
		this.variables = variables;
	}
	
	public RelationName getRelationName(){
		return relationName;
	}
	
	public char[] getVariables(){
		return variables;
	}
	
	@Override
	public String toString(){
		String string = relationName.getName() + "(";
		
		for(int i=0;i<variables.length-1;i++){
			string += variables[i] + ",";
		}
		
		return string + variables[variables.length-1] + ")";
		
		
	}
}
