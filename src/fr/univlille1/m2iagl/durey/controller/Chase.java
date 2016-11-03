package fr.univlille1.m2iagl.durey.controller;

import java.util.List;

import fr.univlille1.m2iagl.durey.model.InstanceSchema;
import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class Chase {
	
	private InstanceSchema instanceSchema;
	private List<Constraint> visibleToInvisibleConstraints;
	private List<Constraint> invisibleToVisibleConstraints;
	
	public Chase(InstanceSchema instanceSchema, List<Constraint> visibleToInvisibleConstraints, List<Constraint> invisibleToVisibleConstraint){
		this.instanceSchema = instanceSchema;
		this.visibleToInvisibleConstraints = visibleToInvisibleConstraints;
		this.invisibleToVisibleConstraints = invisibleToVisibleConstraint;
	}
	
	public InstanceSchema run(){
		InstanceSchema instanceSchema = new InstanceSchema(this.instanceSchema);
		

		for(Constraint constraint : visibleToInvisibleConstraints){
			if(!constraint.isSatisfy(instanceSchema)){
				instanceSchema.add(constraint.createInstanceRelationForMatching(instanceSchema));
			}
		}
		
		return instanceSchema;
	}

}
