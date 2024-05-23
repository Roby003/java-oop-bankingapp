package main.models;

import java.sql.*;


public class CardDatabase {
    static private Connection connection;
    private static CardDatabase instance;
    private CardDatabase(Connection connection) {
        this.connection = connection;
    }
    public static CardDatabase getInstance(Connection connection)
    {
        if (instance==null)
        {
            instance=new CardDatabase(connection);

        }
        return instance;
    }

    public void create(Card card) {
        try {
            String query = "INSERT INTO card (no_account, card_number, creation_date, expiration_date, cvv, pin) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, card.getNo_account());
            preparedStatement.setString(2, card.getCard_number());
            preparedStatement.setDate(3, new java.sql.Date(card.getCreation_date().getTime()));
            preparedStatement.setDate(4, new java.sql.Date(card.getExpiration_date().getTime()));
            preparedStatement.setString(5, card.getCvv());
            preparedStatement.setString(6, card.getPin());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void delete(Card card)
    {
        try
        {
            String query="DELETE FROM card WHERE card_number= ?;";
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setString(1, card.getCard_number());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
