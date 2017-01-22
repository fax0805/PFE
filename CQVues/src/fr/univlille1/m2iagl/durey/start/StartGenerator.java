package fr.univlille1.m2iagl.durey.start;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class StartGenerator {
	

	public static void main(String [] args) throws IOException, InterruptedException{

		PrintWriter printWriter = new PrintWriter(new File("results/comparaison.csv"));
		printWriter.println("nbRelations, relationArity, nbConstraints, constraintSize, visibleRelations, initialSchemaSize, finalSchemaSize, time (ms)");
		int[] nbsRelations = new int[]{/*10, 100, */1000};
		int[] relationArities = new int[]{20};
		int[] nbsConstraints = new int[]{10, 100, 1000, 10000};
		int[] constraintSizes = new int[]{20};
		double[] visiblesRelations = new double[]{0.3/*, 0.5, 0.7*/};

		for(int nbRelations : nbsRelations){
			for(int relationArity : relationArities){
				for(int nbConstraints : nbsConstraints){
					for(int constraintSize : constraintSizes){
						for(double visibleRelations : visiblesRelations){
							StartSpecifiedChase.start(printWriter, nbRelations, relationArity, nbConstraints, constraintSize, visibleRelations);
						}
					}
				}
			}
		}
		
		printWriter.close();
	}
}
