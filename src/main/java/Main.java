import model.Graph;
import util.LoadData;

public class Main {
	public static void main(String[] args) {
        Graph graphMatriz;

        graphMatriz = LoadData.loadAdj("dijkstra\\src\\main\\java\\data\\data.txt");

        System.out.println("Matriz:");
        System.out.println(graphMatriz);

        Graph lisGraph = LoadData.loadList("dijkstra\\src\\main\\java\\data\\data.txt");

        System.out.println("Lista:");
        System.out.println(lisGraph);
    }
}

