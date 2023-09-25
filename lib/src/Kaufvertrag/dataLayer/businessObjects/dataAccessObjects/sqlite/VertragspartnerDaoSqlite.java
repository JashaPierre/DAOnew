package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.exceptions.DaoException;

import java.util.List;

public class VertragspartnerDaoSqlite implements IDao<IVertragspartner, String> {
    @Override
    public IVertragspartner create() {
        return null;
    }

    @Override
    public void create(IVertragspartner objectToInsert) throws DaoException {

    }

    @Override
    public IVertragspartner read(String id) throws DaoException {
        return null;
    }

    @Override
    public List readAll() throws DaoException {
        return null;
    }

    @Override
    public void update(IVertragspartner objectToUpdate) throws DaoException {

    }

    @Override
    public void delete(String id) throws DaoException {

    }
}
