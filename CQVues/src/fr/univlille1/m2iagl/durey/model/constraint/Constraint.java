package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.durey.controller.Factory;
import fr.univlille1.m2iagl.durey.model.RelationName;

public abstract class Constraint {

	protected ConstraintElementGroup left;
	protected ConstraintElementGroup right;


	public Constraint(RelationName[] leftRelationNames, char[][][] leftVars, RelationName rightRelationName, char[] rightVars){
		left = new ConstraintElementGroup(Factory.createConstraintElements(leftRelationNames, leftVars));
		right = new ConstraintElementGroup(new HashMap<RelationName, ConstraintElement[]>());
		right.put(rightRelationName, new ConstraintElement[]{Factory.createConstraintElement(rightRelationName, rightVars)});
	}
	
	public Constraint(RelationName leftRelationName, char[] leftVars, RelationName[] rightRelationNames, char[][][] rightVars){
		left = new ConstraintElementGroup(new HashMap<RelationName, ConstraintElement[]>());
		left.put(leftRelationName, new ConstraintElement[]{Factory.createConstraintElement(leftRelationName, leftVars)});
		right = new ConstraintElementGroup(Factory.createConstraintElements(rightRelationNames, rightVars));

	}

	public Constraint(Map<RelationName, ConstraintElement[]> left, ConstraintElement right){
		this.left = new ConstraintElementGroup(left);
		this.right = new ConstraintElementGroup(new HashMap<RelationName, ConstraintElement[]>());
		this.right.put(right.getRelationName(), new ConstraintElement[]{right});
	}


	public Constraint(RelationName leftRelationName, char[] leftRelation, RelationName rightRelationName, char[] rightRelation){
		this.left = new ConstraintElementGroup(new HashMap<RelationName, ConstraintElement[]>());
		this.left.put(leftRelationName, new ConstraintElement[]{Factory.createConstraintElement(leftRelationName, leftRelation)});

		this.right = new ConstraintElementGroup(new HashMap<RelationName, ConstraintElement[]>());
		this.right.put(rightRelationName, new ConstraintElement[]{Factory.createConstraintElement(rightRelationName, rightRelation)});
	}
	
	
	public ConstraintElementGroup getLeftConstraintElementGroup(){
		return left;
	}
	
	public ConstraintElementGroup getRightConstraintElementGroup(){
		return right;
	}

	public String toString(){
		return left.toString() + " -> " + right.toString();
	}


}
