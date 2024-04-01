package main.models.account;

import java.util.ArrayList;
import java.util.List;

public class CheckingAccount extends CardAccount {

    @Override
    public String toString() {
        return "CheckingAccount{" +
                ", no_account='" + no_account + '\'' +
                ", creation_date=" + creation_date +
                ", balance=" + balance +
                ", interest_rate=" + interest_rate +
                '}';
    }

    public CheckingAccount() {
        super(0);
    }
}
