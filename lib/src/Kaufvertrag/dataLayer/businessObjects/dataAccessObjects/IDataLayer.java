package dataLayer.businessObjects.dataAccessObjects;

import businessObjects.IVertragspartner;
import businessObjects.IWare;

public interface IDataLayer {
    IDao<IVertragspartner,String> getDaoVertragspartner();
    IDao<IWare,Long> getDaoWare();
}
