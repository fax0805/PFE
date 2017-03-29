package fr.univlille1.m2iagl.durey.start;

import java.io.PrintWriter;

import fr.univlille1.m2iagl.durey.controller.Chase;
import fr.univlille1.m2iagl.durey.controller.Generator;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;

public class StartSpecifiedChase {
	
	public static void start(PrintWriter printWriter, int nbRelations, int relationArity, int nbConstraints, int constraintSize, double visibleRelations){

		// (int nbRelations, int relationArity, int nbConstraint, int constraintSize, double visibleRelation, double visibleToInvisibleConstraint)
		try {
			Generator generator = new Generator(nbRelations, relationArity, nbConstraints, constraintSize, visibleRelations);
			generator.generate();
			

			InstanceSchema instanceSchema = InstanceSchema.createBasicInstanceSchema(generator.getSchema());

			int nbBefore = instanceSchema.getNbFacts();
			Chase chase = new Chase(instanceSchema, generator.getVisibleToInvisible(), generator.getInvisibleToVisible());

			long time = System.currentTimeMillis();
			instanceSchema = chase.run();
			int nbAfter = instanceSchema.getNbFacts();
			long timeBis = System.currentTimeMillis();
			
			printWriter.println(nbRelations + ", " + relationArity + ", " + nbConstraints + ", " + constraintSize + ", " + visibleRelations + ", " + nbBefore + ", " + nbAfter + ", " + (timeBis - time));
		} catch(Exception e){

		}
	}
}
