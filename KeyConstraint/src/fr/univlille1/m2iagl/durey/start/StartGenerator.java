package fr.univlille1.m2iagl.durey.start;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class StartGenerator {
	
	public static void main(String [] args) throws IOException, InterruptedException{

		// (int nbRelations, int relationArity, int nbConstraint, int constraintSize, double visibleRelation, double visibleToInvisibleConstraint)
		/*
		Generator generator = new Generator(10000, 50, 5000, 10, 0.5);
		generator.generate();

		InstanceSchema instanceSchema = InstanceSchema.createBasicInstanceSchema(generator.getSchema());

		System.out.println("instanceSchema : " + instanceSchema);

		Chase chase = new Chase(instanceSchema, generator.getVisibleToInvisible(), generator.getInvisibleToVisible());

		long time = System.currentTimeMillis();
		instanceSchema = chase.run();
		long timeBis = System.currentTimeMillis();
		System.out.println(timeBis - time);

		System.out.println("InstanceSchema : " + instanceSchema);
		System.exit(1);
		 */
		
		PrintWriter printWriter = new PrintWriter(new File("results/comparaison.csv"));
		printWriter.println("nbRelations, relationArity, nbConstraints, constraintSize, visibleRelations, nbKeyConstraints, initialSchemaSize, finalSchemaSize, time (ms)");

		int[] nbsRelations = new int[]{10};
		int[] relationArities = new int[]{2};
		int[] nbsConstraints = new int[]{8};
		int[] constraintSizes = new int[]{1};
		int[] nbsKeyConstraints = new int[]{5};
		double[] visiblesRelations = new double[]{0.5};

		int i=0;

		for(int nbRelations : nbsRelations){
			for(int relationArity : relationArities){
				for(int nbConstraints : nbsConstraints){
					for(int constraintSize : constraintSizes){
						for(int nbKeyConstraints : nbsKeyConstraints){
							for(double visibleRelations : visiblesRelations){
								StartSpecifiedChase.start(printWriter, nbRelations, relationArity, nbConstraints, constraintSize, nbKeyConstraints, visibleRelations);

								System.out.println("i : " + i);
								i++;
							}
						}
					}
				}
			}
		}
		
		printWriter.close();
	}
}
