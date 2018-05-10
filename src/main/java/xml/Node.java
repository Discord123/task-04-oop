package xml;

public class Node {

    private String name;
    private String startTag;
    private String endTag;
    private String attribute;
    private long startPosition;
    private long endPosition;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        if (startPosition != node.startPosition) return false;
        if (endPosition != node.endPosition) return false;
        if (!name.equals(node.name)) return false;
        if (!startTag.equals(node.startTag)) return false;
        if (!endTag.equals(node.endTag)) return false;
        return attribute.equals(node.attribute);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + startTag.hashCode();
        result = 31 * result + endTag.hashCode();
        result = 31 * result + attribute.hashCode();
        result = 31 * result + (int) (startPosition ^ (startPosition >>> 32));
        result = 31 * result + (int) (endPosition ^ (endPosition >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("name='").append(name).append('\'');
        sb.append(", startTag='").append(startTag).append('\'');
        sb.append(", endTag='").append(endTag).append('\'');
        sb.append(", attribute='").append(attribute).append('\'');
        sb.append(", startPosition=").append(startPosition);
        sb.append(", endPosition=").append(endPosition);
        sb.append('}');
        return sb.toString();
    }
}
