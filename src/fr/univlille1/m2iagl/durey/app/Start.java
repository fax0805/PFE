package fr.univlille1.m2iagl.durey.app;

import java.util.HashMap;
import java.util.Map;

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
		
		Relation R = new Relation(rName, 2);
		Relation S = new Relation(sName, 2);
		Relation T = new Relation(tName, 3);
		
		Map<RelationName, Relation> map = new HashMap<>();
		map.put(new RelationName("R"), R);
		map.put(new RelationName("S"), S);
		map.put(new RelationName("T"), T);

		Schema schema = new Schema(map, new HashMap<RelationName, Relation>());
		
		InstanceSchema instanceSchema = new InstanceSchema(schema);
		instanceSchema.add(new InstanceRelation(R, new char[]{'a', 'b'}));
		instanceSchema.add(new InstanceRelation(S, new char[]{'b', 'a'}));
		
		
		Constraint constraint = new ImpliesConstraint(rName, new char[]{'x', 'y'}, sName, new char[]{'y', 'x'});
		
		System.out.println(constraint.isSatisfy(instanceSchema));

	}
}
