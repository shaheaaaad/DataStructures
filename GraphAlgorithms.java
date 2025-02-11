import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.List;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue; //// OIS THIS A VALID IMPORT
import java.util.HashMap;


/**
 * Your implementation of various different graph algorithms.
 *
 * @author Shahd Bargouthi
 * @version 1.0
 * @userid sbargouthi3
 * @GTID 90387889
 *
 * Collaborators:
 *
 * Resources:
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) { /// is this how we check?
            throw new IllegalArgumentException("The input is null! TRY AGAIN");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start is not in the graph. Try again^^");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<Vertex<T>> queue = new LinkedList<>();
        List<Vertex<T>> traversalList = new LinkedList<>();
        queue.add(start);
        visitedSet.add(start);
        traversalList.add(start);
        while (!queue.isEmpty() && !(traversalList.size() == graph.getVertices().size())) {
            Vertex<T> v = queue.remove();
            for (VertexDistance<T> vD : graph.getAdjList().get(v)) {
                if (!visitedSet.contains(vD.getVertex())) {
                    visitedSet.add(vD.getVertex());
                    queue.add(vD.getVertex());
                    traversalList.add(vD.getVertex());
                }
            }
        }

        return traversalList;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) { /// is this how we check?
            throw new IllegalArgumentException("The input is null! TRY AGAIN");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start is not in the graph. Try again^^");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> traversalList = new LinkedList<>();
        dfsHelper(start, graph, traversalList, visitedSet);
        return traversalList;


    }

    /**
     * This is  DFS helper method that recursively carries out a depth search, starting at curr.
     * @param start current curr.
     * @param graph the graph to be traversed
     * @param traversalList the traversal.
     * @param visitedSet vertices that have been visited
     * @param <T> the generic typing of the data
     */
    private static <T> void dfsHelper(Vertex<T> start, Graph<T> graph,
                                      List<Vertex<T>> traversalList, Set<Vertex<T>> visitedSet) {

        visitedSet.add(start);
        traversalList.add(start);
        for (VertexDistance<T> vD : graph.getAdjList().get(start)) {
            if (!visitedSet.contains(vD.getVertex())) {
                dfsHelper(vD.getVertex(), graph, traversalList, visitedSet);
            }
        }

    }




    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) { /// is this how we check?
            throw new IllegalArgumentException("The input is null! TRY AGAIN");
        }
        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start is not in the graph. Try again^^");
        }
        Set<Vertex<T>> visitedSet = new HashSet<>();
        Queue<VertexDistance<T>> priorityQueue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>(); // why do i need to import hashmap
        // what should the initial size be
        for (Vertex<T> vertex : graph.getVertices()) {
            if (vertex.equals(start)) {
                distanceMap.put(vertex, 0);
                continue;
            }
            distanceMap.put(vertex, Integer.MAX_VALUE);
        }
        priorityQueue.add(new VertexDistance<>(start, 0));
        while (visitedSet.size() < graph.getVertices().size() && !priorityQueue.isEmpty()) {
            VertexDistance<T> cursor = priorityQueue.remove();
            visitedSet.add(cursor.getVertex());
            for (VertexDistance<T> vertexDistance : graph.getAdjList().get(cursor.getVertex())) {
                int x = cursor.getDistance() + vertexDistance.getDistance();
                if (!visitedSet.contains(vertexDistance.getVertex())
                        && distanceMap.get(vertexDistance.getVertex()) > x) {
                    distanceMap.put(vertexDistance.getVertex(), x);
                    priorityQueue.add(new VertexDistance<>(vertexDistance.getVertex(), x));
                }
            }

        }
        return distanceMap;


    }

    /**
     * Runs Kruskal's algorithm on the given graph and returns the Minimal
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you. A Disjoint Set will keep track of which vertices are
     * connected given the edges in your current MST, allowing you to easily
     * figure out whether adding an edge will create a cycle. Refer
     * to the DisjointSet and DisjointSetNode classes that
     * have been provided to you for more information.
     *
     * An MST should NOT have self-loops or parallel edges.
     *
     * By using the Disjoint Set provided, you can avoid adding self-loops and
     * parallel edges into the MST.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Set, and any class that implements the aforementioned
     * interfaces.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param graph the graph we are applying Kruskals to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null
     */
    public static <T> Set<Edge<T>> kruskals(Graph<T> graph) {
        if (graph == null) { /// is this how we check?
            throw new IllegalArgumentException("The input graph is null! TRY AGAIN");
        }
        if (graph.getEdges().isEmpty()) {
            //disconnected
            return null;
        }
        DisjointSet<Vertex<T>> disjointSet = new DisjointSet<>();
        Set<Edge<T>> edgeSetMST = new HashSet<>();
        Queue<Edge<T>> priorityQueue = new PriorityQueue<>(graph.getEdges());


        priorityQueue.addAll(graph.getEdges());

        while (!priorityQueue.isEmpty() && edgeSetMST.size() < 2 * (graph.getVertices().size() - 1)) { // n - 1
            Edge<T> curr = priorityQueue.remove();
            Vertex<T> u = curr.getU();
            Vertex<T> v = curr.getV();
            if (disjointSet.find(u) != disjointSet.find(v)) { // are the root nodes equal
                disjointSet.union(disjointSet.find(u), disjointSet.find(v));
                edgeSetMST.add(curr);
                Edge<T> oppEdge = new Edge<>(v, u, curr.getWeight());
                edgeSetMST.add(oppEdge);
            }
        }
        if (edgeSetMST.size() < 2 * (graph.getVertices().size() - 1)) {
            return null;
        }
        return edgeSetMST;
    }
}
