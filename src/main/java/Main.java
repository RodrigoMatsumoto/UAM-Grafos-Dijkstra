import model.Dijkstra;
import model.Graph;
import util.LoadData;

public class Main {
	public static void main(String[] args) {
        Graph graphMatriz = LoadData.loadAdj("src/main/java/data/data.txt");
        System.out.println("Matriz:");
        System.out.println(graphMatriz);

        Graph lisGraph = LoadData.loadList("src/main/java/data/data.txt");
        System.out.println("Lista:");
        System.out.println(lisGraph);

        Dijkstra teste = LoadData.loadDijkstra("src/main/java/data/data.txt");
        System.out.println("Dijkstra:");
        System.out.println(teste);
    }
}