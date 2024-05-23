package main.models.transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

final public class Deposit extends Transaction{
    public Deposit(String no_account,Date timestamp, double amount, String s_account) {
        super(no_account,timestamp, amount, s_account, s_account, null);
    }
    public Deposit(ResultSet in) throws SQLException {
        super(in);
    }
}
