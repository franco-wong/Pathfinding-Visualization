import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.Comparator;

import java.util.*;

public class Pathfinding implements ActionListener{
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
        width = 1000;
        height = 1000;
        inputMode = 0;
        hasStart = false;
        hasEnd = false;
        start = null;
        end = null;
        rows = (height-height%SIZE)/SIZE;
        cols = (width-width%SIZE)/SIZE;
        myFrame = new MyFrame(width, height, rows, cols, this);
        // create an array of these nodes
        grid = new Node[cols][rows];

        setEdges();
        setCostFromEnd();
        myFrame.repaint();
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
        if(hasStart && hasEnd){
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

                    if(!next.role.equals("Wall")){
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
        pQueue = new PriorityQueue<Node>(new HSortPQueue());
        if(hasStart && hasEnd){
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

                    if(next.role.equals("Wall")){
                        if(cost <= next.costFromStart){
                            next.costFromStart = cost;
                            next.costAddH = next.costFromStart + next.costFromEnd;
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
                if(grid[i][j].role != "End"){
                    grid[i][j].bVisited = false;
                    grid[i][j].costFromEnd = 999999999;
                }
            }
        }
    }

    void setEdges(){
        int tempCost;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j] = new Node(i, j, i*SIZE, j*SIZE, SIZE, this);
                myFrame.add(grid[i][j]);
                if(i-1 >= 0){
                    grid[i][j].addEdge(grid[i-1][j]);
                    grid[i-1][j].addEdge(grid[i][j]);
                }
                if(j-1 >= 0){
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

    public void actionPerformed(ActionEvent e){

    }

    public static void main(String[] args){
        new Pathfinding();
    }
}

class HSortPQueue implements Comparator<Node>{
    @Override
    public int compare(Node n1, Node n2){
        return Integer.compare(n1.getHFunction(), n2.getHFunction());
    }
}


class SortPQueue implements Comparator<Node>{
    @Override
    public int compare(Node n1, Node n2){
        return Integer.compare(n1.getCostFromStart(), n2.getCostFromStart());
    }
}