
package fr.univlille1.m2iagl.durey.start;

import java.io.PrintWriter;
import java.util.List;

import fr.univlille1.m2iagl.durey.app.Helper;
import fr.univlille1.m2iagl.durey.controller.Chase;
import fr.univlille1.m2iagl.durey.controller.Generator;
import fr.univlille1.m2iagl.durey.graph.Graph;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToInvisibleConstraint;

public class StartSpecifiedChase {
	
	public static void start(PrintWriter printWriter, int nbRelations, int relationArity, int nbConstraints, int constraintSize, int nbKeyConstraints, double visibleRelations, int biggestCycleSize, int secretSize, int limitTime){

		// (int nbRelations, int relationArity, int nbConstraint, int constraintSize, double visibleRelation, double visibleToInvisibleConstraint)
		try {
			Generator generator = new Generator(nbRelations, relationArity, nbConstraints, constraintSize, nbKeyConstraints, visibleRelations, biggestCycleSize);
			generator.generate();

			InstanceSchema instanceSchema = InstanceSchema.createBasicInstanceSchema(generator.getSchema());
			List<InvisibleToInvisibleConstraint> keyConstraints = generator.getKeyConstraints();

			int nbBefore = instanceSchema.getNbFacts();
			
			Graph graph = new Graph(keyConstraints);
			graph.run();
			int nbCycles = graph.getAllCycles().size();
			biggestCycleSize = graph.getBiggestCycle().size();
			
			int limit = Helper.computeTreeHeight(biggestCycleSize, secretSize);
			
			
			System.out.print(nbRelations + ", " + relationArity + ", " + nbConstraints + ", " + constraintSize + ", " + visibleRelations + ", " + nbKeyConstraints + ", " + nbBefore + ", ");

			
			
			Chase chase = new Chase(instanceSchema, generator.getVisibleToInvisible(), generator.getInvisibleToVisible(), keyConstraints, limit, limitTime);

			long time = System.currentTimeMillis();
			instanceSchema = chase.run();
			int nbAfter = instanceSchema.getNbFacts();
			long timeBis = System.currentTimeMillis();
			
			System.out.println(nbAfter + ", " + (timeBis - time) + ", " + nbCycles + ", " + biggestCycleSize);
			

		} catch(Exception e){
			e.printStackTrace();

		}
	}
}
