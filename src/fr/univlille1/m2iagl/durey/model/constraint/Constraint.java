package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.durey.controller.Factory;
import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.RelationName;

public abstract class Constraint {

	private Map<RelationName, ConstraintElement []> left;
	private ConstraintElement right;


	public Constraint(RelationName[] leftRelationNames, char[][][] leftVars, RelationName rightRelationName, char[] rightVars){
		left = Factory.createConstraintElements(leftRelationNames, leftVars);
		right = Factory.createConstraintElement(rightRelationName, rightVars);
	}

	public Constraint(Map<RelationName, ConstraintElement[]> left, ConstraintElement right){
		this.left = left;
		this.right = right;
	}


	public Constraint(RelationName relationName1, char[] relation1, RelationName relationName2, char[] relation2){
		ConstraintElement constraintElementLeft = new ConstraintElement(relationName1, relation1);
		ConstraintElement constraintElementRight = new ConstraintElement(relationName2, relation2);

		Map<RelationName, ConstraintElement[]> map = new HashMap<RelationName, ConstraintElement[]>();
		map.put(relationName1, new ConstraintElement[]{constraintElementLeft});

		this.left = map;
		this.right = constraintElementRight;
	}

	public List<Integer> getPosOfVariableIntoLeftRelation(int constraintInd, int pos, char variable){
		List<Integer> list = new ArrayList<Integer>();

		ConstraintElement element = left.get(constraintInd)[pos];
		for(int i=0;i<element.getVariables().length;i++){
			if(element.getVariables()[i] == variable){
				list.add(i);
			}
		}
		return list;
	}


	public Set<RelationName> getLeftRelationNames(){
		return left.keySet();
	}
	
	public int getSizeOf(RelationName relationName){
		return left.get(relationName).length;
	}

	public char[] getVariablesOf(RelationName relationName, int pos){
		return left.get(relationName)[pos].getVariables();
	}

	public char getLeftVariable(RelationName relationName, int pos, int j){
		return getVariablesOf(relationName, pos)[j];
	}

	public RelationName getRightRelationName(){
		return right.getRelationName();
	}

	public char[] getRightVariables(){
		return right.getVariables();
	}

	public char getRightVariable(int i){
		return getRightVariables()[i];
	}

	public abstract Fait getUnsatisfiedInstanceRelation(InstanceSchema instanceSchema);

	public abstract Homomorphism satisfy(InstanceSchema instanceSchema);

	public abstract boolean satisfyHomomorphism(InstanceSchema instanceSchema, Homomorphism homomorphism);

	public abstract Fait createFaitForMatching(InstanceSchema instanceSchema);

	public String toString(){

		String string = "";
		int i=0, j=0;
		for(RelationName relationName : left.keySet()){
			for(ConstraintElement constraintElement : left.get(relationName)){

				if(i != left.size() - 1 || j != left.get(relationName).length - 1)
					string += constraintElement.toString() + ", ";
				else
					string += constraintElement.toString();
				j++;
			}
			i++;

		}
		return string + " -> " + right.toString();
	}


}
