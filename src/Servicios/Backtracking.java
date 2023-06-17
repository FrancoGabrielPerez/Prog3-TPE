package Servicios;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import Grafo.*;

public class Backtracking {
	private static int metric = 0;

	private static class Solutions{
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
	
	static private boolean isValid(HashSet<Arco<Integer>> currentSolucion, Set<Integer> vertices){
		if (currentSolucion.isEmpty()) {
			return false;
		}
		UnionFind UF = new UnionFind(vertices); // Se crea una instancia de UnionFind del tamaño de cantidad de vertices del grafo
		for (Arco<Integer> a : currentSolucion)  { // Itera sobre los arcos de la solucion
				UF.union(a.getVerticeOrigen(), a.getVerticeDestino()); // Hace el union sobre los vertices de ese arco
		}
		return UF.numberOfSets() == 1; // Si la cantidad de conjuntos es 1 el grafo es conexo
	}

	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> solve(Grafo<Integer> grafo){
		LinkedList<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next()); //heuristica: si ordeno los arcos por peso podo mas cuando (currentsolution + candidate > bestSolution), encuentro antes la mejor solucion
		}
		/* Collections.sort(arcos, new Comparator<Arco<Integer>>() {
			@Override
			public int compare(Arco<Integer> o1, Arco<Integer> o2) {
				return Integer.compare(o2.getEtiqueta(), o1.getEtiqueta());
			}
		}); */ //con esto solo no alcanza, tal vez matener la lista ordenada?
		Solutions s = new Solutions(grafo.cantidadArcos());
		internalBacktracking(grafo.getVertices(), arcos, s);
		return new SimpleEntry<HashSet<Arco<Integer>>,Integer>(s.bestSolution, metric);
	}
	static private void internalBacktracking(Set<Integer> vertices, LinkedList<Arco<Integer>> arcos, Solutions s){ //O(n!) donde n es la cantidad de arcos
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
				if ((s.currentDistance < s.bestDistance) && (s.currentSolution.size() < vertices.size())){
					internalBacktracking(vertices, arcos, s);
				}
				// internalBacktracking(vertices, arcos, s);
				s.currentDistance -= candidate.getEtiqueta();
				s.currentSolution.remove(candidate);
				arcos.addLast(candidate);
			}
		}
	}
	
	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> solvecoso(Grafo<Integer> grafo){//TODO limpiar codigo y cambiar nombre metodo
		LinkedList<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next()); //heuristica: si ordeno los arcos por peso podo mas cuando (currentsolution + candidate > bestSolution), encuentro antes la mejor solucion
		}
		/* Collections.sort(arcos, new Comparator<Arco<Integer>>() {
			@Override
			public int compare(Arco<Integer> o1, Arco<Integer> o2) {
				return Integer.compare(o2.getEtiqueta(), o1.getEtiqueta());
			}
		}); */ //con esto solo no alcanza, tal vez matener la lista ordenada?
		Solutions s = new Solutions(grafo.cantidadArcos());
		internalBacktrackingcoso(grafo.getVertices(), arcos, s);
		return new SimpleEntry<HashSet<Arco<Integer>>,Integer>(s.bestSolution, metric);
	}
	
	
	static private void internalBacktrackingcoso(Set<Integer> vertices, LinkedList<Arco<Integer>> arcos, Solutions s){ //O(n!) donde n es la cantidad de arcos
		metric++;
		//System.out.println(metric);
		if (arcos.isEmpty()) {
			if (isValid(s.currentSolution, vertices)) {
				if (s.currentDistance < s.bestDistance) {
					s.bestSolution = new HashSet<>(s.currentSolution);
					s.bestDistance = s.currentDistance;
				}
			}
		} else {
			Arco<Integer> candidate = arcos.removeFirst();
				internalBacktrackingcoso(vertices, arcos, s);
				// arcos.addFirst(candidate);
				// candidate = arcos.removeFirst();
				s.currentSolution.add(candidate);
				s.currentDistance += candidate.getEtiqueta();
				if ((s.currentDistance < s.bestDistance) && (s.currentSolution.size() < vertices.size())){
					internalBacktrackingcoso(vertices, arcos, s);
				}
				// internalBacktrackingcoso(vertices, arcos, s);
				s.currentDistance -= candidate.getEtiqueta();
				s.currentSolution.remove(candidate);
				arcos.addFirst(candidate);
		}
	}
}
