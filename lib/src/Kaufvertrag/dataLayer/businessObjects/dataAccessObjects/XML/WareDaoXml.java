package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;

import java.util.List;
import java.util.Scanner;

/**
 * Soll IWare später benutzen.
 * */
public class WareDaoXml implements IDao {
    @Override
    public Object create() {
       return new WareDaoXml();
    }

    @Override
    public void create(Object objectToInsert) throws DaoException {

    }

    @Override
    public Object read(Object id) throws DaoException {
        return null; //PLACEHOLDER
    }

    @Override
    public List readAll() throws DaoException {
        return null; //PLACEHOLDER
    }

    @Override
    public void update(Object objectToUpdate) throws DaoException {
    }

    @Override
    public void delete(Object id) throws DaoException {

    }
}
