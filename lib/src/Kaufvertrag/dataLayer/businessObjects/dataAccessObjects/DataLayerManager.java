package dataLayer.businessObjects.dataAccessObjects;

public class DataLayerManager {
    private DataLayerManager instance;
    private String persistenceType;
    private DataLayerManager(){}

    public DataLayerManager getInstance(){
        return null;
    }
    public IDataLayer getDataLayer() {
        return null;
    }

    String readPersistenceType(){
        return null;
    }
}
