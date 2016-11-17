package fr.univlille1.m2iagl.durey.model;

import java.util.Arrays;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class InstanceRelation {

	private Relation relation;
	private char[] values;
	
	public InstanceRelation(Relation relation, char [] variables, Homomorphism homomorphism){
		this.relation = relation;
		values = new char[variables.length];
		
		fillValuesAccordingToVariablesAndHomomorphism(variables, homomorphism);
	}
	
	
	public InstanceRelation(Relation relation, char [] values){
		this.relation = relation;
		this.values = values;
	}
	
	public RelationName getRelationName(){
		return relation.getRelationName();
	}
	
	
	public void modifyInstanceWithHomomorphismAndConstraint(Homomorphism homomorphism, Constraint constraint){
		char [] newValues = new char[values.length];
		
		for(int i=0;i<constraint.getRightVariables().length;i++){
			char variable = constraint.getRightVariable(i);
			if(homomorphism.containsVariable(variable)){
				char replacement = homomorphism.get(variable);
				for(int j : constraint.getPosOfVariableIntoLeftRelation(variable)){
					newValues[j] = replacement;
				}
			}
		}
		
		for(int i=0;i<newValues.length;i++){
			if(newValues[i] == '\0'){
				newValues[i] = values[i];
			}
		}
		
		values = newValues;
	}
	
	public int getArity(){
		return relation.getArity();
	}
	
	public char get(int i){
		return values[i];
	}
	
	private void fillValuesAccordingToVariablesAndHomomorphism(char [] variables, Homomorphism homomorphism){
		for(int i=0;i<variables.length;i++){
			char variable = variables[i];
		
			if(homomorphism.containsVariable(variable)){
				values[i] = homomorphism.get(variable);
			
			} else {
				values[i] = Value.getNewValue();
				
			}
		}
		
	}
	
	@Override
	public String toString(){
		String string = relation.getRelationName().toString() + "(";
		
		for(int i=0;i<values.length-1;i++){
			string += values[i] + ", ";
		}
		return string += values[values.length-1] + ")";
	}
}
