package dao;

import dao.impl.XMLDAOImpl;

public class XMLDAOFactory {
    private static final XMLDAOFactory INSTANCE = new XMLDAOFactory();

    private final XMLDAO xmlDAO = new XMLDAOImpl();

    private XMLDAOFactory(){
    }

    public XMLDAO getXmlDAO(){
        return xmlDAO;
    }

    public static XMLDAOFactory getInstance(){
        return INSTANCE;
    }
}
