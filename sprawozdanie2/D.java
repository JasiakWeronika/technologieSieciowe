import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import java.util.Random;

public class D implements NowyGraf {
    private final Random random = new Random(System.currentTimeMillis());
    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> stworzGraf() {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> g =
                new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 1; i <= 20; i++) {
            g.addVertex(i);
        }
        for (int i = 1; i < 20; i++) {
            g.setEdgeWeight(g.addEdge(i, i + 1), 0.95);
        }
        g.setEdgeWeight(g.addEdge(1, 20), 0.95);
        g.setEdgeWeight(g.addEdge(1, 10), 0.8);
        g.setEdgeWeight(g.addEdge(5, 15), 0.7);
        for (int i = 0; i < 4; i++) {
            int i1 = 0;
            int i2 = 0;
            while (i1 == i2 || g.containsEdge(i1, i2) || g.containsEdge(i2, i1)) {
                i1 = randInt(1, 20);
                i2 = randInt(1, 20);
            }
            g.setEdgeWeight(g.addEdge(i1, i2), 0.4);
        }
        return g;
    }
    private int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }
    public String toString() {
        return "D";
    }
}