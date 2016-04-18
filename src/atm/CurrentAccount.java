package atm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CurrentAccount extends Account{
    private final double overdraft = 1.25;
    
    public CurrentAccount(int accountTypeID, double balance, User user) {
        super(accountTypeID, balance, user);
        String sql = "INSERT INTO account (AccountNumber, AccountTypeID , AccountBalance, Overdraft)" +
                   "VALUES (" + user.getAccountNumber() + "," + accountTypeID + "," + 0 + "," + overdraft + ")";
        try {
            DBConnection.getStmt().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = DBConnection.getStmt().getGeneratedKeys();
            if (rs.next()) {
                accountID = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CurrentAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public CurrentAccount(int accountID, int accountTypeID, double balance, User user) {
        super(accountID, accountTypeID, balance, user);
    }

    public double getOverdraft() {
        return overdraft;
    }
    
    @Override
    public String getAccountType() {
        return "Current Account";
    }
}
