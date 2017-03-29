package fr.univlille1.m2iagl.durey.start;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

public class StartGenerator {


	public static void main(String [] args) throws IOException, InterruptedException{

		PrintWriter printWriter = new PrintWriter(new File("results/comparaison.csv"));
		printWriter.println("nbRelations, relationArity, nbConstraints, constraintSize, visibleRelations, initialSchemaSize, finalSchemaSize, time (ms)");
		
		Properties p = new Properties();
		
		p.load(new FileReader("config.cfg"));
		
		
		int nbRelations = Integer.parseInt(p.getProperty("NB_RELATIONS"));
		int relationArity = Integer.parseInt(p.getProperty("RELATION_ARITY"));
		int nbConstraints = Integer.parseInt(p.getProperty("NB_CONSTRAINTS"));
		int constraintSize = Integer.parseInt(p.getProperty("CONSTRAINTS_SIZE"));
		double visibleRelations = Double.parseDouble(p.getProperty("VISIBLE_RELATIONS"));
		int nbTimes = Integer.parseInt(p.getProperty("NB_TIMES"));

		for(int i=0;i<nbTimes;i++){
			System.out.println("i : " + i);
			StartSpecifiedChase.start(printWriter, nbRelations, relationArity, nbConstraints, constraintSize, visibleRelations);
		}
		
		printWriter.close();
	}
}
