package fr.univlille1.m2iagl.durey.start;

import fr.univlille1.m2iagl.durey.controller.Chase;
import fr.univlille1.m2iagl.durey.controller.Generator;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;

public class StartGenerator {

	public static void main(String [] args){
		
		// (int nbRelations, int relationArity, int nbConstraint, int constraintSize, double visibleRelation, double visibleToInvisibleConstraint)
		
		int nbRelations = Integer.parseInt(args[0]);
		int relationArity = Integer.parseInt(args[1]);
		int nbConstraint = Integer.parseInt(args[2]);
		int constraintSize = Integer.parseInt(args[3]);
		double visibleRelation = Double.parseDouble(args[4]);
		double visibleToInvisibleConstraint = Double.parseDouble(args[5]);
		
		Generator generator = new Generator(nbRelations, relationArity, nbConstraint, constraintSize, visibleRelation, visibleToInvisibleConstraint);
		generator.generate();
		
		/*
		Schema generatedSchema = generator.getSchema();
		List<Constraint> generatedVisibleToInvisible = generator.getVisibleToInvisible();
		List<Constraint> generatedInvisibleToVisible = generator.getInvisibleToVisible();
		*/

		InstanceSchema instanceSchema = InstanceSchema.createBasicInstanceSchema(generator.getSchema());

		Chase chase = new Chase(instanceSchema, generator.getVisibleToInvisible(), generator.getInvisibleToVisible());
		InstanceSchema newInstanceSchema = chase.run();
		System.out.println("---------------------------------------------------");
		System.out.println("Final instance : ");
		System.out.println(newInstanceSchema.toString());

	}
}
