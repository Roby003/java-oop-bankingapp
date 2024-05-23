package main.models.transaction;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

final public class Withdraw extends Transaction{

    public Withdraw(String no_account,Date timestamp, double amount, String s_account) {
        super(no_account,timestamp, amount, s_account, null, null);

    }
    public Withdraw(ResultSet in) throws SQLException {
        super(in);
    }

}
