package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.dataLayer.businessObjects.Vertragspartner;
import Kaufvertrag.exceptions.DaoException;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class VertragspartnerDaoSqlite implements IDao {
    @Override
    public Object create() {
        String vorname = null;
        String nachname = null;
        Scanner scanner = new Scanner(System.in);
//            String input = reader.readLine();


        if(vorname != null && nachname != null){
            return new Vertragspartner(vorname,nachname);
        }

        return null;
    }

    @Override
    public void create(Object objectToInsert) throws DaoException {

    }

    @Override
    public Object read(Object id) throws DaoException {
        return null;
    }

    @Override
    public List readAll() throws DaoException {
        return null;
    }

    @Override
    public void update(Object objectToUpdate) throws DaoException {

    }

    @Override
    public void delete(Object id) throws DaoException {

    }
}
