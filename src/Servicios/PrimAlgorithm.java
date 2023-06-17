package Servicios;

import java.util.*;
import Grafo.*;

public class PrimAlgorithm <T> {

    static public void primMST(Grafo<Integer> vertices) {

        
        Set<Arco<Integer>> edges = new HashSet<>();
		for (Iterator<Arco<Integer>> it = vertices.obtenerArcos(); it.hasNext();) {
			edges.add(it.next()); //heuristica: si ordeno los arcos por peso podo mas cuando (currentsolution + candidate > bestSolution), encuentro antes la mejor solucion
		}

        // Crear un conjunto para almacenar los vértices visitados
        Set<Integer> visited = new HashSet<>();

        // Crear un PriorityQueue para almacenar los arcos (ordenados por peso)
        PriorityQueue<Arco<Integer>> minHeap = new PriorityQueue<>(Comparator.comparingInt(Arco<Integer>::getEtiqueta));

        // Escoger un vértice inicial arbitrario
        Iterator<Integer> itVertices = vertices.obtenerVertices();
        Integer startVertex = itVertices.next();
        visited.add(startVertex);

        // Agregar los arcos adyacentes al vértice inicial al minHeap
        addAdjacentEdges(startVertex, edges, minHeap);

        int totalWeight = 0;
        int metric = 0;
        // Iterar hasta que se visiten todos los vértices
        while (visited.size() < vertices.cantidadVertices()) {
            //metric++;
            // Extraer el arco de menor peso del minHeap
            Arco<Integer> minEdge = minHeap.poll();
            
            // Obtener el vértice adyacente no visitado
            Integer nextVertex = minEdge.getVerticeDestino();

            // Si el vértice adyacente no ha sido visitado, agregarlo al árbol y marcarlo como visitado
            if (!visited.contains(nextVertex)) {
                metric++;
                visited.add(nextVertex);
                totalWeight += minEdge.getEtiqueta();
                System.out.println("Edge: " + minEdge.getVerticeOrigen() + " - " + minEdge.getVerticeDestino() + ", Weight: " + minEdge.getEtiqueta());
                addAdjacentEdges(nextVertex, edges, minHeap);
            }
        }
        System.out.println("Total weight: " + totalWeight);
        System.out.println("Metrica: " + metric);
    }

    static private void addAdjacentEdges(Integer vertex, Set<Arco<Integer>> edges, PriorityQueue<Arco<Integer>> minHeap) {
        for (Arco<Integer> edge : edges) {
            if (edge.getVerticeOrigen() == vertex) {
                minHeap.offer(edge);
            }
        }
    }    
}
