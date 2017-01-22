package fr.univlille1.m2iagl.durey.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElementGroup;
import fr.univlille1.m2iagl.durey.model.constraint.VisibleToInvisibleConstraint;
/**
 * Represents an instance of a given schema. 
 * The facts are represented with a map.
 * for each relation name, the map stores a list of facts.
 * @author Antonin
 *
 */
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
	
	/**
	 * For each visible relation into the schema
	 * create a fact with identical column ('a')
	 * @param schema
	 * @return the instanceSchema with all the facts inside
	 */
	public static InstanceSchema createBasicInstanceSchema(Schema schema){
		InstanceSchema instanceSchema = new InstanceSchema(schema);
		
		
		for(RelationName relationName : schema.visibleKeySet()){
			instanceSchema.add(Fait.createBasicFait(schema.getRelation(relationName)));
		}
		return instanceSchema;
	}
	
	/**
	 * Regarde si l'instance (this) satisfait la contrainte passée en paramètre
	 * Pour cela, on créé tous les homomorphismes
	 * S'il n'y en a aucun, c'est vrai,
	 * Sinon, on regarde, pour tous les homomorphismes, tous les faits et on retourne vrai si au moins
	 * un fait match chacun des homomorphismes avec la partie droite de la constrainte.
	 * @param constraint
	 * @return
	 */

	public boolean satisfy(VisibleToInvisibleConstraint constraint){
		ConstraintElementGroup constraintElementGroup = constraint.getLeftConstraintElementGroup();
		
		List<Homomorphism> homomorphisms = null;
		for(int i=0;i<constraintElementGroup.size();i++){
			homomorphisms = Homomorphism.createHomomorphisms(this, constraintElementGroup);
			if(homomorphisms.isEmpty())
				return true;
		}
				
		constraintElementGroup = constraint.getRightConstraintElementGroup();
		
		for(Homomorphism homomorphism : homomorphisms){
			boolean match = false;
			for(RelationName relationName : values.keySet()){
				for(Fait fait : values.get(relationName)){
					if(fait.match(constraintElementGroup, homomorphism))
						match = true;
				}
			}
			
			if(!match)
				return false;
		}
		
		return true;
	}
	
	/**
	 * Modifie tous les faits en fonction de l'homomorphisme et de la contrainte donnée
	 * Fonction utilisée dans la seconde partie du chase lorsque des éléments invisibles deviennent visibles
	 * @param homomorphism
	 * @param constraint
	 */
	public void modifyInstanceWithHomomorphism(Homomorphism homomorphism, Constraint constraint){
		for(RelationName relationName : constraint.getLeftConstraintElementGroup().keySet()){
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

	public void add(Fait fait){
		values.get(fait.getRelationName()).add(fait);
	}
	
	public void addAll(List<Fait> faits){
		for(Fait fait : faits){
			add(fait);
		}
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
	
	/**
	 * @return the number of facts in this instanceSchema
	 */
	public int getNbFacts(){
		int size = 0;
		for(RelationName relationName : values.keySet()){
			size += values.get(relationName).size();
		}
		return size;
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
