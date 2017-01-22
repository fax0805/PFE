package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.RelationName;

public class InvisibleToVisibleConstraint extends ImpliesConstraint {

	public InvisibleToVisibleConstraint(Map<RelationName, ConstraintElement[]> list, ConstraintElement constraintElement){
		super(list, constraintElement);
	}

	public InvisibleToVisibleConstraint(RelationName relationName1, char[] relation1,
			RelationName relationName2, char[] relation2) {
		super(relationName1, relation1, relationName2, relation2);
	}

	public InvisibleToVisibleConstraint(RelationName[] leftRelationNames, char[][][] leftVars, RelationName rightRelationName, char[] rightVars) {
		super(leftRelationNames, leftVars, rightRelationName, rightVars);
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
	
	public Fait satisfyHomomorphism(InstanceSchema instanceSchema, Homomorphism homomorphism){
		List<Fait> faits = instanceSchema.get(getRightRelationName());

		Fait unsatisfiedFait = null;
		for(Fait fait : faits){

			for(int i=0;i<getRightVariables().length;i++){
				char variable = getRightVariable(i);

				if(homomorphism.containsVariable(variable) && fait.get(i) != homomorphism.get(variable)){
					unsatisfiedFait = fait;
				}
			}

			if(unsatisfiedFait != null)
				return unsatisfiedFait;
		}
		return null;
	}
}
