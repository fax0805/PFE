package fr.univlille1.m2iagl.durey.model;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class Association {
	
	private Homomorphism homomorphism;
	private Constraint constraint;
	private Fait fait;
	
	public Association(Homomorphism homomorphism, Constraint constraint, Fait fait){
		this.homomorphism = homomorphism;
		this.constraint = constraint;
		this.fait = fait;
	}
	
	public Homomorphism getHomomorphism(){
		return homomorphism;
	}
	
	public Constraint getConstraint(){
		return constraint;
	}
	
	public Fait getFait(){
		return fait;
	}
	
	@Override
	public String toString(){
		return "[homomorphism=" + homomorphism.toString() + ", constraint=" + constraint + ", fait=" + fait +"]";
	}

}
