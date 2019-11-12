import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.BorderFactory;

class Node extends JLabel implements MouseListener{
    Node[] edges;
    int edgesCount;
    final int MAX_EDGES = 4;
    int x, y, width, height;
    Pathfinding pathfinding;
    String role = "";

    Node parent;
    int costFromStart, costFromEnd;
    final int DISTANCE = 1;

    boolean visited = false;
    boolean bVisited = false;

    Node(int x, int y, int xpos, int ypos, int size, Pathfinding pathfinding){
        this.isOpaque();
        this.setBackground(Color.WHITE);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.pathfinding = pathfinding;
        edges = new Node[MAX_EDGES];
        edgesCount = 0;
        parent = null;
        costFromStart = 999999999;
        costFromEnd = 999999999;
        this.x = x;
        this.y = y;
        this.setBounds(xpos, ypos, size, size);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setOpaque(true);
        this.addMouseListener(this);
    }

    boolean addEdge(Node edge){
        for(int i = 0; i < edgesCount; i++){
            if(edges[i] == edge){
                return true;
            }
        }
        edges[edgesCount] = edge;
        edgesCount++;
        return false;
    }

    void reset(){
        if(!role.equals("start")){
            this.setBackground(Color.WHITE);
            costFromStart = 999999999;
        }
        if(!role.equals("end"))
            costFromEnd = 999999999;
        parent = null;
    }

    public int getCostFromStart(){
        return costFromStart;
    }

    public int getCostFromEnd(){
        return costFromEnd;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }

    void showPath(){
        this.setBackground(Color.RED);
    }

    void showAlgo(){
        this.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    public void mouseExited(MouseEvent e){
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }

    @Override
    public void mouseReleased(MouseEvent e){
    }

    @Override
    public void mousePressed(MouseEvent e){
        if(pathfinding.inputMode == 1){
            if(pathfinding.hasStart == false && pathfinding.end != this){
                this.setText("S");
                role = "Start";
                pathfinding.hasStart = true;
                pathfinding.start = this;
                costFromStart = 0;
                setBackground(Color.orange);
            }else if(pathfinding.start == this){
                pathfinding.start = null;
                pathfinding.hasStart = false;
                this.setText("");
                setBackground(Color.WHITE);
                costFromStart = 999999999;
                pathfinding.resetNodes();
            }
            
        }else if(pathfinding.inputMode == 2){
            // if this is not start or end, do something
            if(role != "Start" && role != "End"){
                if(role == ""){
                    this.setText("W");
                    role = "Wall";
                    this.setBackground(Color.GRAY);
                }else {
                    this.setText("");
                    role = "";
                    this.setBackground(Color.WHITE);
                }
                if(pathfinding.running)
                    pathfinding.runAlgo();
            }
            
        }else if(pathfinding.inputMode == 3){
            if(pathfinding.hasEnd == false && pathfinding.start != this){
                role = "End";
                this.setText("E");
                pathfinding.hasEnd = true;
                pathfinding.end = this;
                costFromEnd = 0;
                pathfinding.setCostFromEnd();
                setBackground(Color.orange);
            }else if(pathfinding.end == this){
                pathfinding.end = null;
                pathfinding.hasEnd = false;
                this.setText("");
                setBackground(Color.WHITE);
                costFromEnd = 999999999;
                pathfinding.resetBVisited();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e){
    }
}