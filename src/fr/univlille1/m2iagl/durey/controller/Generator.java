package fr.univlille1.m2iagl.durey.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.RelationName;
import fr.univlille1.m2iagl.durey.model.Schema;
import fr.univlille1.m2iagl.durey.model.constraint.Constraint;
import fr.univlille1.m2iagl.durey.model.constraint.ImpliesConstraint;

public class Generator {

	private int nbRelations;
	private int relationArity;
	private int nbConstraint;
	private int constraintSize;
	private double visibleToInvisibleConstraint;
	private double visibleRelation;
	
	
	private Schema schema;
	private List<Constraint> invisibleToVisible;
	private List<Constraint> visibleToInvisible;
	
	private List<Character> alreadyUsed;
	private char current = 'a';
	
	public Generator(int nbRelations, int relationArity, int nbConstraint, int constraintSize, double visibleRelation, double visibleToInvisibleConstraint){
		this.nbRelations = nbRelations;
		this.relationArity = relationArity;
		this.nbConstraint = nbConstraint;
		this.constraintSize = constraintSize;
		this.visibleToInvisibleConstraint = visibleToInvisibleConstraint;
		this.visibleRelation = visibleRelation;
		
		this.invisibleToVisible = new ArrayList<Constraint>();
		this.visibleToInvisible = new ArrayList<Constraint>();
		
		this.alreadyUsed = new ArrayList<Character>();
		
		this.schema = new Schema();
	}
	
	public void generate(){
		addRelations();
		addConstraints();
		
	}
	
	private void addRelations(){
		for(int relationInd=0;relationInd<nbRelations;relationInd++){
			
			RelationName relationName = Factory.getNewRelationName();
			Relation relation = new Relation(relationName, relationArity);

			Random random = new Random();
			if(random.nextDouble() < visibleRelation){
				schema.addVisibleRelation(relationName, relation);
			} else {
				schema.addInvisibleRelation(relationName, relation);
			}
		}
	}
	
	private void addConstraints(){
		for(int constraintInd=0;constraintInd<nbConstraint;constraintInd++){
			Random random = new Random();
			if(random.nextFloat() < visibleToInvisibleConstraint){
				visibleToInvisible.add(createVisibleToInvisibleConstraint());
			} else {
				Constraint constraint = createInvisibleToVisibleConstraint();
				invisibleToVisible.add(constraint);
			}
		}
	}
	
	private Constraint createInvisibleToVisibleConstraint(){
		char[][][] leftVars = new char[constraintSize][1][relationArity];
		
		RelationName[] leftRelationNames = new RelationName[constraintSize];
		
		for(int i=0;i<constraintSize;i++){			
			leftRelationNames[i] = getRandomInvisibleRelationName(schema);
			leftVars[i][0] = createConstraintsVars(relationArity);
		}
		
		RelationName rightRelationName = getRandomVisibleRelationName(schema);
				
		char [] rightVars = createConstraintsVars(relationArity);
		
		return new ImpliesConstraint(leftRelationNames, leftVars, rightRelationName, rightVars);

		
	}
	
	private Constraint createVisibleToInvisibleConstraint(){
		
		char[][][] leftVars = new char[constraintSize][1][relationArity];
		
		RelationName[] leftRelationNames = new RelationName[constraintSize];
		
		for(int i=0;i<constraintSize;i++){
			
			leftRelationNames[i] = getRandomVisibleRelationName(schema);
			leftVars[i][0] = createConstraintsVars(relationArity);
		}
		
		RelationName rightRelationName = getRandomInvisibleRelationName(schema);
				
		char [] rightVars = createConstraintsVars(relationArity);
		
		return new ImpliesConstraint(leftRelationNames, leftVars, rightRelationName, rightVars);
	}
	
	private char[] createConstraintsVars(int size){
		char [] constraintVars = new char[size];
		Random random = new Random();
		
		for(int i=0;i<size;i++){
			char c;
			if(random.nextDouble() < 0.8 && alreadyUsed.size() != 0){
				// Utilisation d'une ancienne
				c = alreadyUsed.get(random.nextInt(alreadyUsed.size()));
				
			} else {
				// Creating d'une nouvelle variable dans le tableau
				c = current++;
				alreadyUsed.add(c);
			}
			
			constraintVars[i] = c;
		}
		return constraintVars;
		
	}
	
	private RelationName getRandomInvisibleRelationName(Schema schema){
		List<RelationName> list = new ArrayList<RelationName>(schema.invisibleKeySet());
		
		Random random = new Random();
		
		return list.get(random.nextInt(list.size()));
		
	}
	
	private RelationName getRandomVisibleRelationName(Schema schema){
		List<RelationName> list = new ArrayList<RelationName>(schema.visibleKeySet());
		
		Random random = new Random();
		
		return list.get(random.nextInt(list.size()));
	}
	
	
	public Schema getSchema(){
		return schema;
	}
	
	public List<Constraint> getInvisibleToVisible(){
		return invisibleToVisible;
	}
	
	public List<Constraint> getVisibleToInvisible(){
		return visibleToInvisible;
	}

}
