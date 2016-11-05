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
		
		Relation R = new Relation(rName, 3);
		Relation S = new Relation(sName, 2);
		Relation T = new Relation(tName, 3);
		
		Map<RelationName, Relation> map = new HashMap<>();
		map.put(new RelationName("R"), R);
		map.put(new RelationName("S"), S);
		map.put(new RelationName("T"), T);

		Schema schema = new Schema(map, new HashMap<RelationName, Relation>());
		
		InstanceSchema instanceSchema = new InstanceSchema(schema);
		instanceSchema.add(new InstanceRelation(T, new char[]{'a', 'a', 'a'}));
		
		List<Constraint> visibleToInvisibleConstraints = new ArrayList<>();
		visibleToInvisibleConstraints.add(new ImpliesConstraint(tName, new char[]{'x', 'y', 'z'}, rName, new char[]{'w', 'x', 'y'}));
		visibleToInvisibleConstraints.add(new ImpliesConstraint(tName, new char[]{'x', 'y', 'z'}, sName, new char[]{'y', 'z'}));

		Chase chase = new Chase(instanceSchema, visibleToInvisibleConstraints, new ArrayList<Constraint>());
		InstanceSchema newInstanceSchema = chase.run();
		System.out.println(newInstanceSchema.toString());

	}
}
