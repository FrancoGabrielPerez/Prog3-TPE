package Servicios;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import Grafo.Grafo;

public class Dijkstra {
	
	static public HashMap<Integer, Integer> DijkstraPaths(Grafo<Integer> g, Integer origin){
		HashMap<Integer, Integer> distance = new HashMap<>();
		HashMap<Integer, Integer> toVisit = new HashMap<>();
		HashMap<Integer, Integer> parent = new HashMap<>();
		g.getVertices().forEach(v -> {distance.put(v, Integer.MAX_VALUE); toVisit.put(v, Integer.MAX_VALUE); parent.put(v, null);});
		/* for (Iterator<Integer> it = g.obtenerVertices(); it.hasNext();) {
			Integer v = it.next();
			dist.put(v, Integer.MAX_VALUE);
			parent.put(v, null);
		} */
		distance.put(origin, 0);
		toVisit.put(origin, 0);
		while (!toVisit.isEmpty()) {
			// System.out.println(distance);
			// System.out.println(parent);
			final int current = Collections.min(toVisit.entrySet(), Map.Entry.<Integer, Integer>comparingByValue()).getKey();
			g.getAdjVertices(current).forEach(v -> {
				// System.out.println("c=" + current + " / v= " + v);
				int currentDist = distance.get(current) + g.obtenerArco(current, v).getEtiqueta();
				if (currentDist < distance.get(v)) {
					distance.put(v, currentDist);
					toVisit.put(v, currentDist);
					parent.put(v, current);
				}
			});
			toVisit.remove(current);
		}
		return parent;
	}
}
