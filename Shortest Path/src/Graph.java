import java.io.*;
import java.util.*;

public class Graph {
    Vertex[] vertexes;
    private int V;
    private int E;

    public Graph(Scanner scan) {
        V = scan.nextInt();
        E = scan.nextInt();
        vertexes = new Vertex[V];
        for (int i = 0; i < vertexes.length; i++) {
            vertexes[i] = new Vertex(scan.nextInt(), scan.nextInt(), scan.nextInt());
        }
        for (int j = 0; j < E; j++) {
            int index = scan.nextInt();
            int index2 = scan.nextInt();
            vertexes[index].addEdge(index2);
            vertexes[index2].addEdge(index);
        }
    }

    public static void main(String[] args) {
        try {
            Graph graph = new Graph(new Scanner(new File("input6.txt")));

            //Testing if it works
            System.out.println(graph);

        } catch (IOException e) {
            System.out.println("File Input Error");
        }
    }

    public double distance(int from, int to) {
        return vertexes[from].findDistance(vertexes[to]);
    }

    public String toString() {
        String output = "Num of Vertexes: " + V + "\n" +
                "Num of Edges: " + E + "\n";
        for (Vertex v : vertexes) {
            output += v.toString();
        }
        return output;
    }
}