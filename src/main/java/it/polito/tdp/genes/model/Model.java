package it.polito.tdp.genes.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Map<String,Genes> idMap;
	private SimpleWeightedGraph<Genes,DefaultWeightedEdge> grafo;
	private List<Genes> vertici;
	
	public Model() {
		dao= new GenesDao();
		idMap= new HashMap<>();
		this.vertici= this.dao.getVertici(idMap);
		for(Genes g : this.vertici) {
			idMap.put(g.getGeneId(), g);
		}
	}
	
	public void creaGrafo() {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		//aggiungo vertici
		Graphs.addAllVertices(this.grafo, vertici);
		//aggiungo archi
		List<Arco> archi= this.dao.getArchi(idMap);
		for(Arco a : archi) {
			if(a.getG1().getChromosome()==a.getG2().getChromosome()) {
				double peso= a.getPeso()*2;
				Graphs.addEdgeWithVertices(this.grafo, a.getG1(), a.getG2(), Math.abs(peso));
			}
			else {
				Graphs.addEdgeWithVertices(this.grafo, a.getG1(), a.getG2(), Math.abs(a.getPeso()));
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Genes> getVertici(){
		return this.vertici;
	}
	
	public List<Adiacenti> getAdiacenti(Genes selezionato){
		List<Genes> vicini = Graphs.neighborListOf(this.grafo, selezionato);
		List<Adiacenti> result= new LinkedList<>();
		for(Genes g : vicini) {
			Adiacenti a = new Adiacenti(g.getGeneId(),this.grafo.getEdgeWeight(this.grafo.getEdge(selezionato, g)));
			result.add(a);
		}
		Collections.sort(result);
		return result;
	}
}
