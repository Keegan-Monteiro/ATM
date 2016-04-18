package atm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SavingsAccount extends Account{
    private final double interestRate = 1.25;

    public SavingsAccount(int accountTypeID, double balance, User user) {
        super(accountTypeID, balance, user);
        String sql = "INSERT INTO account (AccountNumber, AccountTypeID , AccountBalance, InterestRate)" +
                   "VALUES (" + user.getAccountNumber() + "," + accountTypeID + "," + 0 + "," + interestRate + ")";
        try {
            DBConnection.getStmt().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = DBConnection.getStmt().getGeneratedKeys();
            if (rs.next()) {
                accountID = rs.getInt(1);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SavingsAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SavingsAccount(int accountID, int accountTypeID, double balance, User user) {
        super(accountID, accountTypeID, balance, user);
    }

    public double getInterestRate() {
        return interestRate;
    }
    
    @Override
    public String getAccountType() {
        return "Savings Account";
    }
    
    @Override
    public boolean canWithdraw() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
        String sql = "SELECT count(*) FROM transaction Where DateAndTime > '" + timeStamp + "' AND AccountID = " + accountID;
        ResultSet rs = DBConnection.SelectQuery(sql);
        int count = 0;
        try {
            while(rs.next()){
                count = rs.getInt("count(*)");
            }            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(SavingsAccount.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (count >= 3)
            return false;
        else
            return true;
    }
}
