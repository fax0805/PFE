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
import fr.univlille1.m2iagl.durey.model.constraint.Constraint;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;
import fr.univlille1.m2iagl.durey.model.constraint.ImpliesConstraint;

public class StartSmallExample {

	public static void main(String [] args){
		
		/*
		 	Visibles : 
			R, 2
			S, 2

			Invisibles :
			T, 2
			U, 2
			
			R(x, y), S(y, z) -> T(x, w)
			R(x, y), S(y, z) -> T(w, x)
			R(x, y) -> U(x, z)
			
			
			T(x, y), U(y, z) -> R(x, z)
		 */
		
		RelationName rName = new RelationName("R");
		RelationName sName = new RelationName("S");
		RelationName tName = new RelationName("T");
		RelationName uName = new RelationName("U");

		
		Relation R = new Relation(rName, 2);
		Relation S = new Relation(sName, 2);
		Relation T = new Relation(tName, 2);
		Relation U = new Relation(uName, 2);
		
		Map<RelationName, Relation> visibleRelations = new HashMap<>();
		visibleRelations.put(new RelationName("R"), R);
		visibleRelations.put(new RelationName("S"), S);
		
		Map<RelationName, Relation> invisibleRelations = new HashMap<>();
		invisibleRelations.put(new RelationName("T"), T);
		invisibleRelations.put(new RelationName("U"), U);

		Schema schema = new Schema(visibleRelations, invisibleRelations);
		
		InstanceSchema instanceSchema = new InstanceSchema(schema);
		instanceSchema.add(new Fait(R, new char[]{'a', 'a'}));
		instanceSchema.add(new Fait(S, new char[]{'a', 'a'}));
		
		
		List<Constraint> visibleToInvisibleConstraints = new ArrayList<>();
		Constraint constraint1 = new ImpliesConstraint(new RelationName[]{rName, sName}, new char[][][]{{{'x', 'y'}}, {{'y', 'z'}}}, tName, new char[]{'x', 'z'});
		Constraint constraint2 = new ImpliesConstraint(new RelationName[]{rName, sName}, new char[][][]{{{'x', 'y'}}, {{'y', 'z'}}}, tName, new char[]{'w', 'x'});
		Constraint constraint3 = new ImpliesConstraint(new RelationName[]{rName}, new char[][][]{{{'x', 'y'}}}, uName, new char[]{'x', 'z'});
		
		visibleToInvisibleConstraints.add(constraint1);
		visibleToInvisibleConstraints.add(constraint2);
		visibleToInvisibleConstraints.add(constraint3);
		
		List<Constraint> invisibleToVisibleConstraints = new ArrayList<>();
		
		Constraint constraint4 = new ImpliesConstraint(new RelationName[]{tName, uName}, new char[][][]{{{'x', 'y'}}, {{'y', 'z'}}}, rName, new char[]{'x', 'z'});
		invisibleToVisibleConstraints.add(constraint4);

		Chase chase = new Chase(instanceSchema, visibleToInvisibleConstraints, invisibleToVisibleConstraints);
		InstanceSchema newInstanceSchema = chase.run();
		System.out.println("---------------------------------------------------");
		System.out.println("Final instance : ");
		System.out.println(newInstanceSchema.toString());

	}
}
