package Kaufvertrag.dataLayer.businessObjects.dataAccessObjects.sqlite;

import java.sql.*;

public class ConnectionManager {

    private String CLASSNAME;
    private String CONNECTIONSTRING;
    private boolean classLoaded;

    public Connection getNewConnection() {
        return null; //PLACEHOLDER
    }

    public Connection getExistingConnection() {
        return null; //PLACEHOLDER
    }

    public void close(ResultSet resultSet, Statement statement, Connection connection){
        resultSet = null; //PLACEHOLDER
        statement = null; //PLACEHOLDER
        connection = null; //PLACEHOLDER
    }


}
