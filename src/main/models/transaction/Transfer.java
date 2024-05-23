package main.models.transaction;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

final public class Transfer extends Transaction{
    public Transfer(String no_account,Date timestamp, double amount, String s_account, String d_account, String description) {
        super(no_account,timestamp, amount, s_account, d_account, description);
    }
    public Transfer(ResultSet in) throws SQLException {
        super(in);
    }
}
