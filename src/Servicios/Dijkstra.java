package Servicios;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import Grafo.Grafo;

public class Dijkstra { //TODO limapiar prints
	
	static public HashMap<Integer, Integer> dijkstraVertex(Grafo<Integer> g, Integer origin){
		HashMap<Integer, Integer> distance = new HashMap<>(g.cantidadVertices());
		HashMap<Integer, Integer> parent = new HashMap<>(g.cantidadVertices());
		g.getVertices().forEach(v -> {distance.put(v, Integer.MAX_VALUE); parent.put(v, null);});
		HashMap<Integer, Integer> toVisit = new HashMap<>();
		g.getAdjVertices(origin).forEach(v -> {toVisit.put(v, Integer.MAX_VALUE);});
		distance.put(origin, 0);
		toVisit.put(origin, 0);
		while (!toVisit.isEmpty()) {
			// System.out.println(distance);
			// System.out.println(parent);
			int current = Collections.min(toVisit.entrySet(), Map.Entry.<Integer, Integer>comparingByValue()).getKey();
			for (Integer v : g.getAdjVertices(current)) {
				// System.out.println("c=" + current + " / v= " + v);
				int currentDist = distance.get(current) + g.obtenerArco(current, v).getEtiqueta();
				if (currentDist < distance.get(v)) {
					distance.put(v, currentDist);
					toVisit.put(v, currentDist);
					parent.put(v, current);
				}
			}
			toVisit.remove(current);
		}
		return parent;
	}
	
	static public SimpleEntry<HashMap<Integer, Integer>, Integer> dijkstraAll(Grafo<Integer> g){
		HashMap<Integer, Integer> bestSolution = null;
		int bestDistance = Integer.MAX_VALUE;
		for (Integer v : g.getVertices()) {
			HashMap<Integer, Integer> currentSolution = Dijkstra.dijkstraVertex(g, v);
			int currentDistance = 0;
			boolean valid = true;
			for (Map.Entry<Integer, Integer> entry : currentSolution.entrySet()) {
				if (entry.getKey() != v) {
					if (entry.getValue() != null) {
						currentDistance += g.obtenerArco(entry.getValue(), entry.getKey()).getEtiqueta();
						if (currentDistance == -1) {
							valid = false;
						}
					} else {
						valid = false;
					}
				}
			}
			// System.out.println("best: " + bestDistance);
			// System.out.println("current: " + currentDistance);
			if (valid && currentDistance < bestDistance) {
				bestDistance = currentDistance;
				bestSolution = currentSolution;
			}
		}
		return new SimpleEntry<>(bestSolution, bestDistance);
	}
	
	/* static public SimpleEntry<HashMap<Integer, Integer>, Integer> dijkstraAll(Grafo<Integer> g){
		var bestSolution = new Object(){HashMap<Integer, Integer> solution; int distance = Integer.MAX_VALUE;};
		g.getVertices().forEach(v -> {
			var currentSolution = new Object(){ HashMap<Integer, Integer> solution; int distance = 0; boolean valid = true;};
			currentSolution.solution = Dijkstra.dijkstraVertex(g, v);
			//System.out.println(currentSolution);
			currentSolution.solution.entrySet().forEach(e -> {
				if (e.getKey() != v) {
					if (e.getValue() != null) {
						currentSolution.distance += g.obtenerArco(e.getValue(), e.getKey()).getEtiqueta();
						if (currentSolution.distance == -1) {
							currentSolution.valid = false;
						}
					} else {
						currentSolution.valid = false;
					}
				}
			});
			// System.out.println("best: " + bestSolution.distance);
			// System.out.println("current: " + currentSolution.distance);
			if ((currentSolution.valid) && (currentSolution.distance < bestSolution.distance)) {
				bestSolution.distance = currentSolution.distance;
				bestSolution.solution = currentSolution.solution;
			}
		});
		return new SimpleEntry<>(bestSolution.solution, bestSolution.distance);
	} */
}
