package atm;

public class Transaction {
    private int transactionID;
    private Account account;
    private String date;
    private double amount;

    public Transaction(int transactionID, Account account, double amount, String date) {
        this.transactionID = transactionID;
        this.account = account;
        this.amount = amount;
        this.date = date;
    }
    
    public int getTransactionID() {
        return transactionID;
    }

    public Account getAccount() {
        return account;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
