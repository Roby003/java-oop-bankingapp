package main.models.account;

import javax.naming.spi.DirStateFactory.Result;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CreditAccount extends CardAccount{
   private double credit_limit;

   
    public CreditAccount(int customerId,double credit_limit) {
        super(customerId,0);
        this.credit_limit = credit_limit;
        super.balance=credit_limit;


    }
    public CreditAccount(ResultSet in) throws SQLException{
        super(in);
        this.credit_limit = in.getDouble("credit_limit");
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "credit_limit=" + credit_limit +
                ", no_account='" + no_account + '\'' +
                ", creation_date=" + creation_date +
                ", balance=" + balance +
                ", interest_rate=" + interest_rate +
                '}';
    }

    public double getCreditLimit() {
        return credit_limit;
    }
}
