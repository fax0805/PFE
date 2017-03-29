package fr.univlille1.m2iagl.durey.start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.durey.controller.Chase;
import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.RelationName;
import fr.univlille1.m2iagl.durey.model.Schema;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToInvisibleConstraint;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToVisibleConstraint;
import fr.univlille1.m2iagl.durey.model.constraint.VisibleToInvisibleConstraint;

public class StartSmallExample {

	public static void main(String [] args){
		
		/*
		 	Visible :
		 	A(a, a)
		 	Invisible :
			
			Visible To Invisible
			A(x, y) -> B(x, z)
			Key constraints
			B(x, y) -> B(y, z)
			InvisibleToVisible
			B(x, z) -> A(x, y)
		 */
		
		RelationName aName = new RelationName("A");
		RelationName bName = new RelationName("B");
		
		Relation A = new Relation(aName, 2);
		Relation B = new Relation(bName, 2);

		
		Map<RelationName, Relation> visibleRelations = new HashMap<>();
		visibleRelations.put(aName, A);
		
		Map<RelationName, Relation> invisibleRelations = new HashMap<>();
		invisibleRelations.put(bName, B);

		Schema schema = new Schema(visibleRelations, invisibleRelations);
		
		InstanceSchema instanceSchema = new InstanceSchema(schema);
		instanceSchema.add(new Fait(A, new char[]{'a', 'a'}, 0));
		
		List<VisibleToInvisibleConstraint> visibleToInvisibleConstraints = new ArrayList<>();
		visibleToInvisibleConstraints.add(new VisibleToInvisibleConstraint(aName, new char[]{'x', 'y'}, new RelationName[]{bName}, new char[][][]{{{'x', 'z'}}}));
		
		List<InvisibleToInvisibleConstraint> keyConstraints = new ArrayList<>();
		
		keyConstraints.add(new InvisibleToInvisibleConstraint(bName, new char[]{'x', 'y'}, bName, new char[]{'y', 'z'}));
		
		List<InvisibleToVisibleConstraint> invisibleToVisibleConstraints = new ArrayList<>();
		invisibleToVisibleConstraints.add(new InvisibleToVisibleConstraint(bName, new char[]{'x', 'z'}, aName, new char[]{'x', 'y'}));
		
		int nbRelations = visibleRelations.keySet().size() + invisibleRelations.keySet().size();
		int arity = 2;
		int nbConstraints = visibleToInvisibleConstraints.size() + keyConstraints.size();
		//int limit = Helper.computeTreeHeight(nbRelations, n, arity, nbConstraints);
		
		int limit = 20;
		Chase chase = new Chase(instanceSchema, visibleToInvisibleConstraints, invisibleToVisibleConstraints, keyConstraints, limit, 300000);
		InstanceSchema newInstanceSchema = chase.run();
		System.out.println("---------------------------------------------------");
		System.out.println("Final instance : ");
		System.out.println(newInstanceSchema.toString());

	}
}
