package main.models.account;

import main.models.transaction.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;

import java.util.*;


public abstract class Account {

    protected
        final String no_account;
        final Date creation_date;
        double balance;
        float interest_rate;
        int customerId;
        List<Transaction> transaction_history;


    public Account( int customerId,float interest_rate) {

        no_account= String.valueOf(UUID.randomUUID());
        creation_date=Date.valueOf(LocalDate.now());
        transaction_history= new ArrayList<>();
        balance=0;
        this.interest_rate = interest_rate;
        this.customerId=customerId;

    }
    public Account (ResultSet in) throws SQLException {
        customerId = in.getInt("customer_id");
        no_account = in.getString("no_account");
        creation_date = in.getDate("creation_date");
        balance = in.getDouble("balance");
        interest_rate = in.getFloat("interest_rate");
        transaction_history = new ArrayList<>();
    }
    public void addTransaction(ResultSet in) throws SQLException {
        TransactionType type = TransactionType.valueOf(in.getString("type"));
        switch (type) {
            case DEPOSIT:
                transaction_history.add(new Deposit(in));
                break;
            case WITHDRAW:
                transaction_history.add(new Withdraw(in));
                break;
            case TRANSFER:
                transaction_history.add(new Transfer(in));
                break;
        }

    }
    private void processTransaction( TransactionType type, double amount, Account target_account)
    {
       switch (type)
        {
            case DEPOSIT:
                this.balance+=amount;
                System.out.println("deposit successful");
                break;
            case WITHDRAW:
                if(this.balance<amount)
                {
                    System.out.println("not enough balance");

                }
                else{
                    this.balance-=amount;
                    System.out.println("withdraw successful");

                }
                break;
            case TRANSFER:
                if(this.balance<amount)
                {
                    System.out.println("not enough balance");


                }
                else
                {  
                    this.balance-=amount;
                    target_account.balance+=amount;
                    System.out.println("transfer successful");

                }
                break;
        }
    }

    public Transaction makeTransaction(TransactionType type, Account target_account, double amount, String description )
    {

        Transaction transaction = null;
        switch (type)
        {
            case DEPOSIT:
                transaction = new Deposit(no_account,Date.valueOf(LocalDate.now()),amount,this.no_account);
                transaction_history.add(transaction);
                processTransaction(type,amount,target_account);
                break;

            case TRANSFER:
                if (target_account != null) {
                    transaction= new Transfer(no_account,Date.valueOf(LocalDate.now()),amount,this.no_account, target_account.no_account,description);
                    transaction_history.add(transaction);
                    processTransaction(type, amount, target_account);
                } else {
                    System.out.println("Invalid target account for transfer");
                }
                 break;

            case WITHDRAW:
                transaction=new Withdraw(no_account,Date.valueOf(LocalDate.now()),amount,this.no_account);
                transaction_history.add(transaction);
                processTransaction(type,amount,target_account);
                break;

        }
        return transaction;
    }



   

    public String getNo_account() {
        return no_account;
    }

    public double getBalance() {
        return balance;
    }
    @Override
    public String toString() {
        return "{" +
                "no_account='" + no_account + '\'' +
                ", creation_date=" + creation_date +
                ", balance=" + balance +
                ", interest_rate=" + interest_rate +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(balance, account.balance) == 0 && Float.compare(interest_rate, account.interest_rate) == 0 && Objects.equals(no_account, account.no_account) && Objects.equals(creation_date, account.creation_date) && Objects.equals(transaction_history, account.transaction_history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no_account, creation_date, balance, interest_rate, transaction_history);
    }

}
