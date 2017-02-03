package fr.univlille1.m2iagl.durey.controller;

import java.util.List;

import fr.univlille1.m2iagl.durey.model.Association;
import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToVisibleConstraint;
import fr.univlille1.m2iagl.durey.model.constraint.VisibleToInvisibleConstraint;

/**
 * 
 * @author Antonin
 *
 * Class representing the chase algorithmm
 * 
 */
public class Chase {

	private InstanceSchema instanceSchema;
	private List<VisibleToInvisibleConstraint> visibleToInvisibleConstraints;
	private List<InvisibleToVisibleConstraint> invisibleToVisibleConstraints;
	
	public Chase(InstanceSchema instanceSchema, List<VisibleToInvisibleConstraint> visibleToInvisibleConstraints, List<InvisibleToVisibleConstraint> invisibleToVisibleConstraints){
		this.instanceSchema = instanceSchema;
		this.visibleToInvisibleConstraints = visibleToInvisibleConstraints;
		this.invisibleToVisibleConstraints = invisibleToVisibleConstraints;
	}
	
	/**
	 * The chase algorithm
	 * @return
	 */
	public InstanceSchema run(){
		InstanceSchema instanceSchema = new InstanceSchema(this.instanceSchema);

		/* First part of the chase algorithm */
		for(VisibleToInvisibleConstraint constraint : visibleToInvisibleConstraints){
			if(!instanceSchema.satisfy(constraint)){
				instanceSchema.addAll(Fait.createFaitsForMatching(instanceSchema, constraint));
			}
		}

		doUnificiation();
	
		return instanceSchema;
	
	}
	
	private void doUnificiation(){
		
		boolean added = true;
		while(added){
			added = false;
			Association association = findConstraintAndHomomorphismNotSatisfied(invisibleToVisibleConstraints);

			if(association != null){
				
				InvisibleToVisibleConstraint constraint = association.getConstraint();
				Fait fait = association.getFait();
							
				Homomorphism homomorphism = Homomorphism.createHomomorphism(constraint.getRightVariables(), fait);
				Homomorphism finalHomomorphism = association.getHomomorphism().changeHomomorphism(homomorphism);

				instanceSchema.modifyInstanceWithHomomorphism(finalHomomorphism, association.getConstraint());

				added = true;
			}	
		}
	}

	private Association findConstraintAndHomomorphismNotSatisfied(List<InvisibleToVisibleConstraint> invisibleToVisibleImpliesConstraints){
		for(InvisibleToVisibleConstraint invisibleToVisibleImpliesConstraint : invisibleToVisibleImpliesConstraints){
			List<Homomorphism> homomorphisms = Homomorphism.createHomomorphisms(instanceSchema, invisibleToVisibleImpliesConstraint.getLeftConstraintElementGroup());
			Fait fait;
			for(Homomorphism homomorphism : homomorphisms){
				if((fait = invisibleToVisibleImpliesConstraint.satisfyHomomorphism(instanceSchema, homomorphism)) != null){
					return new Association(homomorphism, invisibleToVisibleImpliesConstraint, fait);
				}
			}
		}
		return null;

	}
}
