package Servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import Grafo.Grafo;

public class ServicioBFS {

	private HashMap<Integer, VertexInfo> vertices;
	private Grafo<?> grafo;
	private int time;
	
	public ServicioBFS(Grafo<?> grafo) {
		this.grafo = grafo;
	}
	
	@Override
	public String toString() {
		return "vertices: " + vertices;
	}

	public List<Integer> bfsForest() {
		time = 1;
		this.vertices = new HashMap<>();
		for (Iterator<Integer> it = grafo.obtenerVertices(); it.hasNext();) {
			vertices.put(it.next(), new VertexInfo());
		}
		List<Integer> discoveryOrder = new ArrayList<>();
		for (Integer vertex : vertices.keySet()) {
			if (!vertices.get(vertex).isVisited()) {
				discoveryOrder.addAll(BFS(vertex));
			}
		}
		return discoveryOrder;
	}

	private List<Integer> BFS(Integer v){
		List<Integer> discoveryOrder = new ArrayList<>();
		discoveryOrder.add(v);
		vertices.get(v).d = time;
		time++;
		for (int i = 0; i < discoveryOrder.size(); i++) {
			Iterator<Integer> itAdjacents  = grafo.obtenerAdyacentes(discoveryOrder.get(i));
			while (itAdjacents.hasNext()) {
				Integer Adjacent = itAdjacents.next();
				if (!vertices.get(Adjacent).isVisited()) {
					vertices.get(Adjacent).d = time;
					time++;
					discoveryOrder.add(Adjacent);
				}
			}
		}
		return discoveryOrder;
	}
}