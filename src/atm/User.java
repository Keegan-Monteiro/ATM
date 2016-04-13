package atm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {
    private int accountNumber;
    private int pin;
    private String name;
    
    public void createUser(String name, int pin) {
        String sql = "INSERT INTO users (Name, PinNumber)" +
        " VALUES ('" + name + "'," + pin + ")";
        try {
            DBConnection.getStmt().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = DBConnection.getStmt().getGeneratedKeys();
            if (rs.next()) {
                this.accountNumber = rs.getInt(1);
                this.pin = pin;
                this.name = name;
            }
        } catch (SQLException ex) {
            //System.out.println(ex.toString());
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }
    
    public void login(int accountNumber, int pin) {
        String sql = "SELECT * FROM users WHERE users.AccountNumber = " + accountNumber + " AND users.PinNuMBER = " + pin + " LIMIT 1";
        ResultSet rs = DBConnection.SelectQuery(sql);
        try {
            if (rs.next()) {
                this.accountNumber = accountNumber;
                this.pin = pin;
                this.name = rs.getString("Name");
                System.out.println("Login Accepted");
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
}
