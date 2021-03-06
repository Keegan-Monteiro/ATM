package atm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private static Connection conn;
    private static Statement stmt;

    public static Statement getStmt() {
        return stmt;
    }
    
    public DBConnection() {
        connect();
    }
                
    public static boolean connect() {
        String DB_URL = "jdbc:mysql://localhost/";
        String DB_NAME = "atm";
        String USER = "root";
        String PASS = "password";
        boolean connected;
        try {
            conn = DriverManager.getConnection(DB_URL + DB_NAME, USER, PASS);
            stmt = conn.createStatement();
            connected = true;
        } catch (SQLException ex) {
            System.out.println(ex);
            connected = false;
        }
        return connected;
    }
    
    public static boolean isClosed() throws SQLException {
        return conn.isClosed();
    }
    
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public static ResultSet SelectQuery(String sql) {
        ResultSet rs = null;
        try {
            if (isClosed()) {
                if (connect()) {
                    rs = stmt.executeQuery(sql);
                }
            }
            else 
                rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
    public static int SQLQuery(String sql) {
        int success = 0;
        try {
            if (isClosed()) {
                if (connect()) {
                    success = stmt.executeUpdate(sql);
                }
            }
            else
                success = stmt.executeUpdate(sql);
            if (success != 0)
                return (success);
        } catch (SQLException ex) {
            success = 0;
            return success;  
        }
        return success;
    }
}