package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;
import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.exceptions.DaoException;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class VertragspartnerDaoXml implements IDao {

    @Override
    public Object create() {
        ServiceXml serviceXml = new ServiceXml(this, "Vertragspartner");
        return serviceXml;
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
