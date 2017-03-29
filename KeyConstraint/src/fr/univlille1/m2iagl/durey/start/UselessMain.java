package fr.univlille1.m2iagl.durey.start;

import java.util.Scanner;

import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.RelationName;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;

public class UselessMain {

	public static void main(String[] args){
		

		Fait fait = new Fait(new Relation(new RelationName("A"), 2), new char[]{'a', 'b'}, 0);
		ConstraintElement constraintElement = new ConstraintElement(new RelationName("A"), new char[]{'x', 'y'});
		Homomorphism homomorphism = new Homomorphism();
		homomorphism.put('x', 'a');
		homomorphism.put('y', 'b');

		System.out.println(fait.match(constraintElement, homomorphism));


		}

	}
