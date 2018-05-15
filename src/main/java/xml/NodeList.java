package xml;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NodeList implements Serializable{

    private static final long serialVersionUID = 5336559750349168776L;

    private final List<Node> nodeList = new ArrayList<Node>();
    private URL filePath;

    public NodeList() {
    }

    public void setFilePath(URL filePath) {
        this.filePath = filePath;
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

        Node node = nodeList.get(position);

        if(node.getFilePath() == null && filePath != null) {
            node.setFilePath(filePath);
        }

        return node;
    }

    public int size() {
        return nodeList.size();
    }
}
