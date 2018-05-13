package xml;

import dao.XMLDAO;
import dao.XMLDAOFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;

public class DocumentBuilder {
    private static final DocumentBuilder DOCUMENT_BUILDER = new DocumentBuilder();
    private static final ClassLoader CLASS_LOADER = XMLDAOFactory.class.getClassLoader();
    private static final XMLDAOFactory XMLDAO_FACTORY = XMLDAOFactory.getInstance();
    private static final XMLDAO XMLDAO = XMLDAO_FACTORY.getXmlDAO();
    private static RandomAccessFile randomAccessFile;
    private static Document document;

    private DocumentBuilder() {
    }

    public static DocumentBuilder getInstance() {
        return DOCUMENT_BUILDER;
    }

    public Document getDocument(String path) throws IOException {
        URL filePath = CLASS_LOADER.getResource(path);
        randomAccessFile = new RandomAccessFile(filePath.getFile(), "r");
        document = XMLDAO.firstNodeCreator(randomAccessFile);
        document.setFilePath(filePath);
        randomAccessFile.close();

        return document;
    }
}
