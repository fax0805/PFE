package fr.univlille1.m2iagl.durey.controller;

import java.util.HashMap;
import java.util.Map;

import fr.univlille1.m2iagl.durey.app.Helper;
import fr.univlille1.m2iagl.durey.model.RelationName;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;

public class Factory {
	
	private static char relationName = 'A';

	public static Map<RelationName, ConstraintElement[]> createConstraintElements(RelationName[] relationNames,
			char[][][] vars) {
		Map<RelationName, ConstraintElement[]> map = new HashMap<RelationName, ConstraintElement[]>();

		for (int i = 0; i < relationNames.length; i++) {
			ConstraintElement[] array = new ConstraintElement[vars[i].length];
			for (int j = 0; j < vars[i].length; j++) {
				array[j] = Factory.createConstraintElement(relationNames[i], vars[i][j]);
			}
			
			if(!map.containsKey(relationNames[i])){
				map.put(relationNames[i], array);
			} else {
				ConstraintElement[] existingConstraintElements = map.get(relationNames[i]);
				ConstraintElement[] newConstraintElements = Helper.concat(array, existingConstraintElements);
				map.put(relationNames[i],  newConstraintElements);
			}
			
		}

		return map;
	}

	public static ConstraintElement createConstraintElement(RelationName relationName, char[] vars) {
		return new ConstraintElement(relationName, vars);
	}
	
	public static RelationName getNewRelationName(){
		char c = relationName;
		relationName++;
		
		return new RelationName(c + "");
	}

}
