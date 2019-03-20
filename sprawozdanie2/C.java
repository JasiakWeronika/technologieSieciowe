import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class C implements NowyGraf {
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
        return g;
    }
    public String toString() {
        return "C";
    }
}