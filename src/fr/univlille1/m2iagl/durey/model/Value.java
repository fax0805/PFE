package fr.univlille1.m2iagl.durey.model;

public class Value {
	
	private static char newVariable = 'b';
	
	public static char getNewValue(){
		return newVariable++;
	}

}
