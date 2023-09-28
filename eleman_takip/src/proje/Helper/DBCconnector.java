package proje.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCconnector {


    // database bağlantı kodları
    private Connection connect = null;

    public Connection coonectDB() throws SQLException {
        this.connect = DriverManager.getConnection(Config.DB_URL,Config.DB_USERNAME,Config.DB_PASSWORD);
        return this.connect;
    }

    public static Connection getInstance() throws SQLException {
        DBCconnector db = new DBCconnector();
        return db.coonectDB();
    }

}
