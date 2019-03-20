import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.jgrapht.Graph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

public class Zadanie2 {
	// wczytanie danych na temat modelu grafu
	public SimpleGraph<Integer, GraphEdge> g;
    public int[][] flows;
    public double reliability;
    public double maxlate;
    public final double largeOfPackage = 65535.0;
    public int count;
    public Zadanie2(String fileLocation) throws IOException {
    	fileLocation="C:\\Users\\weron\\eclipse-workspace\\TS\\src\\grafy";
        String line;
        File graphFile = new File(fileLocation + File.separator + "graf2");
        BufferedReader brGraph = new BufferedReader(new FileReader(graphFile));
        String[] splitLine = brGraph.readLine().split(" ");
        reliability = Double.parseDouble(splitLine[0]);
        maxlate = Double.parseDouble(splitLine[1]);  
        count = Integer.parseInt(splitLine[2]);       
        g = new SimpleGraph<>(GraphEdge.class);
        while ((line = brGraph.readLine()) != null && (splitLine = line.split(" ")).length == 3) {
        	int v1 = Integer.parseInt(splitLine[0]);
            int v2 = Integer.parseInt(splitLine[1]);
            int capacity = Integer.parseInt(splitLine[2]);
            g.addVertex(v1);
            g.addVertex(v2);
            g.addEdge(v1, v2, new GraphEdge(capacity));
        }
        // wczytanie danych o iloœci przesy³anych pakietów
        int graphSize = g.vertexSet().size();
        flows = new int[graphSize][graphSize];
        graphFile = new File(fileLocation + File.separator + "transfery2");
        brGraph = new BufferedReader(new FileReader(graphFile));
        while ((line = brGraph.readLine()) != null && (splitLine = line.split(" ")).length == 3) {
            int src = Integer.parseInt(splitLine[0])-1 ;
            int desc = Integer.parseInt(splitLine[1])-1;
            int w = Integer.parseInt(splitLine[2]);
            flows[src][desc] = w;
        }
    }
	public static void main(String[] args) {
		String fileLocation = "";
		Random generator = new Random();
		Zadanie2 data = null;
        SimpleGraph<Integer, GraphEdge> graph;
        try {
            data = new Zadanie2(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        int[][] flows2 = data.flows;
        int all = 0;
        int success = 0;
        ArrayList<Integer> lates = new ArrayList<>();
        for (int p = 1; p <= data.count; p++) {
        	graph = (SimpleGraph<Integer, GraphEdge>) data.g.clone();
            graph.edgeSet().parallelStream().forEach(GraphEdge -> GraphEdge.setFlow(0));
            ArrayList<GraphEdge> Edges = new ArrayList<>();
            Edges.addAll(graph.edgeSet());
            // niezawodnoœæ
            for (GraphEdge E : Edges) {
                double weight = data.reliability;
                double number = generator.nextDouble();
                if (number > weight) {
                    graph.removeEdge(E);
                }
            }
            int graphSize = graph.vertexSet().size();
            ConnectivityInspector<Integer, GraphEdge> ci = new ConnectivityInspector<>(graph);
            if (ci.isGraphConnected()) {
                for (int i = 0; i < graphSize; i++) {
                    for (int j = 0; j < graphSize; j++) {
                        if (i == j) continue;
                        int vertex1 = i + 1;
                        int vertex2 = j + 1;
                        int weight = flows2[i][j];
                        ArrayList<GraphEdge> list = (ArrayList<GraphEdge>) new DijkstraShortestPath<>(graph, vertex1, vertex2).getPathEdgeList();
                        // nadanie przep³ywów
                       list.parallelStream().forEach(edge -> edge.setFlow(edge.getFlow() + weight));
                    }
                }
                // jeœli przepustowoœæ wieksza od przep³ywu
                if (CapacityTest(graph)) {
                	// wyliczanie opoznienia
                    int sumOfPackages = Arrays.stream(flows2).flatMapToInt(Arrays::stream).sum();
                    final Zadanie2 finalData = data;
                    double sumFromEdges = graph.edgeSet().stream().mapToDouble(GraphEdge -> ((GraphEdge.getFlow() * finalData.largeOfPackage) / GraphEdge.getCapacity() - GraphEdge.getFlow())).sum();
                    int late = (int) (sumFromEdges / sumOfPackages);
                    lates.add(late);
                    // jeœli opoznienie mniejsze od dopuszczalnego
                    if (late < data.maxlate) {
                        success++;
                    }
                }
            }
            all++;
        }
        System.out.print(success * 100 / all + "% sukcesów "  + "Liczba prób: " + all + " Liczba sukcesów: " + success);
        int middleLate = (lates.parallelStream().mapToInt(Integer::intValue).sum()/lates.size());
        System.out.print("\n");
        System.out.print("Œrednie opóŸnienie: " + middleLate);
    }
    private static boolean CapacityTest(Graph<Integer, GraphEdge> graph) {
        for (GraphEdge edge : graph.edgeSet()) {
            if (edge.getFlow() > edge.getCapacity()) {
                return false;
            }
        }
        return true;
	}
}