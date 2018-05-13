package xml;

import dao.XMLDAO;
import dao.XMLDAOFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

public class DocumentBuilder {
    private static final Logger LOGGER = LogManager.getLogger(DocumentBuilder.class);
    private static final DocumentBuilder DOCUMENT_BUILDER = new DocumentBuilder();
    private static final ClassLoader CLASS_LOADER = XMLDAOFactory.class.getClassLoader();
    private static final XMLDAOFactory XMLDAO_FACTORY = XMLDAOFactory.getInstance();
    private static final XMLDAO XMLDAO = XMLDAO_FACTORY.getXmlDAO();
    private static Document document;
    private URL filePath;

    private DocumentBuilder() {
    }

    public static DocumentBuilder getInstance() {
        return DOCUMENT_BUILDER;
    }

    public Document getDocument(String path) {
        filePath = CLASS_LOADER.getResource(path);

        try {
            document = XMLDAO.firstNodeCreator(filePath);
            document.setFilePath(filePath);
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.FATAL, "XML File was deleted.");
        } catch (IOException e) {
            LOGGER.log(Level.FATAL, "IOException");
        }

        return document;
    }
}
