package fr.univlille1.m2iagl.durey.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;

public class InstanceSchema {



	private Schema schema;

	Map<RelationName, List<Fait>> values;

	public InstanceSchema(Schema schema){
		this.schema = schema;
		values = new HashMap<>();

		fillKeys();
	}

	public InstanceSchema(InstanceSchema instanceSchema){
		this.schema = instanceSchema.schema;
		this.values = instanceSchema.values;
	}
	
	public static InstanceSchema createBasicInstanceSchema(Schema schema){
		InstanceSchema instanceSchema = new InstanceSchema(schema);
		
		
		for(RelationName relationName : schema.visibleKeySet()){
			instanceSchema.add(Fait.createBasicFait(schema.getRelation(relationName)));
		}
		return instanceSchema;
	}

	public void modifyInstanceWithHomomorphism(Homomorphism homomorphism, Constraint constraint){
		for(RelationName relationName : constraint.getLeftRelationNames()){
			for(Fait fait : values.get(relationName)){
				fait.modifyInstanceWithHomomorphism(homomorphism);
			}
		}
	}

	private void fillKeys(){
		for(RelationName relationName : schema.visibleKeySet()){
			values.put(relationName, new ArrayList<Fait>());
		}

		for(RelationName relationName : schema.invisibleKeySet()){
			values.put(relationName, new ArrayList<Fait>());			
		}
	}

	public void add(Fait instanceRelation){
		values.get(instanceRelation.getRelationName()).add(instanceRelation);
	}

	public Set<RelationName> keySet(){
		return values.keySet();
	}

	public List<Fait> get(RelationName relationName){
		return values.get(relationName);
	}

	public Schema getSchema(){
		return schema;
	}

	@Override
	public String toString(){
		String string = "";
		for(RelationName relationName : values.keySet()){
			for(Fait instanceRelation : values.get(relationName)){
				string += instanceRelation.toString()  + "\n";
			}
		}

		return string;
	}
}
