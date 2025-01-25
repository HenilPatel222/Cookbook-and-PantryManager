import java.util.ArrayList;
import java.util.List;
/*
Name:Henil Patel
Student No: 7084577
Date: 25/3/2024

Assigment  3
 */
public class Graph {
    private List<List<Integer>> adjList; // Adjacency list to represent graph
    public Graph(int vertices) {
        adjList = new ArrayList<>();
        for(int i=0; i<=vertices; i++){
            adjList.add(new ArrayList<>());
        }
    }
    public void addEdge(int source,int destination){
        adjList.get(source).add(destination);
    }


}
