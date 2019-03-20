import org.jgrapht.graph.DefaultEdge;

public class GraphEdge extends DefaultEdge{
    int capacity;
    int flow = 0;
    public GraphEdge(int capacity) {
        this.capacity = capacity;
    }
    public int getCapacity() {
        return capacity;
    }
    public int getFlow() {
        return flow;
    }
    public void setFlow(int flow) {
        this.flow = flow;
    }
}