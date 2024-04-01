package main.models.transaction;

import java.util.Date;

abstract public class Transaction {
    Date timestamp;

    double amount;

    String s_account;
    String d_account;
    String description;

    public Transaction(Date timestamp, double amount, String s_account, String d_account, String description) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.s_account = s_account;
        this.d_account = d_account;
        this.description = description;
    }

}
