package main.models.account;

import main.models.transaction.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



public abstract class Account {

    final String no_account;
    final Date creation_date;
    double balance;
    float interest_rate;
    List<Transaction> transaction_history;

    public Account( float interest_rate) {

        no_account= String.valueOf(UUID.randomUUID());
        creation_date=new Date();
        transaction_history= new ArrayList<>();
        balance=0;
        this.interest_rate = interest_rate;

    }
    private void processTransaction(@NotNull TransactionType type, double amount, Account target_account)
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

    public void makeTransaction(@NotNull TransactionType type, Account target_account, double amount, String description )
    {


        switch (type)
        {
            case DEPOSIT:

                transaction_history.add(new Deposit(new Date(),amount, this.no_account));
                processTransaction(type,amount,target_account);
                break;

            case TRANSFER:
                if (target_account != null) {
                    transaction_history.add(new Transfer(new Date(),amount,this.no_account, target_account.no_account,description));
                    processTransaction(type, amount, target_account);
        } else {
            System.out.println("Invalid target account for transfer");
        }
            break;

            case WITHDRAW:
                transaction_history.add(new Withdraw(new Date(),amount,this.no_account));
                processTransaction(type,amount,target_account);
                break;


        }

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

    public void applyInterest() {
        double interest = calculateInterest();
        balance += interest;
        System.out.println("Interest applied: $" + interest);
    }

    private double calculateInterest() {
        return balance * (interest_rate / 100.0);
    }

    public String getNo_account() {
        return no_account;
    }

    public double getBalance() {
        return balance;
    }
}
