package Servicios;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import Grafo.*;

public class Backtracking {
	
	static public void Back(Grafo<Integer> grafo){
		Queue<Arco<Integer>> arcos = new LinkedList<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			arcos.add(it.next());
		}
		HashSet<Arco<Integer>> solucion = new HashSet<>(grafo.cantidadArcos());
		HashSet<Arco<Integer>> parcial = new HashSet<>(grafo.cantidadArcos());
		backaux(grafo, arcos, solucion, parcial);
	}

	static private boolean isValid(HashSet<Arco<Integer>> currentSolucion, Set<Integer> vertices){
		Grafo<Integer> solution = new GrafoNoDirigido<Integer>();
		for (Integer v : vertices) {
			solution.agregarVertice(v);
		}
		for (Arco<Integer> a : currentSolucion) {
			solution.agregarArco(a.getVerticeOrigen(), a.getVerticeDestino(), a.getEtiqueta());
		}
		
		return false;
	}

	static private void backaux(Grafo<Integer> grafo, Queue<Arco<Integer>> arcos, HashSet<Arco<Integer>> bestSolucion, HashSet<Arco<Integer>> currentSolucion){
		if (isValid(currentSolucion, grafo.getVertices())) {
			if (isShorter(currentSolucion, bestSolucion)) {
				bestSolucion = currentSolucion;
			}
		} else {
			for (int i = 0; i < arcos.size(); i++) { //se puede aplicar poda si currentsolution + candidate > bestSolution, se puede podar con union find tambien
				Arco<Integer> candidate = arcos.poll();
				currentSolucion.add(candidate);
				backaux(grafo, arcos, bestSolucion, currentSolucion);
				currentSolucion.remove(candidate);
				arcos.add(candidate);
			}
		}
	}
}
