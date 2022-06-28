package it.polito.tdp.yelp.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	private Graph<Review, DefaultWeightedEdge> grafo;
	private YelpDao dao;
	private Map<Integer, Review> idMap;
	
	public Model() {
		dao = new YelpDao();
		idMap = new HashMap<Integer, Review>();
	}
	
	public void creaGrafo(String citta, String locale) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		//doppio ciclo for per creare gli archi verificando che la prima review sia minore della seconda review in termini di data
		List<Review> vertici = dao.getReviews(locale);
		for(Review r1 : vertici) {
			for(Review r2 : vertici) {
				if(r1.getDate().isBefore(r2.getDate())) {
					long peso = ChronoUnit.DAYS.between(r1.getDate(), r2.getDate());
					if(peso!=0)
						Graphs.addEdgeWithVertices(this.grafo, r1, r2, peso);
				}
			}
		}
		
	}
	
	public List<String> getArchiUscenti(){
		int numArchiUscentiMax = 0;
		List<String> archiUscenti = new ArrayList<>();
		for(Review r : this.grafo.vertexSet()) {
			 if(this.grafo.outDegreeOf(r)>=numArchiUscentiMax) {
				 numArchiUscentiMax=this.grafo.outDegreeOf(r);
			}
		}
		for(Review r : this.grafo.vertexSet()) {
			 if(this.grafo.outDegreeOf(r)==numArchiUscentiMax) {
				archiUscenti.add(r.getReviewId()+" -----> NUM ARCHI USCENTI: "+ numArchiUscentiMax);
			}
		}
		return archiUscenti;
	}
	
	public int nVertici(){
		return this.grafo.vertexSet().size();
	}
	
	public int nEdge(){
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getCitta(){
		return dao.getAllCity();
	}
	
	public List<Business> getLocali(String citta){
		List<Business> locali = new ArrayList<>(dao.getAllLocali(citta));
		Collections.sort(locali);
		return locali;
	}
}
