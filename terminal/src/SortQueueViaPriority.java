import java.util.*;

class SortQueueViaPriority implements Comparator<MyNode> {
    @Override
    public int compare(MyNode n1, MyNode n2) {
        return Integer.compare(n1.getCost(), n2.getCost());
    }
}