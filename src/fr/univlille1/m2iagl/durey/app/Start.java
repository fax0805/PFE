package fr.univlille1.m2iagl.durey.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.durey.controller.Chase;
import fr.univlille1.m2iagl.durey.model.InstanceRelation;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.RelationName;
import fr.univlille1.m2iagl.durey.model.Schema;
import fr.univlille1.m2iagl.durey.model.constraint.Constraint;
import fr.univlille1.m2iagl.durey.model.constraint.ImpliesConstraint;

public class Start {

	public static void main(String [] args){
		
		RelationName rName = new RelationName("R");
		RelationName sName = new RelationName("S");
		RelationName tName = new RelationName("T");
		RelationName uName = new RelationName("U");
		RelationName vName = new RelationName("V");

		
		Relation R = new Relation(rName, 3);
		Relation S = new Relation(sName, 2);
		Relation T = new Relation(tName, 3);
		Relation U = new Relation(uName, 3);
		Relation V = new Relation(vName, 2);
		
		Map<RelationName, Relation> visibleRelations = new HashMap<>();
		visibleRelations.put(new RelationName("R"), R);
		visibleRelations.put(new RelationName("S"), S);
		visibleRelations.put(new RelationName("T"), T);
		
		Map<RelationName, Relation> invisibleRelations = new HashMap<>();
		invisibleRelations.put(new RelationName("U"), U);
		invisibleRelations.put(new RelationName("V"), V);

		Schema schema = new Schema(visibleRelations, invisibleRelations);
		
		InstanceSchema instanceSchema = new InstanceSchema(schema);
		instanceSchema.add(new InstanceRelation(R, new char[]{'a', 'a', 'a'}));
		instanceSchema.add(new InstanceRelation(S, new char[]{'a', 'a'}));
		instanceSchema.add(new InstanceRelation(T, new char[]{'a', 'a', 'a'}));
		
		List<Constraint> visibleToInvisibleConstraints = new ArrayList<>();
		visibleToInvisibleConstraints.add(new ImpliesConstraint(tName, new char[]{'x', 'y', 'z'}, uName, new char[]{'w', 'x', 'y'}));
		visibleToInvisibleConstraints.add(new ImpliesConstraint(tName, new char[]{'x', 'y', 'z'}, vName, new char[]{'y', 'z'}));
		
		List<Constraint> invisibleToVisibleConstraints = new ArrayList<>();
		invisibleToVisibleConstraints.add(new ImpliesConstraint(uName, new char[]{'w', 'x', 'y'}, sName, new char[]{'w', 'x'}));
		
		

		Chase chase = new Chase(instanceSchema, visibleToInvisibleConstraints, invisibleToVisibleConstraints);
		InstanceSchema newInstanceSchema = chase.run();
		System.out.println(newInstanceSchema.toString());

	}
}
