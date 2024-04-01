package main.models.transaction;

import java.util.Date;

final public class Withdraw extends Transaction{

    public Withdraw(Date timestamp, double amount, String s_account) {
        super(timestamp, amount, s_account, null, null);

    }

}
