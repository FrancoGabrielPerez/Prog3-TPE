package Servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Grafo.Grafo;

public class ServicioDFS {

	private HashMap<Integer, VertexInfo> vertices;
	private Grafo<?> grafo;
	private int time;

	public ServicioDFS(Grafo<?> grafo) {
		this.grafo = grafo;
	}
	
	@Override
	public String toString() {
		return "vertices: " + vertices;
	}

	public List<Integer> dfsForest() {
		time = 1;
		this.vertices = new HashMap<>();
		for (Iterator<Integer> it = grafo.obtenerVertices(); it.hasNext();) {
			vertices.put(it.next(), new VertexInfo());
		}
		List<Integer> discoveryOrder = new ArrayList<>();
		for (Integer vertex : vertices.keySet()) {
			if (!vertices.get(vertex).isVisited()) {
				discoveryOrder.addAll(DFS(vertex));
			}
		}
		return discoveryOrder;
	}

	private List<Integer> DFS(Integer v){
		List<Integer> discoveryOrder = new ArrayList<>();
		discoveryOrder.add(v);
		vertices.get(v).d = time;
		time++;
		Iterator<Integer> itAdjacents  = grafo.obtenerAdyacentes(v);
		while (itAdjacents.hasNext()) {
			Integer Adjacent = itAdjacents.next();
			if (!vertices.get(Adjacent).isVisited()) {
				discoveryOrder.addAll(DFS(Adjacent));
			} 
		}
		vertices.get(v).f = time;
		time++;
		return discoveryOrder;
	}
}