package Assess_Nov20;
import java.util.*;
import java.util.Map.Entry;

class GraphFindAllPaths<T> implements Iterable<T> {
    public final Map<T, Map<T, Double>> graph = new HashMap<T, Map<T, Double>>();
    public void addNode(T node) {
        graph.put(node, new HashMap<T, Double>());
    }
    public void addEdge (T source, T destination, double length) {
        graph.get(source).put(destination, length);
    }
    public Map<T, Double> edgesFrom(T node) {
        Map<T, Double> edges = graph.get(node);
        return Collections.unmodifiableMap(edges);
    }
    @Override public Iterator<T> iterator() {
        return graph.keySet().iterator();
    }
}
public class FindAllPaths<T> {
    public GraphFindAllPaths<T> graph;
    public FindAllPaths(GraphFindAllPaths<T> graph) {
        this.graph = graph;
    }
    public  Map<List<T>,Double> pathscost(T source, T destination) {
        Map<List<T>,Double> cmap = new HashMap<List<T>,Double>();
        List<List<T>> paths = new ArrayList<List<T>>();
        List<Double> totalCost = new ArrayList<Double>();
        double cost =0;
        rcost(source, destination, paths, new LinkedHashSet<T>(),totalCost,cost, new HashMap<T, Double>());
        for(int i=0;i<paths.size();i++){
            cmap.put(paths.get(i), totalCost.get(i));
        }
        return cmap;
    }
    private void rcost (T current, T destination, List<List<T>> paths, LinkedHashSet<T> path, List<Double> totalCost,Double cost, Map<T, Double> allEdges) {
        path.add(current);
        if(allEdges.get(current)!=null){
            cost= cost+allEdges.get(current);
        }
        if (current == destination) {
            cost= cost+allEdges.get(current);
            paths.add(new ArrayList<T>(path));
            cost= cost-allEdges.get(current);
            totalCost.add(cost);
            path.remove(current);
            return;
        }
        allEdges = graph.edgesFrom(current);
        final Set<T> edges  = graph.edgesFrom(current).keySet();
        for (T t : edges) {
            if (!path.contains(t)) {
                //System.out.println(t);
                rcost (t, destination, paths, path,totalCost,cost , allEdges);
            }
        }
        path.remove(current);
    }     


    public static void main(String[] args) {
        GraphFindAllPaths<String> graphFindAllPaths = new GraphFindAllPaths<String>();
        graphFindAllPaths.addNode("A");
        graphFindAllPaths.addNode("B");
        graphFindAllPaths.addNode("C");
        graphFindAllPaths.addNode("D");
        graphFindAllPaths.addNode("E");
        graphFindAllPaths.addEdge("A", "B", 12);
        graphFindAllPaths.addEdge("B", "A", 12);
        graphFindAllPaths.addEdge("A", "C", 13);
        graphFindAllPaths.addEdge("C", "A", 13);
        graphFindAllPaths.addEdge("A", "D", 11);
        graphFindAllPaths.addEdge("D", "A", 11);
        graphFindAllPaths.addEdge("A", "E", 8);
        graphFindAllPaths.addEdge("E", "A", 8);
        graphFindAllPaths.addEdge("B", "C", 3);
        graphFindAllPaths.addEdge("C", "B", 3);
        graphFindAllPaths.addEdge("E", "C", 2);
        graphFindAllPaths.addEdge("C", "E", 2);
        graphFindAllPaths.addEdge("D", "E", 7);
        graphFindAllPaths.addEdge("E", "D", 7);
        FindAllPaths<String> findAllPaths = new FindAllPaths<String>(graphFindAllPaths);
        int c=0;
        System.out.println("\nAll possible paths with total distance : ");
        double res=0.0;
        Map<List<String>,Double> pathWithCost = findAllPaths.pathscost("A", "E");
        for(Entry<List<String>, Double> s : pathWithCost.entrySet()){
        	c++;res=res+s.getValue();
            System.out.println(s);
        }
        System.out.println("Average of two points in the graph : "+(res/c));
    }
}