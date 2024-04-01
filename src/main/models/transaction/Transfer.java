package main.models.transaction;

import java.util.Date;

final public class Transfer extends Transaction{
    public Transfer(Date timestamp, double amount, String s_account, String d_account, String description) {
        super(timestamp, amount, s_account, d_account, description);
    }
}
