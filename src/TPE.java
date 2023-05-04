
import Grafo.*;
import Servicios.ServicioBFS;
import Servicios.ServicioDFS;

public class TPE {
	public static void main(String[] args) throws Exception {
		GrafoDirigido<Integer> grafo = new GrafoDirigido<>();
		grafo.agregarVertice(1);
		grafo.agregarVertice(2);
		grafo.agregarVertice(3);
		grafo.agregarVertice(4);
		grafo.agregarVertice(5);
		grafo.agregarVertice(10);
		grafo.agregarVertice(20);
		grafo.agregarVertice(7);
		grafo.agregarVertice(13);
		grafo.agregarArco(1, 2);
		grafo.agregarArco(1, 3);
		grafo.agregarArco(1, 4);
		grafo.agregarArco(1, 5);
		grafo.agregarArco(5, 7);
		grafo.agregarArco(7, 13);
		grafo.agregarArco(7, 1);
		grafo.agregarArco(2, 4);
		grafo.agregarArco(2, 20);
		grafo.agregarArco(10, 10);
		grafo.agregarArco(20, 7);
		grafo.agregarArco(13, 2);
		System.out.println("Arcos: " + grafo.cantidadArcos());
		System.out.println("Vertices: " + grafo.cantidadVertices());
		System.out.println("Vertice 1?: " + grafo.contieneVertice(1));
		System.out.println("Vertice 6?: " + grafo.contieneVertice(6));
		System.out.println("Arco 13-2?: " + grafo.existeArco(13, 2));
		grafo.borrarArco(13, 2);
		System.out.println("Arco 13-2?: " + grafo.existeArco(13, 2));
		System.out.println(grafo);
		ServicioDFS dfs = new ServicioDFS(grafo);
		System.out.println(dfs.dfsForest());
		System.out.println(dfs);
		ServicioBFS bfs = new ServicioBFS(grafo);
		System.out.println(bfs.bfsForest());
		System.out.println(bfs);
	}
}
