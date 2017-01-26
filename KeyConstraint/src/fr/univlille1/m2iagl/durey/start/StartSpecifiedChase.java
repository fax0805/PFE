
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
	
	public static void start(PrintWriter printWriter, int nbRelations, int relationArity, int nbConstraints, int constraintSize, int nbKeyConstraints, double visibleRelations){

		// (int nbRelations, int relationArity, int nbConstraint, int constraintSize, double visibleRelation, double visibleToInvisibleConstraint)
		try {
			Generator generator = new Generator(nbRelations, relationArity, nbConstraints, constraintSize, nbKeyConstraints, visibleRelations);
			generator.generate();

			InstanceSchema instanceSchema = InstanceSchema.createBasicInstanceSchema(generator.getSchema());
			List<InvisibleToInvisibleConstraint> keyConstraints = generator.getKeyConstraints();

			int nbBefore = instanceSchema.getNbFacts();
			int limit = Helper.computeTreeHeight(instanceSchema.getSchema().invisibleKeySet().size(), relationArity);
			Chase chase = new Chase(instanceSchema, generator.getVisibleToInvisible(), generator.getInvisibleToVisible(), keyConstraints, limit);

			long time = System.currentTimeMillis();
			instanceSchema = chase.run();
			int nbAfter = instanceSchema.getNbFacts();
			long timeBis = System.currentTimeMillis();
			
			Graph graph = new Graph(keyConstraints);
			graph.run();
			int nbCycles = graph.getAllCycles().size(), biggestCycleSize = graph.getBiggestCycle().size();
			if(biggestCycleSize > 0)
				biggestCycleSize--;

			
			printWriter.println(nbRelations + ", " + relationArity + ", " + nbConstraints + ", " + constraintSize + ", " + visibleRelations + ", " + nbKeyConstraints + ", " + nbBefore + ", " + nbAfter + ", " + (timeBis - time) + ", " + nbCycles + ", " + biggestCycleSize);

		} catch(Exception e){
			e.printStackTrace();

		}
	}
}
