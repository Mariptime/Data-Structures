import java.io.*;
import java.util.*;

public class Dijkstra {

    private Graph graph;

    public Dijkstra(Graph graph) {
        this.graph = graph;
    }

    public static void main(String[] args) {
        try {
            Graph g = new Graph(new Scanner(new File("input6.txt")));
            Dijkstra dike = new Dijkstra(g);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    public void dijkstra(int source, int destination) {
        PriorityQueue<Vertex> que = new PriorityQueue<>();
        Vertex initial = graph.vertexes[source];
        que.add(graph.vertexes[source]);
        while (!que.isEmpty()) {
            Vertex vertex = que.poll();
            // not necessarily using vars from Vertex.java
            double previousDistance = Double.POSITIVE_INFINITY;
            for (int index = 0; index < graph.vertexes.length; index++) {
                Vertex[] vexes = graph.vertexes;
                if (index == 0) {
                    previousDistance = initial.findDistance(vexes[index]);
                    continue;
                }
                double minDistance = initial.findDistance(vexes[index]);
                if (minDistance < previousDistance) {
                    que.remove(vertex);
                    que.add(vexes[index]);
                    graph.vertexes[destination].setDistance(minDistance);
                }
            }
        }
    }

    public double distance(int source, int destination) {
        dijkstra(source, destination);
        double output = 0.0;
        for (Vertex v : graph.vertexes) {
            output += v.getDistance();
        }
        return output;
    }
}