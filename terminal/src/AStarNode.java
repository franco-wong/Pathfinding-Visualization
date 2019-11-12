class AStarNode extends MyNode{
    int distanceFromEnd;
    AStarNode[] edges;
    AStarNode parent;
    boolean backVisited;
    AStarNode(int x, int y){
        super(x, y);
        distanceFromEnd = 999999999;
        edges = new AStarNode[super.SIZE];
        super.edgesCount = 0;
        backVisited = false;
    }
    
    int getDistance(){
        return distanceFromEnd;
    }
    
    void setDistance(int distanceFromEnd){
        if(this.distanceFromEnd > distanceFromEnd)
            this.distanceFromEnd = distanceFromEnd;
    }

    void addEdge(AStarNode node){
        boolean found = false;
        for(int i = 0; i < super.edgesCount; i++){
            if(edges[i].equals(node)){
                found = true;
                break;
            }
        }

        if(super.edgesCount < 4 && found == false){
            edges[super.edgesCount] = node;
            super.edgesCount++;
        }
    }

    void setParent(AStarNode parent){
        this.parent = parent;
    }

    void setBackVisited(){
        backVisited = true;
    }

    boolean getBackVisited(){
        return backVisited;
    }
}