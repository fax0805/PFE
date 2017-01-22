package fr.univlille1.m2iagl.durey.model;

public class Value {
	
	public static char basicVar = 'a';
	
	private static char newVariable = 'b';
	
	public static char getNewValue(){
		return newVariable++;
	}

}
