import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

import Grafo.*;
import Servicios.*;

public class TPE {

	static public void TestParte1(){
		GrafoDirigido<Integer> grafo = new GrafoDirigido<>();
		grafo.agregarVertice(1);
		grafo.agregarVertice(2);
		grafo.agregarVertice(3);
		grafo.agregarVertice(4);
		grafo.agregarVertice(5);
		grafo.agregarVertice(6);
		grafo.agregarVertice(7);
		grafo.agregarVertice(13);
		grafo.agregarArco(1, 2);
		grafo.agregarArco(1, 3);
		grafo.agregarArco(1, 4);
		grafo.agregarArco(2, 4);
		grafo.agregarArco(3, 4);
		grafo.agregarArco(3, 5);
		grafo.agregarArco(4, 6);
		grafo.agregarArco(4, 7);
		grafo.agregarArco(6, 2);
		grafo.agregarArco(6, 7);
		grafo.agregarArco(5, 1);
		grafo.agregarArco(7, 2);
		grafo.agregarArco(13, 13);
		grafo.agregarArco(4, 5);
		//grafo.agregarArco(13, 2);
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


		// Servicio caminos
		
		System.out.print("Vertices del grafo: [");
		Iterator<Integer> itVertices = grafo.obtenerVertices();
		while (itVertices.hasNext()){
			Integer value = itVertices.next();
			if (!itVertices.hasNext())
				System.out.println(value + "]");
			else
				System.out.print(value + ", ");			
		}
		System.out.println(grafo);
		try (Scanner scanner = new Scanner(System.in)){            
            System.out.print("Ingrese el vertice de origen: ");
            Integer origen = scanner.nextInt();
			System.out.print("Ingrese el vertice de destino: ");
            Integer destino = scanner.nextInt();
			System.out.print("Ingrese el limite de arcos a recorrer: ");
            Integer limite = scanner.nextInt();
			ServicioCaminos s = new ServicioCaminos(grafo, origen, destino, limite);
			List<List<Integer>> caminos = s.caminos();
			if (caminos.size() == 0)
				System.out.println("No hay camino/s posible/s entre el/los vertice/s ingresado/s.");
			else {
				System.out.println("Camino/s posibles entre el vertice " + origen + " y el " + destino + ", pasando como maximo por "+ limite + " arcos: ");
				int index = 1;
				for (List<Integer> camino : caminos) {
					System.out.println("  " + index + ": " + camino);
					index++;
				}
			}           
        }
	}	

	static public void TestParte2(){
		Integer dataset = 1;
		try (Scanner scanner = new Scanner(System.in)){            
            System.out.print("Ingrese dataset a probar: ");
            dataset = scanner.nextInt();
			
        }
		Path currentWorkingDir = Paths.get("").toAbsolutePath();
		String DatasetPath = currentWorkingDir.normalize().toString() + "\\Datasets\\dataset" + dataset +".txt";
		CSVReader reader = new CSVReader(DatasetPath);
		Grafo<Integer> grafo = reader.read();
		//System.out.println(grafo.toString()); //Imprime el grafo para corroborar que se cargo corectamente

		int cota;
		SimpleEntry<HashSet<Arco<Integer>>, Integer> dijkstraSolution = Dijkstra.dijkstraSolver(grafo);
		cota = printStations(dijkstraSolution, "Dijkstra");
		System.out.println();

		SimpleEntry<HashSet<Arco<Integer>>, Integer> primSolution = Prim.primMST(grafo);
		cota = printStations(primSolution, "Prim");
		
		System.out.println();
		SimpleEntry<HashSet<Arco<Integer>>, Integer> backtrackingBinarySolution = Backtracking.backtrackingBinary(grafo, cota); // tambien hay un metodo para llamarlo sin cota
		printStations(backtrackingBinarySolution, "Backtracking Binario");

		System.out.println();
		SimpleEntry<HashSet<Arco<Integer>>, Integer> backtrackingFactorialSolution = Backtracking.bactrackingFactorial(grafo, cota);
		printStations(backtrackingFactorialSolution, "Backtracking Factorial");
	}

	static private int printStations(SimpleEntry<HashSet<Arco<Integer>>, Integer> solution, String technique){
		System.out.println(technique);
		int distance = 0;
		int prints = 0;
		System.out.println("Conexiones: ");
		for (Arco<Integer> a : solution.getKey()) {
			System.out.print("E" + a.getVerticeOrigen() + "-E" + a.getVerticeDestino() + ", ");
			prints++;
			if (prints == 4) {
				System.out.println();
				prints = 0;
			}
			distance += a.getEtiqueta();
		}
		if (prints != 0) {
			System.out.println();
		}
		System.out.println("Distancia: " + distance + " kms.");
		System.out.println("Metrica (iteraciones):  "+ solution.getValue());
		return distance;
	}
	
	public static <T> void main(String[] args) throws Exception {
		//TestParte1();
		TestParte2();
	}
}
