package Grafo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class GrafoDirigido<T> implements Grafo<T> {

	private HashMap<Integer, HashMap<Integer, Arco<T>>> graph;
	private int cantArcs;

	public GrafoDirigido() {
		this.graph = new HashMap<>();
		this.cantArcs = 0;
	}

	/**
    * Complejidad: O(1) debido a que, para poder agregar un nuevo vertice, se está utilizando el método 
	* putIfAbsent() de HashMap, el cual realiza una búsqueda y una inserción en tiempo constante.
    */
	@Override
	public void agregarVertice(int verticeId) {
		graph.putIfAbsent(verticeId, new HashMap<>());
	}

	/**
    * Complejidad: O(n) donde n es la cantidad de vertices, debido a que para poder borrar un vertice se 
	* hace un recorrido por todos los vertices de la estructura. El resto de los metodos utilizados de 
	* la clase HashMap son todos de tiempo constante.
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
    * Complejidad: O(1) debido a que para agregar un nuevo arco, se accede directamente la lista de adyacencia correspondiente 
	* al vertice donde se desea agregar el arco a traves del metodo get (O(1)), chequeando previemante que exista con el 
	* metodo containsKey (O(1)) y se agrega el arco a esa lista utilizando el método put() de HashMap, que tambien es de tiempo
	* constante. Además, se incrementa el contador de cantArcs en una unidad, lo cual es una operación de tiempo constante.
    */
	@Override
	public void agregarArco(int verticeId1, int verticeId2, T etiqueta) {
		if (graph.containsKey(verticeId1)) {
			graph.get(verticeId1).put(verticeId2, new Arco<>(verticeId1, verticeId2, etiqueta));
			cantArcs++;
		}
	}

	/**
    * Complejidad: O(1) ya que para agregar un nuevor arco con este metodo, se invoca al metodo agregarArco(int, int, T) 
	* de esta misma clase que es de complejidad O(1)
    */
	public void agregarArco(int verticeId1, int verticeId2) {
		agregarArco(verticeId1, verticeId2, null);
	}

	/**
    * Complejidad: O(1) debido a que para borrar un arco se utilizan métodos de la clase HashMap como remove(), 
	* get() y containsKey(), los cuales tienen complejidad O(1).
    */
	@Override
	public void borrarArco(int verticeId1, int verticeId2) {
		if (graph.containsKey(verticeId1)) {
			if (graph.get(verticeId1).remove(verticeId2) != null) {
				cantArcs--;
			}
		}
	}

	/**
	* Complejidad: O(1) debido a que para poder consultar si existe un vertice se utiliza el método de la clase HashMap 
    * containsKey(), el cual tiene una complejidad O(1).
	*/
	@Override
	public boolean contieneVertice(int verticeId) {
		return graph.containsKey(verticeId);
	}

	/**
	* Complejidad: O(1) debido a que para poder consultar si existe un arco se utilizan los métodos de la clase HashMap 
    * containsKey() y get(), los cuales tienen una complejidad O(1).
	*/
	@Override
	public boolean existeArco(int verticeId1, int verticeId2) {
		return graph.containsKey(verticeId1) ? graph.get(verticeId1).containsKey(verticeId2) : false;
	}

	/**
	* Complejidad: O(1) debido a que para poder obtener un arco se utilizan los métodos de la clase HashMap 
    * containsKey() y get(), los cuales tienen una complejidad O(1).
	*/
	@Override
	public Arco<T> obtenerArco(int verticeId1, int verticeId2) {
		return graph.containsKey(verticeId1) ? graph.get(verticeId1).get(verticeId2) : null;
	}

	/**
	* Complejidad: O(1) debido a que para poder obtener la cantidad de vertices del grafo se utiliza el método 
    * de la clase HashMap size(), el cual tiene una complejidad O(1).
	*/
	@Override
	public int cantidadVertices() {
		return graph.size();
	}

	/**
	* Complejidad: O(1) debido a que para poder obtener la cantidad de arcos del grafo se retorna el valoruna de una variable
	* interna que almacena la cantidad de arcos y que se mantiene actualizada.
	*/
	@Override
	public int cantidadArcos() {
		return cantArcs;
	}

	/**
	* Complejidad: O(1) debido a que para poder obtener el iterador de vertices del grafo se utiliza el método de la clase HashMap 
    * keySet() y al mismo se le pide un iterador; ambos tienen una complejidad O(1),.
	*/
	@Override
	public Iterator<Integer> obtenerVertices() {
		return graph.keySet().iterator();
	}

	/**
	* Complejidad: O(1) debido a que para poder obtener el iterador de nodos adyacentes de un vertice dado 
	* se utilizan los métodos de la clase HashMap containsKey(), keySet() y get(), y el metodo iterator() de 
	* la clase Set los cuales tienen una complejidad O(1).
	*/
	@Override
	public Iterator<Integer> obtenerAdyacentes(int verticeId) {
		return graph.containsKey(verticeId) ? graph.get(verticeId).keySet().iterator() : Collections.emptyIterator();
	}

	/**
	 * Complejidad: O(1) debido a que para devolver un iterador de arcos se crea y retorna un iterador propio 
	 * en el cual las implementaciones de hasNext() y next() son O(1), ya que las mismas utilizan metodos 
	 * como values() e iterator() que son de complejidad O(1). 
	 */ 
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

	/**
	* Complejidad: O(1) debido a que para poder obtener el iterador de arcos de un vertice dado 
	* se utilizan los métodos de la clase HashMap containsKey(), values() y get(), y el metodo iterator() de 
	* la clase Set los cuales tienen una complejidad O(1).
	*/
	@Override
	public Iterator<Arco<T>> obtenerArcos(int verticeId) {
		return graph.containsKey(verticeId) ? graph.get(verticeId).values().iterator() : Collections.emptyIterator();
	}

	/**
	 * Complejidad: O(n), donde n es la cantidad de arcos, debido a que para poder imprimir por pantalla los
	 * arcos del grafo se itera sobre los mismos.
	 */
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