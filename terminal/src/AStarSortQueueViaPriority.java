import java.util.*;
class AStarSortQueueViaPriority implements Comparator<AStarNode> {
    @Override
    public int compare(AStarNode n1, AStarNode n2) {
        return Integer.compare(n1.getCost(), n2.getCost());
    }
}