package fr.univlille1.m2iagl.durey.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.RelationName;
import fr.univlille1.m2iagl.durey.model.Schema;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElementGroup;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToVisibleConstraint;
import fr.univlille1.m2iagl.durey.model.constraint.VisibleToInvisibleConstraint;

public class Generator {

	private int nbRelations;
	private int relationArity;
	private int nbConstraint;
	private int constraintSize;
	private double visibleRelation;


	private Schema schema;
	private List<InvisibleToVisibleConstraint> invisibleToVisible;
	private List<VisibleToInvisibleConstraint> visibleToInvisible;

	private List<Character> alreadyUsed;
	private char current = 'a';

	public Generator(int nbRelations, int relationArity, int nbConstraint, int constraintSize, double visibleRelation){
		this.nbRelations = nbRelations;
		this.relationArity = relationArity;
		this.nbConstraint = nbConstraint;
		this.constraintSize = constraintSize;
		this.visibleRelation = visibleRelation;

		this.invisibleToVisible = new ArrayList<InvisibleToVisibleConstraint>();
		this.visibleToInvisible = new ArrayList<VisibleToInvisibleConstraint>();

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
		for(int constraintInd=0;constraintInd<nbConstraint/2;constraintInd++){
			InvisibleToVisibleConstraint invisibleToVisibleConstraint = createInvisibleToVisibleConstraint();
			invisibleToVisible.add(invisibleToVisibleConstraint);

			visibleToInvisible.add(createReverseConstraints(invisibleToVisibleConstraint));

		}
	}

	private InvisibleToVisibleConstraint createInvisibleToVisibleConstraint(){
		char[][][] leftVars = new char[constraintSize][1][relationArity];

		RelationName[] leftRelationNames = new RelationName[constraintSize];

		for(int i=0;i<constraintSize;i++){			
			leftRelationNames[i] = getRandomInvisibleRelationName(schema);
			leftVars[i][0] = createConstraintsVars(relationArity);
		}

		RelationName rightRelationName = getRandomVisibleRelationName(schema);

		char [] rightVars = createConstraintsVars(relationArity);

		return new InvisibleToVisibleConstraint(leftRelationNames, leftVars, rightRelationName, rightVars);
	}

	private VisibleToInvisibleConstraint createReverseConstraints(InvisibleToVisibleConstraint invisibleToVisible){


		RelationName leftRelationName = invisibleToVisible.getRightRelationName();
		char[] leftVars = invisibleToVisible.getRightVariables();

		char [][][] rightVars = new char[constraintSize][1][relationArity];
		
		List<RelationName> rightRelationNames = new ArrayList<RelationName>();
		List<RelationName> relationNames = new ArrayList<RelationName>(invisibleToVisible.getLeftConstraintElementGroup().keySet());

		ConstraintElementGroup constraintElementGroup = invisibleToVisible.getLeftConstraintElementGroup();
		
		int cpt = 0;
		for(int i=0;i<relationNames.size();i++){
			RelationName relationName = relationNames.get(i);
			ConstraintElement[] constraintElement = constraintElementGroup.get(relationName);
			for(int j=0;j<constraintElement.length;j++){
				
				rightRelationNames.add(relationName);
				rightVars[cpt][0] = invisibleToVisible.getLeftConstraintElementGroup().getVariablesOf(relationName, j);
				
				cpt++;
			}
		}
		
		return new VisibleToInvisibleConstraint(leftRelationName, leftVars, rightRelationNames.toArray(new RelationName[rightRelationNames.size()]), rightVars);
	}

	private char[] createConstraintsVars(int size){
		char [] constraintVars = new char[size];
		Random random = new Random();

		for(int i=0;i<size;i++){
			char c = ' ';
			if(random.nextDouble() < 0.8 && alreadyUsed.size() != 0){
				// Utilisation d'une ancienne, mais pas déjà utilisé
				boolean alreadyInTheConstraint = true;
			
				while(alreadyInTheConstraint){
					c = alreadyUsed.get(random.nextInt(alreadyUsed.size()));
					
					alreadyInTheConstraint = false;
					for(int j=0;j<i;j++){
						if(c == constraintVars[j])
							alreadyInTheConstraint = true;
					}
				}
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

	public List<InvisibleToVisibleConstraint> getInvisibleToVisible(){
		return invisibleToVisible;
	}

	public List<VisibleToInvisibleConstraint> getVisibleToInvisible(){
		return visibleToInvisible;
	}	
}
