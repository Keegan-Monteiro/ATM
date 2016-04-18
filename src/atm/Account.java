package atm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Account {
    protected int accountID;
    protected User user;
    private int accountTypeID;
    private double balance;
    
    public Account(int accountTypeID, double balance, User user) {
        this.user = user;
        this.accountTypeID = accountTypeID;
        this.balance = balance;
    }
    
    public Account(int accountID, int accountTypeID, double balance, User user) {
        this.accountID = accountID;
        this.user = user;
        this.accountTypeID = accountTypeID;
        this.balance = balance;
    }

    public int getAccountID() {
        return accountID;
    }

    public User getUser() {
        return user;
    }

    public String getAccountType() {
//        if (accountTypeID.toUpperCase().equals("C"))
//            return "Current Account";
//        else if (accountTypeID.toUpperCase().equals("S"))
//            return "Savings Account";
        return ""+accountTypeID;
    }

    public double getBalance() {
        return balance;
    }
    
    public String setBalance(double amount) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String sql = "INSERT INTO transaction (AccountID, Amount, DateAndTime)" +
                   "VALUES (" + accountID + "," + amount + ",'" + timeStamp + "')";
        int success = DBConnection.SQLQuery(sql);
        if (success != 0)
        {
            this.balance = this.balance + amount;
            return ("Successful");
        }
        else
            return "Unsuccessful";
    }
    
    public List<Transaction> getTransactions(Account account) {
        String sql = "SELECT TransactionID, Amount, DateAndTime FROM transaction WHERE AccountID = " + accountID;
        ResultSet rs = DBConnection.SelectQuery(sql);
        List<Transaction> transactions = new ArrayList<Transaction>();
        balance = 0;
        try {
            while(rs.next()) {
                transactions.add(new Transaction(rs.getInt("TransactionID"),account,rs.getDouble("Amount"),rs.getString("DateAndTime")));
                balance = balance + rs.getDouble("Amount");
            }
            rs.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return transactions;
    }
    
    public boolean canWithdraw() {return true;}
    
}
