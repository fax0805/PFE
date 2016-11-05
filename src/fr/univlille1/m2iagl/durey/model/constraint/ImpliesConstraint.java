package fr.univlille1.m2iagl.durey.model.constraint;

import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceRelation;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.RelationName;

public class ImpliesConstraint extends Constraint{
	

	public ImpliesConstraint(RelationName relationName1, char[] relation1,
			RelationName relationName2, char[] relation2) {
		super(relationName1, relation1, relationName2, relation2);
	}

	@Override
	public boolean isSatisfy(InstanceSchema instanceSchema) {
		for(InstanceRelation instanceRelation : instanceSchema.get(getLeftRelationName())){
			Homomorphism homomorphism = null;
			if((homomorphism = Homomorphism.createHomomorphism(getLeftVariables(), instanceRelation))!= null){
				if(!SatisfyHelper.matchInstanceSchema(getRightRelationName(), instanceSchema, homomorphism, this))
					return false;
			}
		}
		
		return true;
	}

	@Override
	public InstanceRelation createInstanceRelationForMatching(InstanceSchema instanceSchema) {
		for(InstanceRelation instanceRelation : instanceSchema.get(getLeftRelationName())){
			Homomorphism homomorphism = null;
			if((homomorphism = Homomorphism.createHomomorphism(getLeftVariables(), instanceRelation))!= null
					&& !SatisfyHelper.matchInstanceSchema(getRightRelationName(), instanceSchema, homomorphism, this)){
				return new InstanceRelation(instanceSchema.getSchema().getRelation(getRightRelationName()), getRightVariables(), homomorphism);
					
			}
		}
		
		return null;
	}

	@Override
	public InstanceRelation getUnsatisfiedInstanceRelation(InstanceSchema instanceSchema) {
		for(InstanceRelation instanceRelation : instanceSchema.get(getLeftRelationName())){
			Homomorphism homomorphism = null;
			if((homomorphism = Homomorphism.createHomomorphism(getLeftVariables(), instanceRelation))!= null){
				if(!SatisfyHelper.matchInstanceSchema(getRightRelationName(), instanceSchema, homomorphism, this)){
					// return instanceSchema.getSchema().getRelation(getRightRelationName());
				}
			}
		}
		
		return null;
	}
	
	

}
