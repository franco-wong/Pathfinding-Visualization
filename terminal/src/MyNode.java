public class MyNode{
    MyNode[] edges;
    MyNode parent;
    boolean visited;
    final int SIZE = 4;
    int edgesCount;
    int weight;
    int cost;
    String value;
    int x, y;
    int costFromStart;
    // can set the weight later
    MyNode(int x, int y){
        parent = null;
        edges = new MyNode[SIZE];
        visited = false;
        edgesCount = 0; // how many edges there are
        this.x = x; // the x position in the grid
        this.y = y; // the y position in the grid
        value = ""; // to mark the path from the start to the end
        weight = 1; // the distance(?)
        costFromStart = 999999999; // the cost of the path from the start to the current node
    }

    void addEdge(MyNode node){
        boolean found = false;
        for(int i = 0; i < edgesCount; i++){
            if(edges[i].equals(node)){
                found = true;
                break;
            }
        }

        if(edgesCount < 4 && found == false){
            edges[edgesCount] = node;
            edgesCount++;
        }
    }
    
    void setParent(MyNode parent){
        this.parent = parent;
    }

    void setValue(String value){
        this.value = value;
    }

    void setVisited(){
        visited = true;
        // value = "o";
    }

    int getCost(){
        return costFromStart;
    }
}