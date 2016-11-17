package fr.univlille1.m2iagl.durey.model;

import java.util.HashMap;
import java.util.Map;

public class Homomorphism {

	private Map<Character, Character> values;
	
	public Homomorphism(){
		values = new HashMap<Character, Character>();
	}
	
	public boolean containsVariable(char variable){
		return values.containsKey(variable);
	}
	
	public char get(char variable){
		return values.get(variable);
	}
	
	public void put(Character variable, Character value){
		values.put(variable, value);
	}
	
	public boolean isCorrectValue(Character variable, Character value){
		
		if(!values.containsKey(variable))
			return true;
		return values.get(variable).equals(value);
	}
	
	public static Homomorphism createHomomorphism(char [] variables, InstanceRelation instanceRelation){
				
		Homomorphism homomorphism = new Homomorphism();
		
		for(int i=0;i<variables.length;i++){
			Character variable = variables[i];
			Character value = instanceRelation.get(i);
			
			if(!homomorphism.values.containsKey(variable)){
				homomorphism.values.put(variable,  value);
			} else if(!homomorphism.values.get(variable).equals(value)){
				return null;
			}
		}
		
		return homomorphism;
	}
	
	public String toString(){
		return values.toString();
	}
}
