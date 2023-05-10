package Servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import Grafo.Grafo;

public class ServicioCaminos {

	private Grafo<?> grafo;
	private int origen;
	private int destino;
	private int lim;
	private int time;
	private HashMap<Integer, HashSet<Integer>> arcos;
	
	// Servicio caminos
	public ServicioCaminos(Grafo<?> grafo, int origen, int destino, int lim) {
		this.grafo = grafo;
		this.origen = origen;
		this.destino = destino;
		this.lim = lim;
		this.arcos = new HashMap<>();
	}

	public List<List<Integer>> caminos() {
		/* time = 1;
		this.vertices = new HashMap<>();
		for (Iterator<Integer> it = grafo.obtenerVertices(); it.hasNext();) {//mover a constructor
			vertices.put(it.next(), new VertexInfo());
		} */
		List<List<Integer>> roads = new ArrayList<>();
		List<Integer> aux = new ArrayList<>();
		caminosDFS(origen, roads, aux);
		return roads;
	}


	private void caminosDFS(Integer v, List<List<Integer>> roads, List<Integer> caminoActual){ //O(v*a) v: vertives y a: arcos
		if (v == destino && !caminoActual.isEmpty()) { //si el origen y destino son iguales lo agregaba y no deberia. Asi quedo arreglado
			caminoActual.add(v);
			//System.out.println(caminoActual);
			roads.add(new ArrayList<>(caminoActual));
		} else {
			caminoActual.add(v);
		}
		arcos.putIfAbsent(v, new HashSet<>());
		Iterator<Integer> itAdjacents  = grafo.obtenerAdyacentes(v);
		if (caminoActual.size() < lim) {			//¿< o <=?
			while (itAdjacents.hasNext()) {
				Integer Adjacent = itAdjacents.next();
				if (!arcos.get(v).contains(Adjacent)) {
					arcos.get(v).add(Adjacent);
					caminosDFS(Adjacent, roads, caminoActual);
					caminoActual.remove(caminoActual.size()-1);
					arcos.get(v).remove(Adjacent);
				} else {
					//cyclical = true;
				}
			}
		}
		return;
	}
}