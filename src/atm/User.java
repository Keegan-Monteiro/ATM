package atm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            rs.close();
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
    
    public boolean login(int accountNumber, int pin) {
        String sql = "SELECT * FROM users WHERE users.AccountNumber = " + accountNumber + " AND users.PinNumber = " + pin + " LIMIT 1";
        ResultSet rs = DBConnection.SelectQuery(sql);
        try {
            if (rs.next()) {
                this.accountNumber = accountNumber;
                this.pin = pin;
                this.name = rs.getString("Name");
                return true;
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return false;
        }
        return false;
    }
    
    public List<Account> getUserAccounts(User user){
        List<Account> userAccounts = new ArrayList<Account>();
        String sql = "SELECT a.AccountID, a.AccountTypeID, a.AccountNumber, a.AccountBalance FROM account a WHERE a.AccountNumber = " + user.getAccountNumber();
        ResultSet rs = DBConnection.SelectQuery(sql);
        try {
            while(rs.next()) {
                if (rs.getInt("AccountTypeID") == 1)
                    userAccounts.add(new CurrentAccount(rs.getInt("AccountID"),rs.getInt("AccountTypeID"),rs.getDouble("AccountBalance"),user));
                else if (rs.getInt("AccountTypeID") == 2)
                    userAccounts.add(new SavingsAccount(rs.getInt("AccountID"),rs.getInt("AccountTypeID"),rs.getDouble("AccountBalance"),user));
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return userAccounts;
    }
}
