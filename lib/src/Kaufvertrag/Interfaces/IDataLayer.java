package Kaufvertrag.Interfaces;

public interface IDataLayer {
    IDao<IVertragspartner,String> getDaoVertragspartner();
    IDao<IWare,Long> getDaoWare();
}
