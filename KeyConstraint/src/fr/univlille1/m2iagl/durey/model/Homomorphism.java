package fr.univlille1.m2iagl.durey.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElementGroup;

public class Homomorphism {

	private Map<Character, Character> values;
	private int cpt;

	public Homomorphism() {
		values = new HashMap<Character, Character>();
	}

	public boolean containsVariable(char variable) {
		return values.containsKey(variable);
	}

	public char get(char variable) {
		return values.get(variable);
	}

	public void put(Character variable, Character value) {
		values.put(variable, value);
	}

	public boolean isCorrectValue(Character variable, Character value) {

		if (!values.containsKey(variable))
			return true;
		return values.get(variable).equals(value);
	}
	
	public int getCpt(){
		return cpt;
	}

	public Homomorphism merge(Homomorphism homomorphism) {
		Homomorphism finalHomomorphism = new Homomorphism();

		for (Character key : values.keySet()) {
			finalHomomorphism.put(key, values.get(key));
		}

		for (Character key : homomorphism.values.keySet()) {
			char value = homomorphism.values.get(key);
			if (finalHomomorphism.containsVariable(key) && finalHomomorphism.get(key) != value)
				return null;

			finalHomomorphism.put(key, value);
		}

		return finalHomomorphism;
	}

	public Homomorphism changeHomomorphism(Homomorphism homomorphism) {
		Homomorphism result = new Homomorphism();
		for (char key : homomorphism.values.keySet()) {
			// Le IF est a vérifier
			if(this.values.containsKey(key)){
				result.put(this.get(key), homomorphism.get(key));
			}
		}

		return result;
	}

	public static List<Homomorphism> createHomomorphisms(InstanceSchema instanceSchema, ConstraintElementGroup constraintElementGroup) {

		List<Homomorphism> homomorphisms = new ArrayList<Homomorphism>();
		homomorphisms.add(new Homomorphism());

		for (RelationName relationName : constraintElementGroup.keySet()) {
			for (int pos = 0; pos < constraintElementGroup.getSizeOf(relationName); pos++) {
				char[] variables = constraintElementGroup.getVariablesOf(relationName, pos);

				List<Fait> faits = instanceSchema.get(relationName);

				List<Homomorphism> tmpHomomorphisms = new ArrayList<Homomorphism>();
				for (Fait fait : faits) {

					Homomorphism homomorphism = new Homomorphism();
					for (int i = 0; i < variables.length; i++) {
						char variable = variables[i];
						char value = fait.get(i);

						homomorphism.put(variable, value);
					}

					tmpHomomorphisms.add(homomorphism);
				}

				List<Homomorphism> mergingList = new ArrayList<>();

				/* Merging the 2 lists */
				for (Homomorphism h1 : homomorphisms) {
					for (Homomorphism h2 : tmpHomomorphisms) {

						Homomorphism result = h1.merge(h2);
						if (result != null) {
							mergingList.add(result);
						}
					}
				}

				/* Copie de la nouvelle liste dans homomorphisms */
				homomorphisms = mergingList;
			}

		}

		return homomorphisms;
	}

	public static Homomorphism createHomomorphism(char[] variables, Fait fait) {

		Homomorphism homomorphism = new Homomorphism();

		for (int i = 0; i < variables.length; i++) {
			Character variable = variables[i];
			Character value = fait.get(i);

			if (!homomorphism.values.containsKey(variable)) {
				homomorphism.values.put(variable, value);
			} else if (!homomorphism.values.get(variable).equals(value)) {
				return null;
			}
		}

		return homomorphism;
	}
	
	@Override
	public String toString(){
		return values.toString();
	}
}
