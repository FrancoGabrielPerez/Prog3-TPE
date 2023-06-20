package Servicios;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;

import Grafo.*;

public class Prim <T> {

    static public SimpleEntry<HashSet<Arco<Integer>>, Integer>  primMST(Grafo<Integer> grafo) {

        HashSet<Arco<Integer>> solution = new HashSet<Arco<Integer>>();
        
        // Crear el set de Arcos del grafo
        Set<Arco<Integer>> edges = new HashSet<>();
		for (Iterator<Arco<Integer>> it = grafo.obtenerArcos(); it.hasNext();) {
			edges.add(it.next()); 
		}

        // Crear un conjunto para almacenar los vértices visitados
        Set<Integer> visited = new HashSet<>();

        // Crear un PriorityQueue para almacenar los arcos (ordenados por peso)
        PriorityQueue<Arco<Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Arco<Integer>::getEtiqueta));

        // Escoger un vértice inicial arbitrario
        Iterator<Integer> itVertices = grafo.obtenerVertices();
        Integer startVertex = itVertices.next();
        visited.add(startVertex);

        // Agregar los arcos adyacentes al vértice inicial al minHeap
        addAdjacentEdges(startVertex, edges, minHeap);

        
        int metric = 0;
        // Iterar hasta que se visiten todos los vértices
        while (visited.size() < grafo.cantidadVertices()) {
            metric++;
            // Extraer el arco de menor peso del minHeap
            Arco<Integer> minEdge = minHeap.poll();
            
            // Obtener el vértice adyacente no visitado
            Integer nextVertex = minEdge.getVerticeDestino();

            // Si el vértice adyacente no ha sido visitado, agregarlo al árbol y marcarlo como visitado
            if (!visited.contains(nextVertex)) {
                //metric++;
                visited.add(nextVertex);
                solution.add(minEdge);
                addAdjacentEdges(nextVertex, edges, minHeap);
            }
        }
        return new SimpleEntry<HashSet<Arco<Integer>>, Integer> (solution, metric); 
    }

    static private void addAdjacentEdges(Integer vertex, Set<Arco<Integer>> edges, PriorityQueue<Arco<Integer>> minHeap) {
        for (Arco<Integer> edge : edges) {
            if (edge.getVerticeOrigen() == vertex) {
                minHeap.offer(edge);
            }
        }
    }    
}
