package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.RelationName;

public class InvisibleToInvisibleConstraint extends ImpliesConstraint {

	public InvisibleToInvisibleConstraint(Map<RelationName, ConstraintElement[]> list, ConstraintElement constraintElement){
		super(list, constraintElement);
	}

	public InvisibleToInvisibleConstraint(RelationName relationName1, char[] relation1,
			RelationName relationName2, char[] relation2) {
		super(relationName1, relation1, relationName2, relation2);
	}

	public RelationName getLeftRelationName(){
		return left.keySet().iterator().next();
	}

	public char[] getLeftVariables(){
		return left.get(getLeftRelationName())[0].getVariables();
	}

	public char getLeftVariable(int i){
		return getLeftVariables()[i];
	}

	public RelationName getRightRelationName(){
		return right.keySet().iterator().next();
	}

	public char[] getRightVariables(){
		return right.get(getRightRelationName())[0].getVariables();
	}

	public char getRightVariable(int i){
		return getRightVariables()[i];
	}

	/**
	 * Return the fact which does not satisfy the constraint with the given homomorphism and instanceSchema
	 * @param instanceSchema
	 * @param homomorphism
	 * @return
	 */
	public Fait satisfyHomomorphism(InstanceSchema instanceSchema, Homomorphism homomorphism){
		List<Fait> faitsLeft = instanceSchema.get(getLeftRelationName());
		List<Fait> faitsRight = instanceSchema.get(getRightRelationName());

		Fait unsatisfiedFait = null;
		for(Fait faitLeft : faitsLeft){
			if(faitLeft.match(getLeftConstraintElementGroup(), homomorphism)){

				unsatisfiedFait = faitLeft;

				boolean faitSatisfied = false;
				for(Fait faitRight : faitsRight){
					
					boolean satisfied = true;
					for(int i=0;i<getRightVariables().length;i++){
						char variable = getRightVariable(i);
						
						if(homomorphism.containsVariable(variable) && faitRight.get(i) != homomorphism.get(variable)){
							satisfied = false;
						}
					}
					
					if(satisfied){
						faitSatisfied = true;
					}
				}
				
				if(!faitSatisfied)
					return faitLeft;
			}
		}
		return null;
	}
}
