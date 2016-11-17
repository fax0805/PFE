package fr.univlille1.m2iagl.durey.controller;

import java.util.List;

import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceRelation;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class Chase {
	
	private InstanceSchema instanceSchema;
	private List<Constraint> visibleToInvisibleConstraints;
	private List<Constraint> invisibleToVisibleConstraints;
	
	public Chase(InstanceSchema instanceSchema, List<Constraint> visibleToInvisibleConstraints, List<Constraint> invisibleToVisibleConstraints){
		this.instanceSchema = instanceSchema;
		this.visibleToInvisibleConstraints = visibleToInvisibleConstraints;
		this.invisibleToVisibleConstraints = invisibleToVisibleConstraints;
		
	}
	
	public InstanceSchema run(){
		InstanceSchema instanceSchema = new InstanceSchema(this.instanceSchema);
		

		for(Constraint constraint : visibleToInvisibleConstraints){
			if(!constraint.isSatisfy(instanceSchema)){
				instanceSchema.add(constraint.createInstanceRelationForMatching(instanceSchema));
			}
		}

		boolean added = true;
		
		while(added){
			added = false;
			Constraint invisibleToVisibleConstraint = findConstraintNotSatisfied(invisibleToVisibleConstraints);
			
			if(invisibleToVisibleConstraint != null){
				InstanceRelation instanceRelation = invisibleToVisibleConstraint.getUnsatisfiedInstanceRelation(instanceSchema);
				Homomorphism homomorphism = Homomorphism.createHomomorphism(invisibleToVisibleConstraint.getRightVariables(), instanceRelation);
				
				instanceSchema.modifyInstanceWithHomomorphism(homomorphism, invisibleToVisibleConstraint);
				added = true;
			}
			
		}
		
		return instanceSchema;
	}
	
	public Constraint findConstraintNotSatisfied(List<Constraint> constraints){
		for(Constraint constraint : constraints){
			if(!constraint.isSatisfy(instanceSchema)){
				return constraint;
			}
		}
		return null;
			
	}
	

}
