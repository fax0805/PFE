package fr.univlille1.m2iagl.durey.controller;

import java.util.List;

import fr.univlille1.m2iagl.durey.model.Association;
import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToInvisibleConstraint;
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
	private List<InvisibleToInvisibleConstraint> invisibleToInvisibleConstraints;
	private List<InvisibleToVisibleConstraint> invisibleToVisibleConstraints;

	private int limit;

	public Chase(InstanceSchema instanceSchema, List<VisibleToInvisibleConstraint> visibleToInvisibleConstraints, List<InvisibleToVisibleConstraint> invisibleToVisibleConstraints, List<InvisibleToInvisibleConstraint> invisibleToInvisibleConstraints, int limit){
		this.instanceSchema = instanceSchema;
		this.visibleToInvisibleConstraints = visibleToInvisibleConstraints;
		this.invisibleToInvisibleConstraints = invisibleToInvisibleConstraints;
		this.invisibleToVisibleConstraints = invisibleToVisibleConstraints;

		this.limit = limit;
	}

	/**
	 * The chase algorithm
	 * @return
	 */
	public InstanceSchema run(){
		InstanceSchema instanceSchema = new InstanceSchema(this.instanceSchema);
		System.out.println("--------------------");
		System.out.println("Initial : instanceSchema.size() : " + instanceSchema.getNbFacts());
		/* First part of the chase algorithm */
		for(VisibleToInvisibleConstraint constraint : visibleToInvisibleConstraints){
			if(!instanceSchema.satisfy(constraint)){
				instanceSchema.addAll(Fait.createFaitsForMatching(instanceSchema, constraint, 0));
			}
		}
		System.out.println("After dealing with visibleToInvisibleConstraint : instanceSchema.size() : " + instanceSchema.getNbFacts());


		dealWithKeysConstraints();
		System.out.println("After dealing with keyConstraint : instanceSchema.size() : " + instanceSchema.getNbFacts());


		doUnificiation();

		return instanceSchema;
	}

	private void dealWithKeysConstraints(){
		boolean added = true;
		while(added){

			added = false;
			Association association = findInvisibleToInvisibleConstraintHomomorphismAndFactNotSatisfied(invisibleToInvisibleConstraints);
			if(association != null){

				InvisibleToInvisibleConstraint constraint = (InvisibleToInvisibleConstraint) association.getConstraint();
				ConstraintElement constraintElement = constraint.getRightConstraintElementGroup().getFirstConstraitElement();
				Relation relation = instanceSchema.getSchema().getRelation(constraintElement.getRelationName());
				instanceSchema.add(Fait.createFaitForMatching(relation, constraintElement, association.getHomomorphism(), association.getFait().getCpt()+1));
				added = true;
			}	
		}
	}

	private void doUnificiation(){

		boolean added = true;
		while(added){
			added = false;
			Association association = findInvisibleToVisibleConstraintHomomorphismAndFactNotSatisfied(invisibleToVisibleConstraints);

			if(association != null){
				InvisibleToVisibleConstraint invisibleToVisibleConstraint = (InvisibleToVisibleConstraint) association.getConstraint();
				Fait fait = association.getFait();

				Homomorphism homomorphism = Homomorphism.createHomomorphism(invisibleToVisibleConstraint.getRightVariables(), fait);
				Homomorphism finalHomomorphism = association.getHomomorphism().changeHomomorphism(homomorphism);

				instanceSchema.modifyInstanceWithHomomorphism(finalHomomorphism, invisibleToVisibleConstraint);

				added = true;
			}	
		}
	}

	private Association findInvisibleToInvisibleConstraintHomomorphismAndFactNotSatisfied(List<InvisibleToInvisibleConstraint> invisibleToInvisibleConstraints){
		for(InvisibleToInvisibleConstraint constraint : invisibleToInvisibleConstraints){
			List<Homomorphism> homomorphisms = Homomorphism.createHomomorphisms(instanceSchema, constraint.getLeftConstraintElementGroup());
			Fait fait;
			for(Homomorphism homomorphism : homomorphisms){

				fait = constraint.satisfyHomomorphism(instanceSchema, homomorphism);
				if(fait != null && fait.getCpt() < limit)
					return new Association(homomorphism, constraint, fait);
			}
		}
		return null;
	}

	private Association findInvisibleToVisibleConstraintHomomorphismAndFactNotSatisfied(List<InvisibleToVisibleConstraint> invisibleToVisibleImpliesConstraints){
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
