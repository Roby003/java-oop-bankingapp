package main.models;

import main.models.account.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    String name;
    String phone_number;
    String email;

    List<Account> accounts;

    public Customer(String name, String phone_number, String email) {
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.accounts=new ArrayList<>();

    }

    public void addAccount(@NotNull AccountType type, float interest_rate, double credit_limit)
    {
        switch (type)
        {
            case CHECKING:
                accounts.add(new CheckingAccount());
                break;
            case SAVING:
                accounts.add(new SavingsAccount(interest_rate,20));
                break;
            case CREDIT:
                accounts.add(new CreditAccount(credit_limit));
                break;
        }
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
