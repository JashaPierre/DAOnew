package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects;

import Kaufvertrag.exceptions.DaoException;
import java.util.List;

    public interface IDao<T,K> {
        T creat();
        void create(T objectToInsert) throws DaoException;
        T read(K id) throws DaoException;
        List<T> readAll() throws DaoException;
        void update(T objectToUpdate) throws DaoException;
        void delete(K id) throws DaoException;
    }


