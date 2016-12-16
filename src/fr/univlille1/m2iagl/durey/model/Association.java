package fr.univlille1.m2iagl.durey.model;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class Association {
	
	private Homomorphism homomorphism;
	private Constraint constraint;
	
	public Association(Homomorphism homomorphism, Constraint constraint){
		this.homomorphism = homomorphism;
		this.constraint = constraint;
	}
	
	public Homomorphism getHomomorphism(){
		return homomorphism;
	}
	
	public Constraint getConstraint(){
		return constraint;
	}

}
