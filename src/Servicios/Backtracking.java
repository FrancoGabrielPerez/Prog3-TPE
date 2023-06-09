package Servicios;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import Grafo.*;

public class Backtracking {	
	
	static private boolean isValid(HashSet<Arco<Integer>> currentSolucion, int cantVertices){
		/* Grafo<Integer> solution = new GrafoNoDirigido<Integer>();
		for (Integer v : vertices) {
			solution.agregarVertice(v);
		}
		for (Arco<Integer> a : currentSolucion) {
			solution.agregarArco(a.getVerticeOrigen(), a.getVerticeDestino(), a.getEtiqueta());
		} */
		//System.out.println(currentSolucion);
		if (currentSolucion.size() > 0) {
			//System.out.println(currentSolucion.size());
			UnionFind finder = new UnionFind(cantVertices); // Se crea una instancia de UnionFinder del tamaño del Set de arcos
			for (Arco<Integer> a : currentSolucion)  { // Generar para el Set de arcos, los arreglos de uniones
					finder.union(a.getVerticeOrigen()-1, a.getVerticeDestino()-1);
				}
			return finder.numberOfSets() == 1;  // Si la cantidad de conjuntos es distinto de 1, es un grafo con vertices desconectados 
		}
		return false;
		
		// NOTA: no se si esta funcionando, no lo probe, habria que verificar tambien que esten todos los vertices del grafo en la 
		// union resultante.
	}
	
	static private int distanceAdder(HashSet<Arco<Integer>> solution) {
		int res = 0;
		for (Arco<Integer> a : solution) {
			res += a.getEtiqueta();
		}
		return res;
	}
		
	static private boolean isShorter(HashSet<Arco<Integer>> currentSolucion, HashSet<Arco<Integer>> bestSolucion) {
		return distanceAdder(currentSolucion) < distanceAdder(bestSolucion);
	}
	
	static public SimpleEntry<HashSet<Arco<Integer>>, Integer> back(Grafo<Integer> grafo){
		Queue<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next());
		}
		HashSet<Arco<Integer>> solucion = new HashSet<>(grafo.cantidadArcos());
		HashSet<Arco<Integer>> parcial = new HashSet<>(grafo.cantidadArcos());
		int archCounter = 0;
		backaux(archCounter, grafo, arcos, solucion, parcial);
		return new SimpleEntry<>(solucion, distanceAdder(solucion));
	}
	
	static private void backaux(int archCounter, Grafo<Integer> grafo, Queue<Arco<Integer>> arcos, HashSet<Arco<Integer>> bestSolucion, HashSet<Arco<Integer>> currentSolucion){
		if (isValid(currentSolucion, grafo.cantidadVertices())) {
			if (isShorter(currentSolucion, bestSolucion)) {
				bestSolucion = currentSolucion;
				archCounter = 0;
			}
		} else {
			for (int i = 0; i < arcos.size(); i++) { //se puede aplicar poda si currentsolution + candidate > bestSolution, se puede podar con union find tambien
				Arco<Integer> candidate = arcos.poll();
				currentSolucion.add(candidate);
				archCounter++; // no hace falta, es igual a la cantidad de arcos en current solution
				backaux(archCounter, grafo, arcos, bestSolucion, currentSolucion);
				currentSolucion.remove(candidate);
				arcos.add(candidate);
				archCounter--;
			}
		}
	}
}
