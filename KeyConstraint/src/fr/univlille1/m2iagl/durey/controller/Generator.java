package fr.univlille1.m2iagl.durey.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univlille1.m2iagl.durey.model.Relation;
import fr.univlille1.m2iagl.durey.model.RelationName;
import fr.univlille1.m2iagl.durey.model.Schema;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElement;
import fr.univlille1.m2iagl.durey.model.constraint.ConstraintElementGroup;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToInvisibleConstraint;
import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToVisibleConstraint;
import fr.univlille1.m2iagl.durey.model.constraint.VisibleToInvisibleConstraint;

public class Generator {

	private int nbRelations;
	private int relationArity;
	private int nbConstraint;
	private int constraintSize;
	private int nbKeyConstraints;
	private double visibleRelation;
	private int biggestCycleSize;

	private Schema schema;
	private List<InvisibleToVisibleConstraint> invisibleToVisible;
	private List<VisibleToInvisibleConstraint> visibleToInvisible;
	private List<InvisibleToInvisibleConstraint> keyConstraints;

	private List<Character> alreadyUsed;
	private char current = 'a';

	public Generator(int nbRelations, int relationArity, int nbConstraint, int constraintSize, int nbKeyConstraints, double visibleRelation, int biggestCycleSize){
		this.nbRelations = nbRelations;
		this.relationArity = relationArity;
		this.nbConstraint = nbConstraint;
		this.constraintSize = constraintSize;
		this.visibleRelation = visibleRelation;
		this.nbKeyConstraints = nbKeyConstraints;
		this.biggestCycleSize = biggestCycleSize;

		this.invisibleToVisible = new ArrayList<>();
		this.visibleToInvisible = new ArrayList<>();
		keyConstraints = new ArrayList<>();

		this.alreadyUsed = new ArrayList<Character>();

		this.schema = new Schema();
	}

	public void generate(){
		addRelations();
		addConstraints();
		addKeyConstraints();

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

	private void addKeyConstraints(){

		createAndAddKeyConstraintCycle();

		System.out.println("Keys : " + keyConstraints);
		for(int constraintInd=0;constraintInd<nbKeyConstraints-biggestCycleSize;constraintInd++){
			keyConstraints.add(createKeyConstraint());
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

	private InvisibleToInvisibleConstraint createKeyConstraint(){
		List<RelationName> invisibleRelations = new ArrayList<>(schema.invisibleKeySet());
		Random r = new Random();
		RelationName relationName1 = invisibleRelations.get(r.nextInt(invisibleRelations.size()));
		RelationName relationName2 = invisibleRelations.get(r.nextInt(invisibleRelations.size()));

		int leftRelationArity = schema.getInvisibleRelation(relationName1).getArity();
		int rightRelationArity = schema.getInvisibleRelation(relationName2).getArity();

		char[] c1 = new char[leftRelationArity];

		int nbColumnsExported = 1;
		List<Integer> leftPosOfColumnsExported = getNbRandomPos(nbColumnsExported, leftRelationArity);

		for(int i=0;i<leftRelationArity;i++){
			c1[i] = current++;
		}

		char[] c2 = new char[rightRelationArity];

		List<Integer> rightPosOfColumnsExported = getNbRandomPos(nbColumnsExported, rightRelationArity);

		for(int i=0;i<rightRelationArity;i++){
			if(rightPosOfColumnsExported.contains(i)){
				c2[i] = c1[leftPosOfColumnsExported.get(r.nextInt(leftPosOfColumnsExported.size()))];
			} else {
				c2[i] = current++;
			}
		}

		return new InvisibleToInvisibleConstraint(relationName1, c1, relationName2, c2);

	}

	private void createAndAddKeyConstraintCycle(){
		List<RelationName> invisibleRelations = new ArrayList<>(schema.invisibleKeySet());
		Random r = new Random();
		RelationName previousRelationName = invisibleRelations.get(r.nextInt(invisibleRelations.size()));

		int previousRelationArity = schema.getInvisibleRelation(previousRelationName).getArity();

		RelationName relationName = null;
		int relationArity;

		List<RelationName> used = new ArrayList<>();
		used.add(previousRelationName);


		for(int i=0;i<=biggestCycleSize;i++){
			if(i != biggestCycleSize -1){
				while(relationName == null || used.contains(relationName))
					relationName = invisibleRelations.get(r.nextInt(invisibleRelations.size()));
			} else
				relationName = used.get(0);

			relationArity = schema.getInvisibleRelation(relationName).getArity();

			char[] c1 = new char[previousRelationArity];

			int nbColumnsExported = 1;
			List<Integer> leftPosOfColumnsExported = getNbRandomPos(nbColumnsExported, previousRelationArity);

			for(int j=0;j<previousRelationArity;j++){
				c1[j] = current++;
			}

			char[] c2 = new char[relationArity];

			List<Integer> rightPosOfColumnsExported = getNbRandomPos(nbColumnsExported, relationArity);

			for(int j=0;j<relationArity;j++){
				if(rightPosOfColumnsExported.contains(j)){
					c2[j] = c1[leftPosOfColumnsExported.get(r.nextInt(leftPosOfColumnsExported.size()))];
				} else {
					c2[j] = current++;
				}
			}

			keyConstraints.add(new InvisibleToInvisibleConstraint(previousRelationName, c1, relationName, c2));

			used.add(relationName);
			previousRelationName = new RelationName(relationName.getName());
			previousRelationArity = relationArity;

		}
	}

	private List<Integer> getNbRandomPos(int cpt, int limit){
		Random r = new Random();
		List<Integer> list = new ArrayList<Integer>();
		for(int i=0;i<cpt;i++){
			int newPos = -1;
			while(newPos == -1 || list.contains(newPos)){
				newPos = r.nextInt(limit);
			}
			list.add(newPos);
		}

		return list;
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

	public List<InvisibleToVisibleConstraint> getInvisibleToVisible(){
		return invisibleToVisible;
	}

	public List<VisibleToInvisibleConstraint> getVisibleToInvisible(){
		return visibleToInvisible;
	}	

	public List<InvisibleToInvisibleConstraint> getKeyConstraints(){
		return keyConstraints;
	}
}
