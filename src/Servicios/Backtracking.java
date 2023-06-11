package Servicios;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import Grafo.*;

public class Backtracking {	
	
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
	
	static private int distanceAdder(HashSet<Arco<Integer>> solution) {
		int res = 0;
		for (Arco<Integer> a : solution) {
			res += a.getEtiqueta();
		}
		return res != 0 ? res : Integer.MAX_VALUE;
	}
	
	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> solve(Grafo<Integer> grafo){
		Queue<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next()); //heuristica: si ordeno los arcos por peso podo mas cuando (currentsolution + candidate > bestSolution), encuentro antes la mejor solucion
		}
		SimpleEntry<HashSet<Arco<Integer>>, Integer> solucion = new SimpleEntry<HashSet<Arco<Integer>>,Integer>(new HashSet<>(grafo.cantidadArcos()), 0);
		HashSet<Arco<Integer>> parcial = new HashSet<>(grafo.cantidadArcos());
		solucion = internalBacktracking(grafo, arcos, solucion, parcial);
		return solucion;
	}
	
	static private SimpleEntry<HashSet<Arco<Integer>>, Integer> internalBacktracking(Grafo<Integer> grafo, Queue<Arco<Integer>> arcos, SimpleEntry<HashSet<Arco<Integer>>, Integer> bestSolucion, HashSet<Arco<Integer>> currentSolucion){ //O(n) donde n es la cantidad de arcos
		bestSolucion.setValue(bestSolucion.getValue()+1);
		if (isValid(currentSolucion, grafo.getVertices())) {
			if (distanceAdder(currentSolucion) < distanceAdder(bestSolucion.getKey())) {
				return new SimpleEntry<HashSet<Arco<Integer>>,Integer>(new HashSet<>(currentSolucion), bestSolucion.getValue()) ;
			}
		} else {
			
			for (int i = 0; i < arcos.size(); i++) { //se puede aplicar poda si currentsolution + candidate > bestSolution, se puede podar con union find tambien
				Arco<Integer> candidate = arcos.poll();
				currentSolucion.add(candidate);
				if (distanceAdder(currentSolucion) <= 500)
					bestSolucion = internalBacktracking(grafo, arcos, bestSolucion, currentSolucion);
				currentSolucion.remove(candidate);
				arcos.add(candidate);
			}
		}
		return bestSolucion;
	}
}
