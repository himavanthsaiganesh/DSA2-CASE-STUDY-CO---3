import java.util.*;

public class EduGraphCO3 {

    // Number of vertices
    private int vertices;

    // Adjacency Matrix
    private int[][] graph;

    // Constructor
    public EduGraphCO3(int vertices) {

        this.vertices = vertices;

        graph = new int[vertices][vertices];
    }

    // Add Edge
    void addEdge(int source,
                 int destination,
                 int weight) {

        graph[source][destination] = weight;
        graph[destination][source] = weight;
    }

    // ---------------- BFS ---------------- //

    void bfsTraversal(int startVertex) {

        boolean[] visited =
                new boolean[vertices];

        Queue<Integer> queue =
                new LinkedList<>();

        visited[startVertex] = true;

        queue.add(startVertex);

        System.out.println(
                "BFS Traversal:");

        while (!queue.isEmpty()) {

            int vertex = queue.poll();

            System.out.print(vertex + " ");

            for (int i = 0;
                 i < vertices;
                 i++) {

                if (graph[vertex][i] != 0 &&
                        !visited[i]) {

                    visited[i] = true;

                    queue.add(i);
                }
            }
        }

        System.out.println();
    }

    // ---------------- DFS ---------------- //

    void dfsTraversal(int startVertex) {

        boolean[] visited =
                new boolean[vertices];

        System.out.println(
                "\nDFS Traversal:");

        dfsHelper(startVertex,
                visited);

        System.out.println();
    }

    void dfsHelper(int vertex,
                   boolean[] visited) {

        visited[vertex] = true;

        System.out.print(vertex + " ");

        for (int i = 0;
             i < vertices;
             i++) {

            if (graph[vertex][i] != 0 &&
                    !visited[i]) {

                dfsHelper(i,
                        visited);
            }
        }
    }

    // ---------------- PRIM'S ALGORITHM ---------------- //

    void primMST() {

        int[] parent =
                new int[vertices];

        int[] key =
                new int[vertices];

        boolean[] mstSet =
                new boolean[vertices];

        Arrays.fill(key,
                Integer.MAX_VALUE);

        key[0] = 0;

        parent[0] = -1;

        for (int count = 0;
             count < vertices - 1;
             count++) {

            int u =
                    minKey(key,
                            mstSet);

            mstSet[u] = true;

            for (int v = 0;
                 v < vertices;
                 v++) {

                if (graph[u][v] != 0 &&
                        !mstSet[v] &&
                        graph[u][v] < key[v]) {

                    parent[v] = u;

                    key[v] = graph[u][v];
                }
            }
        }

        System.out.println(
                "\nPrim's Minimum Spanning Tree:");

        for (int i = 1;
             i < vertices;
             i++) {

            System.out.println(
                    parent[i] +
                    " - " + i +
                    " Weight: " +
                    graph[i][parent[i]]);
        }
    }

    int minKey(int[] key,
               boolean[] mstSet) {

        int min =
                Integer.MAX_VALUE;

        int minIndex = -1;

        for (int v = 0;
             v < vertices;
             v++) {

            if (!mstSet[v] &&
                    key[v] < min) {

                min = key[v];

                minIndex = v;
            }
        }

        return minIndex;
    }

    // ---------------- KRUSKAL'S ALGORITHM ---------------- //

    static class Edge
            implements Comparable<Edge> {

        int source,
                destination,
                weight;

        public int compareTo(Edge compareEdge) {

            return this.weight -
                    compareEdge.weight;
        }
    }

    int findParent(int[] parent,
                   int i) {

        if (parent[i] == i)
            return i;

        return findParent(parent,
                parent[i]);
    }

    void unionSets(int[] parent,
                   int x,
                   int y) {

        int xSet =
                findParent(parent,
                        x);

        int ySet =
                findParent(parent,
                        y);

        parent[xSet] = ySet;
    }

    void kruskalMST() {

        ArrayList<Edge> edges =
                new ArrayList<>();

        for (int i = 0;
             i < vertices;
             i++) {

            for (int j = i + 1;
                 j < vertices;
                 j++) {

                if (graph[i][j] != 0) {

                    Edge edge =
                            new Edge();

                    edge.source = i;
                    edge.destination = j;
                    edge.weight = graph[i][j];

                    edges.add(edge);
                }
            }
        }

        Collections.sort(edges);

        int[] parent =
                new int[vertices];

        for (int i = 0;
             i < vertices;
             i++) {

            parent[i] = i;
        }

        System.out.println(
                "\nKruskal's Minimum Spanning Tree:");

        for (Edge edge : edges) {

            int x =
                    findParent(parent,
                            edge.source);

            int y =
                    findParent(parent,
                            edge.destination);

            if (x != y) {

                System.out.println(
                        edge.source +
                        " - " +
                        edge.destination +
                        " Weight: " +
                        edge.weight);

                unionSets(parent,
                        x,
                        y);
            }
        }
    }

    // ---------------- MAIN METHOD ---------------- //

    public static void main(String[] args) {

        EduGraphCO3 graph =
                new EduGraphCO3(5);

        // Add Edges

        graph.addEdge(0,
                1,
                2);

        graph.addEdge(0,
                3,
                6);

        graph.addEdge(1,
                2,
                3);

        graph.addEdge(1,
                3,
                8);

        graph.addEdge(1,
                4,
                5);

        graph.addEdge(2,
                4,
                7);

        graph.addEdge(3,
                4,
                9);

        // BFS

        graph.bfsTraversal(0);

        // DFS

        graph.dfsTraversal(0);

        // Prim's MST

        graph.primMST();

        // Kruskal's MST

        graph.kruskalMST();
    }
}