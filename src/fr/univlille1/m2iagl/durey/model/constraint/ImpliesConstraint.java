package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.RelationName;

public class ImpliesConstraint extends Constraint{

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


	public boolean satisfyHomomorphism(InstanceSchema instanceSchema, Homomorphism homomorphism){
		List<Fait> faits = instanceSchema.get(getRightRelationName());

		for(Fait fait : faits){

			boolean satisfied = true;

			System.out.println("Fait : " + fait);
			System.out.println("Homomorphism : " + homomorphism);
			for(int i=0;i<getRightVariables().length;i++){
				char variable = getRightVariable(i);

				System.out.println("Variable : " + variable);

				if(homomorphism.containsVariable(variable) && fait.get(i) != homomorphism.get(variable)){
					satisfied = false;
				}
			}

			if(satisfied)
				return true;
		}

		return false;
	}

	/**
	 * Return the homomorphism witch does NOT satisfy the instance schema and the constraint, or null if it satisfy
	 */

	@Override
	public Homomorphism satisfy(InstanceSchema instanceSchema) {

		List<Homomorphism> homomorphisms = Homomorphism.createHomomorphisms(instanceSchema, this);

		for(Homomorphism homomorphism : homomorphisms){
			if(!satisfyHomomorphism(instanceSchema, homomorphism))
				return homomorphism;
		}

		return null;
	}


	@Override
	public Fait createFaitForMatching(InstanceSchema instanceSchema) {
		for(RelationName relationName : getLeftRelationNames()){
			for(int pos=0;pos<getSizeOf(relationName);pos++){
				for(Fait instanceRelation : instanceSchema.get(relationName)){
					Homomorphism homomorphism = null;
					if((homomorphism = Homomorphism.createHomomorphism(getVariablesOf(relationName, pos), instanceRelation))!= null
							&& !SatisfyHelper.matchInstanceSchema(getRightRelationName(), instanceSchema, homomorphism, this)){
						return new Fait(instanceSchema.getSchema().getRelation(getRightRelationName()), getRightVariables(), homomorphism);
					}
				}
			}
		}
		return null;
	}

	@Override
	public Fait getUnsatisfiedInstanceRelation(InstanceSchema instanceSchema) {
		for(RelationName relationName : getLeftRelationNames()){
			for(int pos=0;pos<getSizeOf(relationName);pos++){

				for(Fait instanceRelation : instanceSchema.get(relationName)){
					Homomorphism homomorphism = null;
					if((homomorphism = Homomorphism.createHomomorphism(getVariablesOf(relationName, pos), instanceRelation))!= null){
						Fait instanceRelationNonMatching = null;
						if((instanceRelationNonMatching = SatisfyHelper.getInstanceRelationNonMatching(getRightRelationName(), instanceSchema, homomorphism, this)) != null){
							return instanceRelationNonMatching;
						}
					}
				}
			}
		}

		return null;
	}
}
