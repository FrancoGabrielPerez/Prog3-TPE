package Grafo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GrafoDirigido<T> implements Grafo<T> {

	private HashMap<Integer, HashMap<Integer, Arco<T>>> graph; //se pueden considerar O(1) los metodos de un hashmap o se toman como O(n)?. https://en.wikipedia.org/wiki/Hash_table
	private int cantArcs;

	public GrafoDirigido() {
		this.graph = new HashMap<>();
		this.cantArcs = 0;
	}

	/**
    * Complejidad: O(1) debido a que se está utilizando el método putIfAbsent() de HashMap,
	* el cual realiza una búsqueda y una inserción en tiempo constante.
    */
	@Override
	public void agregarVertice(int verticeId) {
		graph.putIfAbsent(verticeId, new HashMap<>());
	}

	/**
    * Complejidad: O(N) donde N es la cantidad de vertices, debido a que
    */
	@Override
	public void borrarVertice(int verticeId) {
		if (! graph.containsKey(verticeId)) {
			return;
		}
		cantArcs -= graph.get(verticeId).size();
		graph.remove(verticeId);
		graph.forEach((k,v) -> {if (v.remove(verticeId) != null){cantArcs--;}});
	}

	/**
    * Complejidad: O(1) debido a que se busca directamente la lista de adyacencia correspondiente al vertice verticeId1
    * y se agrega el arco a esa lista utilizando el método put() de HashMap.
    * Además, se incrementa el contador de cantArcs en una unidad, lo cual es una operación de tiempo constante.
    */
	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
		if (graph.containsKey(verticeId1)) {
			graph.get(verticeId1).put(verticeId2, new Arco<>(verticeId1, verticeId2, etiqueta));
			cantArcs++;
		}
	}

	/**
    * Complejidad: O(1) ya que se llama a agregarArco(int, int, T) que es de complejidad O(1)
    */
	public void agregarArco(int verticeId1, int verticeId2) {
		agregarArco(verticeId1, verticeId2, null);
	}

	/**
    * Complejidad: O(1) debido a que utiliza métodos de HashMap como remove() y containsKey(),
    * los cuales tienen complejidad O(1) en promedio para la mayoría de los casos.
    */
	@Override
	public void borrarArco(int verticeId1, int verticeId2) {
		if (graph.containsKey(verticeId1)) {
			if (graph.get(verticeId1).remove(verticeId2) != null) {
				cantArcs--;
			}
		}
	}

	@Override
	public boolean contieneVertice(int verticeId) {
		return graph.containsKey(verticeId);
	}

	@Override
	public boolean existeArco(int verticeId1, int verticeId2) {
		return graph.containsKey(verticeId1) ? graph.get(verticeId1).containsKey(verticeId2) : false;
	}

	@Override
	public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
		return graph.containsKey(verticeId1) ? graph.get(verticeId1).get(verticeId2) : null;
	}

	@Override
	public int cantidadVertices() {
		return graph.size();
	}

	@Override
	public int cantidadArcos() {
		return cantArcs;
	}

	@Override
	public Iterator<Integer> obtenerVertices() {
		return graph.keySet().iterator();
	}

	@Override
	public Iterator<Integer> obtenerAdyacentes(int verticeId) {
		return graph.containsKey(verticeId) ? graph.get(verticeId).keySet().iterator() : Collections.emptyIterator();
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos() {
		return new Iterator<Arco<T>>() {
			private Iterator<HashMap<Integer, Arco<T>>> outerIterator = graph.values().iterator();
			private Iterator<Arco<T>> innerIterator = Collections.emptyIterator();
	
			@Override
			public boolean hasNext() {
				while (!innerIterator.hasNext() && outerIterator.hasNext()) {
					innerIterator = outerIterator.next().values().iterator();
				}
				return innerIterator.hasNext();
			}
	
			@Override
			public Arco<T> next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				return innerIterator.next();
			}
		};
	}

	@Override
	public Iterator<Arco<T>> obtenerArcos(int verticeId) {
		return graph.containsKey(verticeId) ? graph.get(verticeId).values().iterator() : Collections.emptyIterator();
	}

	@Override
	public String toString() {
		String s = "[";
		Iterator<Arco<T>> arcsIt = this.obtenerArcos();
		while (arcsIt.hasNext()) {
			s += " ( " + arcsIt.next() + ") ";
		}
		s += "] {" + cantArcs + "}";
		return s;
	}

	
}