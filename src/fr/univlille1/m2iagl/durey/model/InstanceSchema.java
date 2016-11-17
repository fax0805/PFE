package fr.univlille1.m2iagl.durey.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class InstanceSchema {



	private Schema schema;

	Map<RelationName, List<InstanceRelation>> values;

	public InstanceSchema(Schema schema){
		this.schema = schema;
		values = new HashMap<>();

		fillKeys();
	}

	public InstanceSchema(InstanceSchema instanceSchema){
		this.schema = instanceSchema.schema;
		this.values = instanceSchema.values;
	}

	public void modifyInstanceWithHomomorphism(Homomorphism homomorphism, Constraint constraint){
		for(InstanceRelation instanceRelation : values.get(constraint.getLeftRelationName())){
			instanceRelation.modifyInstanceWithHomomorphismAndConstraint(homomorphism, constraint);
		}
	}

	private void fillKeys(){
		for(RelationName relationName : schema.visibleKeySet()){
			values.put(relationName, new ArrayList<InstanceRelation>());
		}

		for(RelationName relationName : schema.invisibleKeySet()){
			values.put(relationName, new ArrayList<InstanceRelation>());			
		}
	}

	public void add(InstanceRelation instanceRelation){
		values.get(instanceRelation.getRelationName()).add(instanceRelation);
	}

	public Set<RelationName> keySet(){
		return values.keySet();
	}

	public List<InstanceRelation> get(RelationName relationName){
		return values.get(relationName);
	}

	public Schema getSchema(){
		return schema;
	}

	@Override
	public String toString(){
		String string = "";
		for(RelationName relationName : values.keySet()){
			for(InstanceRelation instanceRelation : values.get(relationName)){
				string += instanceRelation.toString()  + "\n";
			}
		}

		return string;
	}
}
