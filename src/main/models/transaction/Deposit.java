package main.models.transaction;

import java.util.Date;

final public class Deposit extends Transaction{
    public Deposit(Date timestamp, double amount, String s_account) {
        super(timestamp, amount, s_account, s_account, null);
    }
}
