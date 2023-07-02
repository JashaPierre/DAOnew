package dataLayer.businessObjects.dataAccessObjects.sqlite;

import dataLayer.businessObjects.dataAccessObjects.IDao;
import dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import businessObjects.IVertragspartner;
import businessObjects.IWare;

public class DataLayerSqlite implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        return null;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        return null;
    }
}
