package service;

import service.impl.XMLServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final XMLService xmlService = new XMLServiceImpl();

    private ServiceFactory(){}

    public XMLService getXmlService(){
        return xmlService;
    }

    public static ServiceFactory getInstance(){
        return instance;
    }
}
