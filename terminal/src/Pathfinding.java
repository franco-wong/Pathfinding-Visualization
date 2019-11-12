// import java.text.DecimalFormat;
import java.util.*;

class Pathfinding{
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("1. Dijkstras 2. A* : ");
        int input = in.nextInt();
        System.out.print("How big for the grid? ");
        int gridSize = in.nextInt();

        if(input == 1){
            Dijkstras dijk = new Dijkstras(gridSize);
        } else if(input == 2){
            AStar aStar = new AStar(gridSize);
        }
    }
}