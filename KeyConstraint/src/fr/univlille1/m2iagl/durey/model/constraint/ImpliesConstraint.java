package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.Map;

import fr.univlille1.m2iagl.durey.model.RelationName;

public abstract class ImpliesConstraint extends Constraint{

	public ImpliesConstraint(Map<RelationName, ConstraintElement[]> list, ConstraintElement constraintElement){
		super(list, constraintElement);
	}

	public ImpliesConstraint(RelationName relationName1, char[] relation1,
			RelationName relationName2, char[] relation2) {
		super(relationName1, relation1, relationName2, relation2);
	}

	public ImpliesConstraint(RelationName[] leftRelationNames, char[][][] leftVars, RelationName rightRelationName, char[] rightVars) {
		super(leftRelationNames, leftVars, rightRelationName, rightVars);
	}
	
	public ImpliesConstraint(RelationName leftRelationName, char[] leftVars, RelationName[] rightRelationNames, char[][][] rightVars){
		super(leftRelationName, leftVars, rightRelationNames, rightVars);
	}
	
}
