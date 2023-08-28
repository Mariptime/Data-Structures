import java.util.*;

public class Vertex implements Comparable<Vertex> {

    private double distance = Double.POSITIVE_INFINITY;

    private int ID;
    private int x;
    private int y;

    private List<Integer> edges = new ArrayList<>();
    private boolean visited = false;

    public Vertex(int ID, int x, int y) {
        this.ID = ID;
        this.x = x;
        this.y = y;
    }

    public void addEdge(int ID) {
        edges.add(ID);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double findDistance(Vertex v2) {
        return findDistance(this.x, v2.x, this.y, v2.y);
    }

    public double findDistance(int x1, int x2, int y1, int y2) {
        return Math.pow((Math.pow(x1 + x2, 2) + Math.pow(y1 + y2, 2)), 0.5);
    }

    public String toString() {
        return "The following Variables are as follows:\n" +
                "x: " + x + "\n" +
                "y: " + y + "\n" +
                "ID: " + ID + "\n" +
                "Edges: " + edges.toString() + "\n" +
                "isVisited: " + visited + "\n" +
                "Distance: " + distance + "\n";
    }

    @Override
    public int compareTo(Vertex other) {
        if (distance == other.distance)
            return Integer.compare(ID, other.ID);
        return Double.compare(distance, other.distance);
    }
}