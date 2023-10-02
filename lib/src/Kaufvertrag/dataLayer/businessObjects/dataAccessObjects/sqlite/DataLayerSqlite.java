package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML.WareDaoXml;

public class DataLayerSqlite implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        return new VertragspartnerDaoSqlite();
    }


    @Override
    public IDao<IWare, Long> getDaoWare() {
        return new WareDaoXml();
    }
}
