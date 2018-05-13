package xml;

import dao.XMLDAO;
import dao.XMLDAOFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

public class Document {

    private static final XMLDAOFactory XMLDAO_FACTORY = XMLDAOFactory.getInstance();
    private static final XMLDAO XMLDAO = XMLDAO_FACTORY.getXmlDAO();
    private String information;
    private Node firstNode;
    private RandomAccessFile randomAccessFile;
    private URL filePath;
    private NodeList nodeList;

    public Document(String information, Node firstNode) {
        this.information = information;
        this.firstNode = firstNode;
    }

    public void setFilePath(URL filePath) {
        this.filePath = filePath;
    }

    public NodeList getChildNodes() throws IOException {
        randomAccessFile = new RandomAccessFile(filePath.getFile(),"r");
        nodeList = XMLDAO.getChildList(firstNode, randomAccessFile);
        nodeList.setFilePath(filePath);
        return nodeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (information != null ? !information.equals(document.information) : document.information != null)
            return false;
        if (firstNode != null ? !firstNode.equals(document.firstNode) : document.firstNode != null) return false;
        if (randomAccessFile != null ? !randomAccessFile.equals(document.randomAccessFile) : document.randomAccessFile != null)
            return false;
        return filePath != null ? filePath.equals(document.filePath) : document.filePath == null;
    }

    @Override
    public int hashCode() {
        int result = information != null ? information.hashCode() : 0;
        result = 31 * result + (firstNode != null ? firstNode.hashCode() : 0);
        result = 31 * result + (randomAccessFile != null ? randomAccessFile.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Document{");
        sb.append("information='").append(information).append('\'');
        sb.append(", firstNode=").append(firstNode);
        sb.append(", randomAccessFile=").append(randomAccessFile);
        sb.append('}');
        return sb.toString();
    }
}
