package fr.univlille1.m2iagl.durey.model;

import java.util.ArrayList;
import java.util.List;

import fr.univlille1.m2iagl.durey.model.constraint.Constraint;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElementGroup;

public class Fait {

	private Relation relation;
	private char[] values;

	public Fait(Relation relation, char[] variables, Homomorphism homomorphism) {
		this.relation = relation;
		values = new char[variables.length];

		fillValuesAccordingToVariablesAndHomomorphism(variables, homomorphism);
	}

	public Fait(Relation relation, char[] values) {
		this.relation = relation;
		this.values = values;
	}

	public static Fait createBasicFait(Relation relation) {
		char[] variables = new char[relation.getArity()];
		for (int i = 0; i < relation.getArity(); i++) {
			variables[i] = Value.basicVar;
		}

		return new Fait(relation, variables);
	}

	public RelationName getRelationName() {
		return relation.getRelationName();
	}

	public boolean match(ConstraintElementGroup constraintElementGroup, Homomorphism homomorphism) {
		for (RelationName relationName : constraintElementGroup.keySet()) {
			ConstraintElement[] constraintElements = constraintElementGroup.get(relationName);
			for (ConstraintElement constraintElement : constraintElements) {
				if (match(constraintElement, homomorphism)) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean match(ConstraintElement constraintElement, Homomorphism homomorphism){
		
		if(!constraintElement.getRelationName().equals(relation.getRelationName()))
			return false;
		char[] constraintVars = constraintElement.getVariables();
		for(int i=0;i<values.length;i++){
			char constraintChar = constraintVars[i];
			
			if(homomorphism.containsVariable(constraintChar) && values[i] != homomorphism.get(constraintChar)){
				return false;
			}
		}
		
		return true;
	}

	public void modifyInstanceWithHomomorphism(Homomorphism homomorphism) {

		for (int i = 0; i < values.length; i++) {
			if (homomorphism.containsVariable(values[i])) {
				values[i] = homomorphism.get(values[i]);
			}
		}

	}

	public int getArity() {
		return relation.getArity();
	}

	public char get(int i) {
		return values[i];
	}

	private void fillValuesAccordingToVariablesAndHomomorphism(char[] variables, Homomorphism homomorphism) {
		for (int i = 0; i < variables.length; i++) {
			char variable = variables[i];

			if (homomorphism.containsVariable(variable)) {
				values[i] = homomorphism.get(variable);

			} else {
				values[i] = Value.getNewValue();

			}
		}

	}

	/**
	 * Créer les faits qui font satisfaire la contrainte constraint à partir des
	 * faits contenus dans instanceSchema
	 * 
	 * @param instanceSchema
	 * @param constraint
	 * @return la liste de faits
	 */
	public static List<Fait> createFaitsForMatching(InstanceSchema instanceSchema, Constraint constraint) {
		List<Fait> newFacts = new ArrayList<Fait>();
		List<Homomorphism> homomorphisms = Homomorphism.createHomomorphisms(instanceSchema,
				constraint.getLeftConstraintElementGroup());

		for (Homomorphism homomorphism : homomorphisms) {
			ConstraintElementGroup constraintElementGroup = constraint.getRightConstraintElementGroup();
			for(RelationName relationName : constraintElementGroup.keySet()){
				for(int i=0;i<constraintElementGroup.get(relationName).length;i++){
					ConstraintElement constraintElement = constraintElementGroup.getAt(relationName, i);
					newFacts.add(Fait.createFaitForMatching(instanceSchema.getSchema().getRelation(relationName), constraintElement, homomorphism));
				}
			}
		}

		return newFacts;

	}
	
	private static Fait createFaitForMatching(Relation relation, ConstraintElement constraintElement, Homomorphism homomorphism){
		return new Fait(relation, constraintElement.getVariables(), homomorphism);
		
	}

	@Override
	public String toString() {
		String string = relation.getRelationName().toString() + "(";

		for (int i = 0; i < values.length - 1; i++) {
			string += values[i] + ", ";
		}
		return string += values[values.length - 1] + ")";
	}
}
