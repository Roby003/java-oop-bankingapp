package main.models.transaction;

import java.sql.Date;
import java.util.Objects;
import java.sql.ResultSet;
import java.sql.SQLException;


abstract public class Transaction {
    Date timestamp;
    double amount;

    String s_account;
    String d_account;
    String description;
    String no_account;
    String type;

    public Transaction(String no_account,Date timestamp, double amount, String s_account, String d_account, String description) {
        this.no_account = no_account;
        this.timestamp = timestamp;
        this.amount = amount;
        this.s_account = s_account;
        this.d_account = d_account;
        this.description = description;
    }

    public Transaction(ResultSet in) throws SQLException {
        no_account = in.getString("no_account");
        timestamp = in.getDate("timestamp");
        amount = in.getDouble("amount");
        s_account = in.getString("s_account");
        d_account = in.getString("d_account");
        description = in.getString("description");

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(amount, that.amount) == 0 && Objects.equals(timestamp, that.timestamp) && Objects.equals(s_account, that.s_account) && Objects.equals(d_account, that.d_account) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, amount, s_account, d_account, description);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public String getSAccount() {
        return s_account;
    }

    public String getDAccount() {
        return d_account;
    }

    public String getDescription() {
        return description;
    }

    public String getNoAccount() {
        return no_account;
    }
}
