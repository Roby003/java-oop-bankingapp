package main.models.account;

import main.models.transaction.Transaction;
import main.models.transaction.TransactionType;
import java.util.Objects;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SavingsAccount extends Account{
    protected
    int transaction_limit;
    int transactions_left;



   
    public SavingsAccount(int customerId,float interest_rate, int transaction_limit) {
        super(customerId,interest_rate);
        this.transaction_limit = transaction_limit;
        this.transactions_left=transaction_limit;
    }
    public SavingsAccount(ResultSet in) throws SQLException {
        super(in);
        transaction_limit = in.getInt("transaction_limit");
        transactions_left = in.getInt("transactions_left");
    }
    @Override
    public Transaction makeTransaction( TransactionType type, Account target_account, double amount, String description )
    {
        if(transactions_left==0)
        {
            System.out.println("transaction limit reached");
            return null;
        }
        else
        {
            transactions_left-=1;
            return super.makeTransaction(type,target_account,amount,description);
        }
    }
    @Override
    public String toString() {
        return "SavingsAccount{" +
                "transaction_limit=" + transaction_limit +
                ", transactions_left=" + transactions_left +
                ", no_account='" + no_account + '\'' +
                ", creation_date=" + creation_date +
                ", balance=" + balance +
                ", interest_rate=" + interest_rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SavingsAccount that = (SavingsAccount) o;
        return transaction_limit == that.transaction_limit && transactions_left == that.transactions_left;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), transaction_limit, transactions_left);
    }

    public int getTransactionLimit() {
        return transaction_limit;
    }

    public int getTransactionsLeft() {
        return transactions_left;
    }
}

