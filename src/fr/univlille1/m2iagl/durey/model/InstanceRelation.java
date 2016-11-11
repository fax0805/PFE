package fr.univlille1.m2iagl.durey.model;


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
	
	
	public void modifyInstanceWithHomomorphism(Homomorphism homomorphism){
		char [] newValues = new char[values.length];
		
		for(int i=0;i<values.length;i++){
			char c = values[i];
			if(homomorphism.containsVariable(c)){
				char replacement = homomorphism.get(c);
				newValues[i] = replacement;
			} else {
				newValues[i] = c;
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
