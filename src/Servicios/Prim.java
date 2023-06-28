package Servicios;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;

import Grafo.*;

public class Prim {
    
    static public SimpleEntry<HashSet<Arco<Integer>>, Integer>  primMST(Grafo<Integer> grafo) {
        int metric = 0;
        HashSet<Arco<Integer>> solution = new HashSet<Arco<Integer>>();        
        
        // Se crea el set de Arcos del grafo
        Set<Arco<Integer>> edges = new HashSet<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			edges.add(it.next()); 
		}

        // Se crea un conjunto para almacenar los vertices visitados
        Set<Integer> visited = new HashSet<>();

        // Se crea un PriorityQueue para almacenar los arcos (ordenados por peso)
        PriorityQueue<Arco<Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Arco<Integer>::getEtiqueta));

        // Se escoge un vertice inicial arbitrario
        Iterator<Integer> itVertices = grafo.obtenerVertices();
        Integer startVertex = itVertices.next();
        visited.add(startVertex);

        // Se agregan los arcos adyacentes al vertice inicial al minHeap
        metric += addAdjacentEdges(startVertex, edges, minHeap);
        
        // Se itera hasta que se visiten todos los vertices
        while (visited.size() < grafo.cantidadVertices()) {
            metric++;
            // Se extrae el arco de menor peso del minHeap
            Arco<Integer> minEdge = minHeap.poll();
            
            // Obtener el vertice adyacente no visitado
            Integer nextVertex = minEdge.getVerticeDestino();

            // Si el vertice adyacente no ha sido visitado, se agrega a la solucion y se marca como visitado
            if (!visited.contains(nextVertex)) {
                visited.add(nextVertex);
                solution.add(minEdge);
                metric += addAdjacentEdges(nextVertex, edges, minHeap);
            }
        }
        return new SimpleEntry<HashSet<Arco<Integer>>, Integer> (solution, metric); 
    }

    static private int addAdjacentEdges(Integer vertex, Set<Arco<Integer>> edges, PriorityQueue<Arco<Integer>> minHeap) {
        int internalMetric = 0;
        for (Arco<Integer> edge : edges) {
            internalMetric++;
            if (edge.getVerticeOrigen() == vertex) {
                minHeap.offer(edge);
            }
        }
        return internalMetric;
    }    
}
