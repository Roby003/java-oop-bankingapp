package main.models.account;

import main.models.transaction.TransactionType;
import org.jetbrains.annotations.NotNull;

public class SavingsAccount extends Account{
    int transaction_limit;
    int transactions_left;

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
    public void makeTransaction(@NotNull TransactionType type, Account target_account, double amount, String description )
    {
        if(transactions_left==0)
        {
            System.out.println("transaction limit reached");
            return;
        }
        else
        {
            transactions_left-=1;
            super.makeTransaction(type,target_account,amount,description);
        }
    }
    public SavingsAccount(float interest_rate, int transaction_limit) {
        super(interest_rate);
        this.transaction_limit = transaction_limit;
        this.transactions_left=transaction_limit;
    }

}

