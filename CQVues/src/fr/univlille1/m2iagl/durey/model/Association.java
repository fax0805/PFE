package fr.univlille1.m2iagl.durey.model;

import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToVisibleConstraint;

public class Association {
	
	private Homomorphism homomorphism;
	private InvisibleToVisibleConstraint constraint;
	private Fait fait;
	
	public Association(Homomorphism homomorphism, InvisibleToVisibleConstraint constraint, Fait fait){
		this.homomorphism = homomorphism;
		this.constraint = constraint;
		this.fait = fait;
	}
	
	public Homomorphism getHomomorphism(){
		return homomorphism;
	}
	
	public InvisibleToVisibleConstraint getConstraint(){
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
