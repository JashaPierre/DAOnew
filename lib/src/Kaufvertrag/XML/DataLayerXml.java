package Kaufvertrag.XML;

import Kaufvertrag.Interfaces.IDao;
import Kaufvertrag.Interfaces.IDataLayer;
import Kaufvertrag.Interfaces.IVertragspartner;
import Kaufvertrag.Interfaces.IWare;

public class DataLayerXml implements IDataLayer {

    @Override
    public IDao<IVertragspartner, String> getDaoVertragspartner() {
        return null;
    }

    @Override
    public IDao<IWare, Long> getDaoWare() {
        return null;
    }

}
