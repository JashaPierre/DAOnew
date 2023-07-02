package dataLayer.businessObjects.dataAccessObjects.XML;

import businessObjects.IVertragspartner;
import businessObjects.IWare;
import dataLayer.businessObjects.dataAccessObjects.IDao;
import dataLayer.businessObjects.dataAccessObjects.IDataLayer;

public class DataLayerXml implements IDataLayer {

    @Override
    public dataLayer.businessObjects.dataAccessObjects.IDao<IVertragspartner, String> getDaoVertragspartner() {
        return null;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        return null;
    }

}
