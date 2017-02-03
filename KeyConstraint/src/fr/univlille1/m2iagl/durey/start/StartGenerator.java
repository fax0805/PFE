package fr.univlille1.m2iagl.durey.start;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class StartGenerator {

	public static void main(String [] args) throws IOException, InterruptedException{

		PrintWriter printWriter = new PrintWriter(new File("results/comparaison.csv"));
		printWriter.println("nbRelations, relationArity, nbConstraints, constraintSize, visibleRelations, nbKeyConstraints, initialSchemaSize, finalSchemaSize, time (ms), nbCycles, biggestCycle");

		Properties properties = new Properties();
		properties.load(new FileReader("config.cfg"));

		int nbRelations = Integer.parseInt(properties.getProperty("NB_RELATIONS"));
		int relationArity = Integer.parseInt(properties.getProperty("RELATION_ARITY"));;
		int nbConstraints = Integer.parseInt(properties.getProperty("NB_CONSTRAINTS"));
		int constraintSize = 1;
		int nbKeyConstraints = Integer.parseInt(properties.getProperty("NB_KEY_CONSTRAINTS"));
		double visibleRelations = Double.parseDouble(properties.getProperty("VISIBLE_RELATIONS"));

		int nbTimes = Integer.parseInt(properties.getProperty("NB_TIMES"));
		
		int biggestCycleSize = Integer.parseInt(properties.getProperty("BIGGEST_CYCLE_SIZE"));
		
		int secretSize = Integer.parseInt(properties.getProperty("SECRET_SIZE"));

		int i=0;

		for(int j=0;j<nbTimes;j++){
			StartSpecifiedChase.start(printWriter, nbRelations, relationArity, nbConstraints, constraintSize, nbKeyConstraints, visibleRelations, biggestCycleSize, secretSize);

			System.out.println("i : " + i);
			i++;
		}

		printWriter.close();
	}
}
