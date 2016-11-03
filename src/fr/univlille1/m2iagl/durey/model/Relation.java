package fr.univlille1.m2iagl.durey.model;

public class Relation {
	
	private RelationName relationName;
	private int arity;
	
	public Relation(RelationName relationName, int arity){
		this.relationName = relationName;
		this.arity = arity;
	}
	
	public RelationName getRelationName(){
		return relationName;
	}
	
	public int getArity(){
		return arity;
	}

}
