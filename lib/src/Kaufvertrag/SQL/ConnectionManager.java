package Kaufvertrag.SQL;

public class ConnectionManager {

    private String CLASSNAME;
    private String CONNECTIONSTRING;
    private boolean classLoaded;
    public Connection getNewConnection();
    public Connection getExistingConnection();
    public void close(ResultSet resultSet,Statement statement, Connection connection){
        resultSet = null;
        statement = null;
        connection = null;
    }


}
