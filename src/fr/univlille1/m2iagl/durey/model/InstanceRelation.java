package fr.univlille1.m2iagl.durey.model;

public class InstanceRelation {

	private Relation relation;
	private char[] values;
	
	public InstanceRelation(Relation relation, Homomorphism homomorphism, char[] variables){
		
	}
	
	
	public InstanceRelation(Relation relation, char [] values){
		this.relation = relation;
		this.values = values;
	}
	
	public RelationName getRelationName(){
		return relation.getRelationName();
	}
	
	public int getArity(){
		return relation.getArity();
	}
	
	public char get(int i){
		return values[i];
	}
}
