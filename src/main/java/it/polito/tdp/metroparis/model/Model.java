package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	private Graph<Fermata, DefaultEdge> grafo;
	Map<Integer, Fermata> fermateIdMap;
	List<Fermata> fermate;
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		MetroDAO dao = new MetroDAO();
		
		Graphs.addAllVertices(this.grafo, fermate);
		

		//METODO 1: itero su ogni coppia di vertici --> Troppo oneroso! (5 min)
//		for(Fermata p : fermate) {
//			for(Fermata a : fermate) {
//				if(dao.isFermateConnesse(p, a)) {
//					this.grafo.addEdge(p, a);
//				}
//			}
//		}
		
		//METODO 2: dato ciascun vertice trovo i vertici adiacenti
		//VARIANTE 2a: il DAO restituisce un elenco di ID numerici
//		for(Fermata p : fermate) {
//			List<Integer> idConnesse = dao.getIdFermateConnesse(p);
//			for(Integer id : idConnesse) {
//				Fermata arrivo = null;
//				for(Fermata f : fermate) {
//					if(f.getIdFermata() == id) {
//						arrivo = f;
//						break;
//					}
//				}
//				this.grafo.addEdge(p, arrivo);
//			}
//		}
		
		//VARIANTE 2b: il DAO restituisce un elenco di oggetti fermata
//		for(Fermata partenza : fermate) {
//			List<Fermata> arrivi = dao.getFermateConnesse(partenza);
//			for(Fermata arrivo : arrivi) {
//				this.grafo.addEdge(partenza, arrivo);
//			}
//		}
		
		//VARIANTE 2c: il DAO restituisce un elenco di id numerici, che converto in oggetti tramite 
		//una MAP<Integer,Fermata> - "Identity Map"
//		for(Fermata partenza : fermate) {
//			List<Integer> idConnesse = dao.getIdFermateConnesse(partenza);
//			for(int id : idConnesse) {
//				Fermata arrivo = fermateIdMap.get(id);
//				this.grafo.addEdge(partenza, arrivo);
//			}
//		}
		

		
		//METODO 3: faccio una sola query che mi restituisce le coppie di fermate da collegare
		//(variante preferita 3c: usare Identity Map)
		List<CoppiaId> fermateDaCollegare = dao.getAllFermateConnesse();
		
		for(CoppiaId coppia : fermateDaCollegare) {
			this.grafo.addEdge(fermateIdMap.get(coppia.getId_partenza()), fermateIdMap.get(coppia.getId_arrivo()));
		}
		
		
//		System.out.println(this.grafo);
//		System.out.println("Vertici = " + this.grafo.vertexSet().size());
//		System.out.println("Archi = " + this.grafo.edgeSet().size());
		
		visitaGrafo(fermate.get(0));
	}
	
	public Map<Fermata, Fermata> visitaGrafo(Fermata partenza) {
		GraphIterator<Fermata, DefaultEdge> visita = new BreadthFirstIterator<>(this.grafo, partenza);
		
		Map<Fermata, Fermata> alberoInverso = new HashMap<Fermata,Fermata>();
		alberoInverso.put(partenza, null);
		
		visita.addTraversalListener(new RegistraAlberoVisita(alberoInverso, this.grafo));
		while(visita.hasNext()) {
			Fermata f = visita.next();
			System.out.println(f);
		}
		
		return alberoInverso;
		
	}
	
	public List<Fermata> getFermate() {
		if(this.fermate==null) {
			MetroDAO dao = new MetroDAO();
			this.fermate = dao.getAllFermate();
			
			fermateIdMap= new HashMap<Integer, Fermata>();
			
			for(Fermata f : fermate) {
				fermateIdMap.put(f.getIdFermata(), f);
			}
		}
		
		return this.fermate;
	}
	
	public List<Fermata> calcolaPercorso(Fermata partenza, Fermata arrivo) {
		creaGrafo();
		Map<Fermata, Fermata> alberoInverso = visitaGrafo(partenza);
		
		List<Fermata> percorso = new ArrayList<Fermata>();
		Fermata corrente = arrivo;
		while(corrente != null) {
			percorso.add(0, corrente);
			corrente = alberoInverso.get(corrente);
		}
		return percorso;
	}
	
	
}
