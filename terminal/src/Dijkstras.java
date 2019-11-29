import java.util.*;
class Dijkstras{
    // https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
    MyNode[][] grid;
    MyNode start;
    MyNode end;
    PriorityQueue<MyNode> queue = new PriorityQueue<MyNode>(new SortQueueViaPriority());
    int size;
    Dijkstras(int size){
        this.size = size;
        grid = new MyNode[size][size];

        createGrid();
        computeDijkstras();
        printGrid();
    }

    void createGrid(){
        // Creates the grid of MyNodes and sets all the edges
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(grid[i][j] == null){
                    grid[i][j] = new MyNode(i,j);
                }
                // To put "walls" to direct the algorithm
                if(j == 2 && i != 2){
                    grid[i][j].value = "W";
                }

                if(i-1 >= 0){
                    if(grid[i-1][j] == null){
                        grid[i-1][j] = new MyNode(i-1,j);
                    }
                    grid[i-1][j].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i-1][j]);
                }
                if(i+1 < size){
                    if(grid[i+1][j] == null){
                        grid[i+1][j] = new MyNode(i+1,j);
                    }
                    grid[i+1][j].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i+1][j]);
                }
                if(j-1 >= 0){
                    if(grid[i][j-1] == null){
                        grid[i][j-1] = new MyNode(i,j-1);
                    }
                    grid[i][j-1].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i][j-1]);
                }
                if(j+1 < size){
                    if(grid[i][j+1] == null){
                        grid[i][j+1] = new MyNode(i,j+1);
                    }
                    grid[i][j+1].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i][j+1]);
                }
            }
        }

        // hardcoding the start and the end nodes for now
        start = grid[0][0];
        start.costFromStart = 0;
        start.visited = true;
        start.value = "S";
        end = grid[4][4];
        end.value = "E";
    }

    boolean computeDijkstras(){
        MyNode currNode, nextNode;
        queue.add(start);
        // if the current node is the end, we backtrack using the node's parents to the start of the path
        while(queue.peek() != null){
            currNode = queue.poll();
            if(currNode.value == "E"){
                // backtrack
                MyNode parentNode = currNode.parent;
                while(parentNode.parent != null){
                    parentNode.value = "x";
                    if(parentNode.parent != null && parentNode.parent.parent != null && parentNode.x-1 == parentNode.parent.parent.x && parentNode.y-1 == parentNode.parent.parent.y){
                        parentNode = parentNode.parent.parent;
                    } else {
                        parentNode = parentNode.parent;
                    }
                }
                return true;
            }
            
            for(int i = 0; i < currNode.edgesCount; i++){
                nextNode = currNode.edges[i];
                if(nextNode.value == "W") continue;
                // for all the edges I have to check if the cost from the current node to the next node cost is less than, we update the cost and the parent node
                // if the node is unvisited, add to the priority queue, and update the visited boolean
                int tempCost = currNode.costFromStart + 1;
                if(tempCost < nextNode.costFromStart){
                    nextNode.costFromStart = tempCost;
                    nextNode.parent = currNode;
                }

                if(!nextNode.visited && nextNode.value != "S"){
                    queue.add(nextNode);
                    nextNode.visited = true;
                    
                }
            }
            if(currNode.value == "")
                        currNode.value = String.valueOf(currNode.getCost());
            printGrid();
            System.out.println("");
            System.out.println("");
        }
        return false;
    }

    void printGrid(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.printf("[%1s]",grid[i][j].value);
            }
            System.out.println("");
    }
}