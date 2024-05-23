package main.models.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class TransactionDatabase {
    static private Connection connection;
    private static TransactionDatabase instance;
    private TransactionDatabase(Connection connection) {
        this.connection = connection;
    }
    public static TransactionDatabase getInstance(Connection connection)
    {

            if (instance==null)
            {
                instance=new TransactionDatabase(connection);

            }
            return instance;

    }


    public void create(Transaction transaction,TransactionType type) {
        try{
            String query = "INSERT INTO transaction (no_account, timestamp, amount, s_account, d_account, description, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, transaction.getNoAccount());
            preparedStatement.setDate(2, transaction.getTimestamp());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getSAccount());
            preparedStatement.setString(5, transaction.getDAccount());
            preparedStatement.setString(6, transaction.getDescription());
            preparedStatement.setString(7, type.toString());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    
    }

    public void delete(Transaction transaction) {
        try {
            String query = "DELETE FROM transaction WHERE no_account = ? AND timestamp = ? AND amount = ? AND s_account = ? AND d_account = ? AND description = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, transaction.getNoAccount());
            preparedStatement.setDate(2, transaction.getTimestamp());
            preparedStatement.setDouble(3, transaction.getAmount());
            preparedStatement.setString(4, transaction.getSAccount());
            preparedStatement.setString(5, transaction.getDAccount());
            preparedStatement.setString(6, transaction.getDescription());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
