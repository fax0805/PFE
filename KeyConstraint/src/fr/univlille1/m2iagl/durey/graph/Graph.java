package fr.univlille1.m2iagl.durey.graph;

import java.util.ArrayList;
import java.util.List;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.alg.cycle.TarjanSimpleCycles;
import org.jgrapht.graph.DefaultDirectedGraph;

import fr.univlille1.m2iagl.durey.model.constraint.InvisibleToInvisibleConstraint;

public class Graph {
	
	DefaultDirectedGraph<String, DefaultEdge> graph;
	
	List<InvisibleToInvisibleConstraint> invisibleToInvisibleConstraints;
	
	List<List<String>> cycles;
	
	
	public Graph(List<InvisibleToInvisibleConstraint> invisibleToInvisibleConstraints){
		graph = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		this.invisibleToInvisibleConstraints = invisibleToInvisibleConstraints;
	}
	
	public void run(){
		
		for(InvisibleToInvisibleConstraint invisibleToInvisibleConstraint : invisibleToInvisibleConstraints){
			String leftRelationName = invisibleToInvisibleConstraint.getLeftRelationName().getName();
			String rightRelationName = invisibleToInvisibleConstraint.getRightRelationName().getName();
			
			addEdge(leftRelationName, rightRelationName);
		}
		
		TarjanSimpleCycles<String, DefaultEdge> trajanSimpleCycles = new TarjanSimpleCycles<>(graph);
		cycles = trajanSimpleCycles.findSimpleCycles();
	}
	
	public List<List<String>> getAllCycles(){
		return cycles;
	}
	
	public List<String> getBiggestCycle(){
		if(cycles.size() == 0)
			return new ArrayList<String>();
		
		
		List<String> biggestCycle = cycles.get(0);
		for(int i=1;i<cycles.size();i++){
			List<String> currentCycle = cycles.get(i);
			if(currentCycle.size() > biggestCycle.size())
				biggestCycle = currentCycle;
		}
		
		return biggestCycle;
	}
	
	private void addEdge(String from, String to){
		if(!graph.containsVertex(from))
			graph.addVertex(from);
		if(!graph.containsVertex(to))
			graph.addVertex(to);
		
		if(!graph.containsEdge(from, to))
			graph.addEdge(from, to);
	}
	

}
