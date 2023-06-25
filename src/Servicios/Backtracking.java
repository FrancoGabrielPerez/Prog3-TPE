package Servicios;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import Grafo.*;

public class Backtracking {
	private static int metric = 0;

	private static class Solutions {
		HashSet<Arco<Integer>> bestSolution, currentSolution;
		int bestDistance, currentDistance;
		
		public Solutions(int capacity) {
			bestSolution = new HashSet<>(capacity);
			bestDistance = Integer.MAX_VALUE;
			currentSolution = new HashSet<>(capacity);
			currentDistance = 0;
			metric = 0;
		}
	}
	
	static private boolean isValid(HashSet<Arco<Integer>> currentSolucion, Set<Integer> vertices) {
		if (currentSolucion.isEmpty()) {
			return false;
		}
		UnionFind uFind = new UnionFind(vertices); // Se crea una instancia de UnionFind del tamaño de cantidad de vertices del grafo
		for (Arco<Integer> a : currentSolucion)  { // Itera sobre los arcos de la solucion
				uFind.union(a.getVerticeOrigen(), a.getVerticeDestino()); // Hace el union sobre los vertices de ese arco
		}
		return uFind.numberOfSets() == 1; // Si la cantidad de conjuntos es 1 el grafo es conexo
	}

	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> bactrackingFactorial(Grafo<Integer> grafo) {
		LinkedList<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next());
		}
		Solutions s = new Solutions(grafo.cantidadArcos());
		internalBacktrackingFactorial(grafo.getVertices(), arcos, s);
		return new SimpleEntry<HashSet<Arco<Integer>>, Integer>(s.bestSolution, metric);
	}

	static private void internalBacktrackingFactorial(Set<Integer> vertices, LinkedList<Arco<Integer>> arcos, Solutions s){
		metric++;
		if (isValid(s.currentSolution, vertices)) {
			if (s.currentDistance < s.bestDistance) {
				s.bestSolution = new HashSet<>(s.currentSolution);
				s.bestDistance = s.currentDistance;
			}
		} else {			
			for (int i = 0; i < arcos.size(); i++) {
				Arco<Integer> candidate = arcos.removeFirst();
				s.currentSolution.add(candidate);
				s.currentDistance += candidate.getEtiqueta();
				if ((s.currentDistance < s.bestDistance) && (s.currentSolution.size() < vertices.size())) {
					internalBacktrackingFactorial(vertices, arcos, s);
				}
				s.currentDistance -= candidate.getEtiqueta();
				s.currentSolution.remove(candidate);
				arcos.addLast(candidate);
			}
		}
	}
	
	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> backtrackingBinary(Grafo<Integer> grafo) {
		LinkedList<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next());
		}
		Solutions s = new Solutions(grafo.cantidadArcos());
		internalBacktrackingBinary(grafo.getVertices(), arcos, s);
		return new SimpleEntry<HashSet<Arco<Integer>>, Integer>(s.bestSolution, metric);
	}	
	
	static private void internalBacktrackingBinary(Set<Integer> vertices, LinkedList<Arco<Integer>> arcos, Solutions s) { 
		metric++;
		if (arcos.isEmpty()) {
			if (isValid(s.currentSolution, vertices)) {
				if (s.currentDistance < s.bestDistance) {
					s.bestSolution = new HashSet<>(s.currentSolution);
					s.bestDistance = s.currentDistance;
				}
			}
		} else {
			Arco<Integer> candidate = arcos.removeFirst();
			internalBacktrackingBinary(vertices, arcos, s);
			s.currentSolution.add(candidate);
			s.currentDistance += candidate.getEtiqueta();
			if ((s.currentDistance < s.bestDistance) && (s.currentSolution.size() < vertices.size())){
				internalBacktrackingBinary(vertices, arcos, s);
			}
			s.currentDistance -= candidate.getEtiqueta();
			s.currentSolution.remove(candidate);
			arcos.addFirst(candidate);
		}
	}
}
