package Servicios;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import Grafo.Arco;
import Grafo.Grafo;

public class Dijkstra { //TODO limapiar prints
	private static int metric = 0;
	
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
				metric++;
				// System.out.println("c=" + current + " / v= " + v);
				// aca contar las veces que entra, esto seria la metrica
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
	
	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> solve(Grafo<Integer> g){
		HashMap<Integer, Integer> bestSolution = null;
		int bestDistance = Integer.MAX_VALUE;
		for (Integer v : g.getVertices()) {
			HashMap<Integer, Integer> currentSolution = Dijkstra.dijkstraVertex(g, v);
			int currentDistance = 0;
			boolean valid = true;
			for (Map.Entry<Integer, Integer> entry : currentSolution.entrySet()) {
				metric++;
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
		SimpleEntry<HashSet<Arco<Integer>>, Integer> arcSolution = new SimpleEntry<>(new HashSet<>(bestSolution.size()) , metric);
		bestSolution.forEach((k,v) -> {if (v != null) arcSolution.getKey().add(g.obtenerArco(k, v));});
		return arcSolution; // aca en vez de pasar la distancia pasamos la metrica
	}
}
