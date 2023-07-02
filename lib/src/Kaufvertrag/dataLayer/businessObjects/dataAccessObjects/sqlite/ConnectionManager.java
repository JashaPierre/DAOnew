package dataLayer.businessObjects.dataAccessObjects.sqlite;

import java.sql.*;

public class ConnectionManager {

    private String CLASSNAME;
    private String CONNECTIONSTRING;
    private boolean classLoaded;

    public Connection getNewConnection() {
        return null;
    }

    public Connection getExistingConnection() {
        return null;
    }

    public void close(ResultSet resultSet, Statement statement, Connection connection){
        resultSet = null;
        statement = null;
        connection = null;
    }


}
