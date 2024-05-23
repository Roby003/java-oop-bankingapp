package main.models;

import main.models.account.AccountDatabase;

import java.sql.*;
import java.util.*;

public class CustomerDatabase {

     static private Connection connection;
    private AccountDatabase accountDatabase;

    private static CustomerDatabase instance;
    private CustomerDatabase(Connection connection,AccountDatabase _accountDatabse) {

        this.connection = connection;
        this.accountDatabase = _accountDatabse;
    }

    public static CustomerDatabase getInstance(Connection connection,AccountDatabase _accountDatabase)
    {

            if (instance==null)
            {
                instance=new CustomerDatabase(connection,_accountDatabase);

            }
            return instance;

    }

    static public int getMaxId()
    {
        String query="SELECT MAX(customer_id) FROM customer;";
        try
        {
            Statement stmt=connection.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            rs.next();
            return rs.getInt(1);
        }
        catch (SQLException e)
        {
            return 0;
        }

    }
    public List<Customer> getCustomers()
    {

        List<Customer> customers = new ArrayList<>();
        try
        {
            String query = "SELECT * FROM customer";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next())
            {
                Customer customer = new Customer(resultSet);
                customer.setAccounts(accountDatabase.getAccounts(customer.customer_id));
                customers.add(customer);
            }
            resultSet.close();
            statement.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return customers;
    }

    public void create(Customer customer)
    {
        try
        {
            String query = "INSERT INTO customer (customer_id,name, phone_number, email) VALUES (?,?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,Integer.toString(customer.getCustomer_id()));
            preparedStatement.setString(2, customer.name);
            preparedStatement.setString(3, customer.phone_number);
            preparedStatement.setString(4, customer.email);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }}
    
}

