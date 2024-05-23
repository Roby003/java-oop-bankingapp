package main.models.account;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckingAccount extends CardAccount {



    public CheckingAccount(int customer_id) {
        super(customer_id,0);
    }
    public CheckingAccount(ResultSet in) throws SQLException {
        super(in);
    }
    @Override
    public String toString() {
        return "CheckingAccount{" +
                ", no_account='" + no_account + '\'' +
                ", creation_date=" + creation_date +
                ", balance=" + balance +
                ", interest_rate=" + interest_rate +
                '}';
    }

}
