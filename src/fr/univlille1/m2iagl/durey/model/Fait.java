package fr.univlille1.m2iagl.durey.model;

import java.util.Arrays;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class Fait {

	private Relation relation;
	private char[] values;
	
	public Fait(Relation relation, char [] variables, Homomorphism homomorphism){
		this.relation = relation;
		values = new char[variables.length];
		
		fillValuesAccordingToVariablesAndHomomorphism(variables, homomorphism);
	}
	
	
	public Fait(Relation relation, char [] values){
		this.relation = relation;
		this.values = values;
	}
	
	public static Fait createBasicFait(Relation relation){
		char[] variables = new char[relation.getArity()];
		for(int i=0;i<relation.getArity();i++){
			variables[i] = Value.basicVar;
		}
		
		return new Fait(relation, variables);
	}
	
	public RelationName getRelationName(){
		return relation.getRelationName();
	}
	
	
	public void modifyInstanceWithHomomorphism(Homomorphism homomorphism){
		
		for(int i=0;i<values.length;i++){
			if(homomorphism.containsVariable(values[i])){
				values[i] = homomorphism.get(values[i]);
			}
		}
	
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
