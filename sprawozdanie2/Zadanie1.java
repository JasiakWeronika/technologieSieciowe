import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Zadanie1 {
    private final Random random = new Random(System.currentTimeMillis());
    public void start() {
    	// tworzymy listê zawieraj¹c¹ przyk³adowe grafy 
    	List<NowyGraf> grafy = new ArrayList<>();
    	// dodajemy do niej grafy
        grafy.add((NowyGraf) new A());
        grafy.add((NowyGraf) new B());
        grafy.add((NowyGraf) new C());
        grafy.add((NowyGraf) new D());
        for (NowyGraf nowyGraf : grafy) {
        	System.out.println();
            int wszystkie = 0;
            int zdane = 0;
            System.out.print("Graf " + nowyGraf.toString());
            while (wszystkie != 1000) {
                SimpleWeightedGraph<Integer, DefaultWeightedEdge> graf = nowyGraf.stworzGraf();
                ArrayList<DefaultWeightedEdge> krawedzie = new ArrayList<>();
                krawedzie.addAll(graf.edgeSet());
                for (DefaultWeightedEdge krawedz : krawedzie) {
                    double waga = graf.getEdgeWeight(krawedz);
                    double numer = random.nextDouble();
                    if (numer > waga) {
                        // usuwamy krawêdŸ
                        graf.removeEdge(krawedz);
                    }
                }
                if (czySpojny(graf)) {
                    zdane++;
                }
                wszystkie++;
                System.out.print("\r");
                System.out.print(nowyGraf.toString() + ": " + zdane * 100 / wszystkie + "%  " + zdane+"/" + wszystkie);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean czySpojny(UndirectedGraph<Integer, DefaultWeightedEdge> graph) {
        return new ConnectivityInspector<>(graph).isGraphConnected();
    }
    public static void main(String[] args) {
    	Zadanie1 z = new Zadanie1();
    	z.start();
    }
}