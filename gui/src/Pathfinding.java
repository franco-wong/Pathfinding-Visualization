import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;

import java.util.*;

public class Pathfinding{
    final int SIZE = 50;
    int inputMode, width, height, rows, cols;
    boolean hasStart, hasEnd;
    Node start, end;
    String algo = "Dijkstras"; // enumerators
    Node[][] grid;
    MyFrame myFrame;
    boolean running = false;

    Queue<Node> queue;
    PriorityQueue<Node> pQueue;

    Pathfinding(){
        width = 500;
        height = 500;
        inputMode = 0; // 1: start 2: wall 3: end
        hasStart = false;
        hasEnd = false;
        start = null;
        end = null;
        rows = height/SIZE;
        cols = width/SIZE;
        
        myFrame = new MyFrame(width, height, rows, cols, this);
        // create an array of these nodes
        grid = new Node[cols][rows];

        setEdges();
        setCostFromEnd();
    }

    public void runAlgo(){
        if(algo.equals("Dijkstras")){
            runDijkstras();
        } else if(algo.equals("A*")){
            runAStar();
        }
        running = true;
    }

    void runDijkstras(){
        resetNodes();
        pQueue = new PriorityQueue<Node>(new SortPQueue());
        if(start != null && end != null){
            pQueue.add(start);
            Node current, next;
            int cost;
            while(pQueue.peek() != null){
                current = pQueue.poll();
                if(!current.role.equals("Start") && !current.role.equals("End")) current.showAlgo();
                if(current.role.equals("End")){
                    Node parent = current.parent;
                    while(parent.parent != null){
                        parent.showPath();
                        parent = parent.parent;
                    }
                    break;
                }
                current.visited = true;
                cost = current.costFromStart + 1;
                for(int i = 0; i < current.edgesCount; i++){
                    next = current.edges[i];

                    if(next.role.equals("Wall")) continue;
                    else{
                        if(cost < next.costFromStart){
                            next.costFromStart = cost;
                            next.setParent(current);
                        }
                        if(!next.visited && !next.role.equals("Start")){
                            pQueue.add(next);
                            next.visited = true;
                        }
                    }
                }
            }
        }
    }

    void runAStar(){
        resetNodes();
        setCostFromEnd();
        pQueue = new PriorityQueue<Node>(new SortPQueue());
        if(start != null && end != null){
            pQueue.add(start);
            Node current, next;
            int cost;
            while(pQueue.peek() != null){
                current = pQueue.poll();
                if(!current.role.equals("Start") && !current.role.equals("End")) current.showAlgo();
                if(current.role.equals("End")){
                    Node parent = current.parent;
                    while(parent.parent != null){
                        System.out.println(current.hashCode());
                        parent.showPath();
                        parent = parent.parent;
                    }
                    break;
                }
                current.visited = true;
                cost = current.costFromStart + 1 + current.costFromEnd;
                for(int i = 0; i < current.edgesCount; i++){
                    next = current.edges[i];

                    if(next.role.equals("Wall")) continue;
                    else{
                        if(cost < next.costFromStart){
                            next.costFromStart = cost;
                            next.setParent(current);
                        }
                        if(!next.visited && !next.role.equals("Start")){
                            pQueue.add(next);
                            next.visited = true;
                        }
                    }
                }
            }
        }

    }

    void resetNodes(){
        if(running){
            for(int i = 0; i < rows; i++){
                for(int j = 0; j < cols; j++){
                    grid[i][j].visited = false;

                    if(grid[i][j].role.equals(""))
                        grid[i][j].reset();
                }
            }
        }
    }

    void resetBVisited(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j].bVisited = false;
                grid[i][j].costFromEnd = 999999999;
            }
        }
    }

    void setEdges(){
        int tempCost;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(grid[i][j] == null){
                    grid[i][j] = new Node(i, j, i*SIZE, j*SIZE, SIZE, this);
                    myFrame.add(grid[i][j]);
                }
                if(i-1 >= 0){
                    if(grid[i-1][j] == null){
                        grid[i-1][j] = new Node(i-1, j, (i-1)*SIZE, j*SIZE, SIZE, this);
                        myFrame.add(grid[i-1][j]);
                    }
                    grid[i][j].addEdge(grid[i-1][j]);
                    grid[i-1][j].addEdge(grid[i][j]);
                }
                if(j-1 >= 0){
                    if(grid[i][j-1] == null){
                        grid[i][j-1] = new Node(i, j-1, i*SIZE, (j-1)*SIZE, SIZE, this);
                        myFrame.add(grid[i][j-1]);
                    }
                    grid[i][j].addEdge(grid[i][j-1]);
                    grid[i][j-1].addEdge(grid[i][j]);
                }
            }
        }
    }

    void setCostFromEnd(){
        if(running) resetBVisited();
        if(end != null){
            queue = new LinkedList<Node>();
            queue.add(end);
            Node current, next;
            int temp;
            while (queue.peek() != null){
                current = queue.poll();
                temp = current.costFromEnd+1;
                for(int i = 0; i < current.edgesCount; i++){
                    next = current.edges[i];
                    if(next.costFromEnd > temp){
                        next.costFromEnd = temp;
                        queue.add(next);
                    }
                    if(!next.bVisited){
                        queue.add(next);
                        next.bVisited = true;
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        new Pathfinding();
    }
}

class SortPQueue implements Comparator<Node>{
    @Override
    public int compare(Node n1, Node n2){
        return Integer.compare(n1.getCostFromStart(), n2.getCostFromStart());
    }
}