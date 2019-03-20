import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class A implements NowyGraf {
    public SimpleWeightedGraph<Integer, DefaultWeightedEdge> stworzGraf() {
        SimpleWeightedGraph<Integer, DefaultWeightedEdge> g =
                new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 1; i <= 20; i++) {
            g.addVertex(i);
        }
        for (int i = 1; i < 20; i++) {
            g.setEdgeWeight(g.addEdge(i, i + 1), 0.95);
        }
        return g;
    }
    public String toString() {
        return "A";
    }
}