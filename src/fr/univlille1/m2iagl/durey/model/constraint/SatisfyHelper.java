package fr.univlille1.m2iagl.durey.model.constraint;

import java.util.List;

import fr.univlille1.m2iagl.durey.model.Homomorphism;
import fr.univlille1.m2iagl.durey.model.Fait;
import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.RelationName;

public class SatisfyHelper {
	
	public static boolean matchInstanceSchema(RelationName relationName, InstanceSchema instanceSchema, Homomorphism homomorphism, Constraint constraint){
		List<Fait> instanceRelations = instanceSchema.get(relationName);
		
		for(Fait instanceRelation : instanceRelations){
			if(matchInstanceRelation(instanceRelation, homomorphism, constraint))
				return true;
		}
		
		return false;
	}
	
	public static boolean matchInstanceRelation(Fait fait, Homomorphism homomorphism, Constraint constraint){
		for(int i=0;i<fait.getArity();i++){
			Character value = fait.get(i);
			char variable = constraint.getRightVariable(i);
			if(!homomorphism.isCorrectValue(variable, value))
				return false;
		}
		
		return true;
	}
	
	
	public static Fait getInstanceRelationNonMatching(RelationName relationName, InstanceSchema instanceSchema, Homomorphism homomorphism, Constraint constraint){
		List<Fait> instanceRelations = instanceSchema.get(relationName);
		
		for(Fait instanceRelation : instanceRelations){
			if(!matchInstanceRelation(instanceRelation, homomorphism, constraint))
				return instanceRelation;
		}
		
		return null;
	}
}
