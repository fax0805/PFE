package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.Map;

import fr.univlille1.m2iagl.durey.model.RelationName;

public class VisibleToInvisibleConstraint extends ImpliesConstraint {
	
	public VisibleToInvisibleConstraint(Map<RelationName, ConstraintElement[]> list, ConstraintElement constraintElement){
		super(list, constraintElement);
	}

	public VisibleToInvisibleConstraint(RelationName leftRelationName, char[] leftVars, RelationName[] rightRelationNames, char[][][] rightVars){
		super(leftRelationName, leftVars, rightRelationNames, rightVars);
	}

	public VisibleToInvisibleConstraint(RelationName[] leftRelationNames, char[][][] leftVars, RelationName rightRelationName, char[] rightVars) {
		super(leftRelationNames, leftVars, rightRelationName, rightVars);
	}
		
	public RelationName getRightRelationName(){
		return right.keySet().iterator().next();
	}
	
	public char[] getRightVariables(){
		return right.getAt(getRightRelationName(), 0).getVariables();
	}
}
