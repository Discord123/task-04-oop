package xml;

import dao.XMLDAO;
import dao.XMLDAOFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;

public class Document implements Serializable{

    private static final long serialVersionUID = -3255677712128420511L;
    private static final Logger LOGGER = LogManager.getLogger(Document.class);
    private static final XMLDAOFactory XMLDAO_FACTORY = XMLDAOFactory.getInstance();
    private static final XMLDAO XMLDAO = XMLDAO_FACTORY.getXmlDAO();

    private String information;
    private Node firstNode;
    private URL filePath;
    private NodeList nodeList;

    public Document() {
    }

    public Document(String information, Node firstNode) {
        this.information = information;
        this.firstNode = firstNode;
    }

    public String getInformation() { return information; }

    public void setFilePath(URL filePath) {
        this.filePath = filePath;
    }

    public NodeList getChildNodes() throws IOException {
        try {
            nodeList = XMLDAO.getChildList(firstNode, filePath);
            nodeList.setFilePath(filePath);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.FATAL, "XML File was deleted.");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "IOException");
        }

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
        if (filePath != null ? !filePath.equals(document.filePath) : document.filePath != null) return false;
        return nodeList != null ? nodeList.equals(document.nodeList) : document.nodeList == null;
    }

    @Override
    public int hashCode() {
        int result = information != null ? information.hashCode() : 0;
        result = 31 * result + (firstNode != null ? firstNode.hashCode() : 0);
        result = 31 * result + (filePath != null ? filePath.hashCode() : 0);
        result = 31 * result + (nodeList != null ? nodeList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Document{");
        sb.append("information='").append(information).append('\'');
        sb.append(", firstNode=").append(firstNode);
        sb.append(", filePath=").append(filePath);
        sb.append(", nodeList=").append(nodeList);
        sb.append('}');
        return sb.toString();
    }
}
