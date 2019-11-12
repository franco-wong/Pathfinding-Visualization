import java.util.*;
class AStar{
    // https://en.wikipedia.org/wiki/A*_search_algorithm
    AStarNode[][] grid;
    AStarNode start;
    AStarNode end;
    PriorityQueue<AStarNode> pqueue = new PriorityQueue<AStarNode>(new AStarSortQueueViaPriority());
    int size;

    AStar(int size){
        this.size = size;
        grid = new AStarNode[size][size];

        createGrid();
        setDistance();
        computeAStar();
        printGrid();
    }

    void createGrid(){
        // Creates the grid of MyNodes and sets all the edges
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(grid[i][j] == null){
                    grid[i][j] = new AStarNode(i,j);
                }
                // To put "walls" to direct the algorithm
                if(j == 2 && i != 1){
                    grid[i][j].value = "W";
                }

                if(i-1 >= 0){
                    if(grid[i-1][j] == null){
                        grid[i-1][j] = new AStarNode(i-1,j);
                    }
                    grid[i-1][j].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i-1][j]);
                }
                if(i+1 < size){
                    if(grid[i+1][j] == null){
                        grid[i+1][j] = new AStarNode(i+1,j);
                    }
                    grid[i+1][j].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i+1][j]);
                }
                if(j-1 >= 0){
                    if(grid[i][j-1] == null){
                        grid[i][j-1] = new AStarNode(i,j-1);
                    }
                    grid[i][j-1].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i][j-1]);
                }
                if(j+1 < size){
                    if(grid[i][j+1] == null){
                        grid[i][j+1] = new AStarNode(i,j+1);
                    }
                    grid[i][j+1].addEdge(grid[i][j]);
                    grid[i][j].addEdge(grid[i][j+1]);
                }
            }
        }
        // hardcoding the start and the end nodes for now
        start = grid[1][0];
        start.costFromStart = 0;
        start.visited = true;
        start.value = "S";
        end = grid[9][9];
        end.value = "E";
    }

    void setDistance(){
        Queue<AStarNode> queue = new LinkedList<AStarNode>();
        queue.add(end);
        end.distanceFromEnd = 0;
        AStarNode currNode, nextNode;

        while(queue.peek() != null){
            currNode = queue.poll();
            for(int i = 0; i < currNode.edgesCount; i++){
                nextNode = currNode.edges[i];
                if(nextNode.value == "W") continue;
                nextNode.setDistance(currNode.getDistance()+1);

                if(!nextNode.getBackVisited()){
                    nextNode.setBackVisited();
                    queue.add(nextNode);
                }
            }

            currNode.setBackVisited();
        }
    }

    boolean computeAStar(){
        AStarNode currNode, nextNode;
        pqueue.add(start);
        // if the current node is the end, we backtrack using the node's parents to the start of the path
        while(pqueue.peek() != null){
            currNode = pqueue.poll();
            if(currNode.value == "E"){
                System.out.println("found end node");
                // backtrack
                AStarNode parentNode = currNode.parent;
                while(parentNode.parent != null){
                    parentNode.value = "x";
                    if(parentNode.parent != null && parentNode.parent.parent != null && parentNode.x-1 == parentNode.parent.parent.x && parentNode.y-1 == parentNode.parent.parent.y){
                        parentNode = parentNode.parent.parent;
                    } else {
                        parentNode = parentNode.parent;
                    }
                }
                return true;
            } else {
                
                for(int i = 0; i < currNode.edgesCount; i++){
                    nextNode = currNode.edges[i];
                    if(nextNode.value == "W") continue;
                    // for all the edges I have to check if the cost from the current node to the next node cost is less than, we update the cost and the parent node
                    // if the node is unvisited, add to the priority queue, and update the visited boolean
                    int tempCost = currNode.costFromStart + 1 + nextNode.getDistance();
                    if(tempCost < nextNode.costFromStart){
                        nextNode.costFromStart = tempCost;
                        nextNode.parent = currNode;
                    }

                    if(!nextNode.visited && nextNode.value != "S"){
                        pqueue.add(nextNode);
                        nextNode.visited = true;
                    }
                }
                if(currNode.value == "") currNode.value = "o";
                printGrid();
                System.out.println("");
                System.out.println("");
            }
        }
        return false;
    }

    void printGrid(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                System.out.printf("[%3s]",grid[i][j].value);
            }
            System.out.println("");
        }
    }
}