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
		
		public Solutions(int capacity, int cota) {
			bestSolution = new HashSet<>(capacity);
			bestDistance = cota; //TODO dejas en max value
			currentSolution = new HashSet<>(capacity);
			currentDistance = 0;
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

	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> bactrackingFactorial(Grafo<Integer> grafo, int cota) {
		LinkedList<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next());
		}
		metric = 0;
		Solutions s = new Solutions(grafo.cantidadArcos(), cota);
		internalBacktrackingFactorial(grafo.getVertices(), arcos, s);
		return new SimpleEntry<HashSet<Arco<Integer>>, Integer>(s.bestSolution, metric);
	}

	static private void internalBacktrackingFactorial(Set<Integer> vertices, LinkedList<Arco<Integer>> arcos, Solutions s){
		metric++;
		if (isValid(s.currentSolution, vertices)) {
			if (s.currentDistance <= s.bestDistance) {
				s.bestSolution = new HashSet<>(s.currentSolution);
				s.bestDistance = s.currentDistance;
			}
		} else {			
			for (int i = 0; i < arcos.size(); i++) {
				Arco<Integer> candidate = arcos.removeFirst();
				s.currentSolution.add(candidate);
				s.currentDistance += candidate.getEtiqueta();
				if ((s.currentDistance <= s.bestDistance) && (s.currentSolution.size() < vertices.size())) {
					internalBacktrackingFactorial(vertices, arcos, s);
				}
				s.currentDistance -= candidate.getEtiqueta();
				s.currentSolution.remove(candidate);
				arcos.addLast(candidate);
			}
		}
	}
	
	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> backtrackingBinary(Grafo<Integer> grafo, int cota) {
		LinkedList<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next());
		}
		metric = 0;
		Solutions s = new Solutions(grafo.cantidadArcos(), cota);
		UnionFind uFind = new UnionFind(grafo.getVertices());
		internalBacktrackingBinary(grafo.getVertices(), arcos, s, uFind);
		return new SimpleEntry<HashSet<Arco<Integer>>, Integer>(s.bestSolution, metric);
	}	
	
	static private void internalBacktrackingBinary(Set<Integer> vertices, LinkedList<Arco<Integer>> arcos, Solutions s, UnionFind uFind) { 
		metric++;
		if (arcos.isEmpty() || (s.currentSolution.size() == vertices.size()-1)) {
			if (isValid(s.currentSolution, vertices)) {
				s.bestSolution = new HashSet<>(s.currentSolution);
				s.bestDistance = s.currentDistance;
			}
		} else {
			Arco<Integer> candidate = arcos.removeFirst();
			// System.out.println(candidate);
			// System.out.println(uFind.find(candidate.getVerticeOrigen()));
			// System.out.println(uFind.find(candidate.getVerticeDestino()));
			if (uFind.find(candidate.getVerticeOrigen()) != uFind.find(candidate.getVerticeDestino())) {
				UnionFind newuFind = (UnionFind) uFind.clone();
				newuFind.union(candidate.getVerticeOrigen(), candidate.getVerticeDestino());
				s.currentSolution.add(candidate);
				s.currentDistance += candidate.getEtiqueta();
				if (s.currentDistance <= s.bestDistance){
					internalBacktrackingBinary(vertices, arcos, s, newuFind);
				}
				s.currentDistance -= candidate.getEtiqueta();
				s.currentSolution.remove(candidate);
			}
			internalBacktrackingBinary(vertices, arcos, s, uFind);
			arcos.addFirst(candidate);
		}
	}
}
