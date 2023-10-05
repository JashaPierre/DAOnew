package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.XML;

import Kaufvertrag.businessObjects.IVertragspartner;
import Kaufvertrag.businessObjects.IWare;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDao;
import Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.IDataLayer;

public class DataLayerXml implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        return new VertragspartnerDaoXml();
    }
    @Override
    public IDao<IWare, Long> getDaoWare() {
        return new WareDaoXml();
    }

}
