package xml;

import main.Main;

public class Node {

    private String name;
    private String startTag;
    private String endTag;
    private String attribute;
    private NodeType nodeType;
    private String textContent;
    private long startPosition;
    private long endPosition;
    private long nextLinePosition;

    public Node() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTag() {
        return startTag;
    }

    public void setStartTag(String startTag) {
        this.startTag = startTag;
    }

    public String getEndTag() {
        return endTag;
    }

    public void setEndTag(String endTag) {
        this.endTag = endTag;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    public long getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(long endPosition) {
        this.endPosition = endPosition;
    }

    public long getNextLinePosition() {
        return nextLinePosition;
    }

    public void setNextLinePosition(long nextLinePosition) {
        this.nextLinePosition = nextLinePosition;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public NodeList getChildList(){
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (startPosition != node.startPosition) return false;
        if (endPosition != node.endPosition) return false;
        if (nextLinePosition != node.nextLinePosition) return false;
        if (!name.equals(node.name)) return false;
        if (!startTag.equals(node.startTag)) return false;
        if (!endTag.equals(node.endTag)) return false;
        if (!attribute.equals(node.attribute)) return false;
        if (nodeType != node.nodeType) return false;
        return textContent.equals(node.textContent);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startTag.hashCode();
        result = 31 * result + endTag.hashCode();
        result = 31 * result + attribute.hashCode();
        result = 31 * result + nodeType.hashCode();
        result = 31 * result + textContent.hashCode();
        result = 31 * result + (int) (startPosition ^ (startPosition >>> 32));
        result = 31 * result + (int) (endPosition ^ (endPosition >>> 32));
        result = 31 * result + (int) (nextLinePosition ^ (nextLinePosition >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("name='").append(name).append('\'');
        sb.append(", startTag='").append(startTag).append('\'');
        sb.append(", endTag='").append(endTag).append('\'');
        sb.append(", attribute='").append(attribute).append('\'');
        sb.append(", nodeType=").append(nodeType);
        sb.append(", textContent='").append(textContent).append('\'');
        sb.append(", startPosition=").append(startPosition);
        sb.append(", endPosition=").append(endPosition);
        sb.append(", nextLinePosition=").append(nextLinePosition);
        sb.append('}');
        return sb.toString();
    }
}
