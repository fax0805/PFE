package fr.univlille1.m2iagl.durey.app;

import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;

public class Helper {

	public static ConstraintElement[] concat(ConstraintElement[] a, ConstraintElement[] b) {
		int aLen = a.length;
		int bLen = b.length;
		ConstraintElement[] c= new ConstraintElement[aLen+bLen];
		System.arraycopy(a, 0, c, 0, aLen);
		System.arraycopy(b, 0, c, aLen, bLen);
		return c;
	}
	
	public static int computeTreeHeight(int nbInvisibleRelations, int arity){
		return (int)(nbInvisibleRelations * /*Math.pow(arity, arity)*/ 1);
	}

}
