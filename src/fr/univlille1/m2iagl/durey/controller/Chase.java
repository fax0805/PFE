package fr.univlille1.m2iagl.durey.controller;

import java.util.List;

import fr.univlille1.m2iagl.durey.model.Association;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.constraint.Constraint;
import fr.univlille1.m2iagl.durey.model.constraint.SatisfyHelper;

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
			if(constraint.satisfy(instanceSchema) != null){
				instanceSchema.add(constraint.createFaitForMatching(instanceSchema));
			}
		}

		System.out.println(instanceSchema);

		System.out.println("---------------------------------------------------");

		boolean added = true;
		while(added){
			
			added = false;
			Association association = findConstraintAndHomomorphismNotSatisfied(invisibleToVisibleConstraints);

			if(association != null){

				Constraint constraint = association.getConstraint();
								
				Homomorphism homomorphism = Homomorphism.createHomomorphism(constraint.getRightVariables(),
						instanceSchema.get(constraint.getRightRelationName()).get(0));
				
				System.out.println("First homomorphism : " + homomorphism);
				System.out.println("Second homomorphism : " + association.getHomomorphism());

				Homomorphism finalHomomorphism = association.getHomomorphism().changeHomomorphism(homomorphism);

				instanceSchema.modifyInstanceWithHomomorphism(finalHomomorphism, association.getConstraint());

				added = true;


			}	

		}

		return instanceSchema;
	}

	public Association findConstraintAndHomomorphismNotSatisfied(List<Constraint> constraints){
		for(Constraint constraint : constraints){
			List<Homomorphism> homomorphisms = Homomorphism.createHomomorphisms(instanceSchema, constraint);
			
			for(Homomorphism homomorphism : homomorphisms){
				if(!constraint.satisfyHomomorphism(instanceSchema, homomorphism)){
					return new Association(homomorphism, constraint);
				}
			}
		}
		return null;

	}


}
