package xml;

import java.util.ArrayList;
import java.util.List;

public class NodeList {

    private final List<Node> nodeList = new ArrayList<Node>();

    public NodeList() {
    }

    public void addNodes(List<Node> nodes) {
        nodeList.addAll(nodes);
    }

    public void addNode(Node node) {
        nodeList.add(node);
    }

    public Node item(int position) {
        if (position > nodeList.size()-1 || position < 0){
            return null;
        }
        return nodeList.get(position);
    }

    public int size() {
        return nodeList.size();
    }
}
