package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;

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
