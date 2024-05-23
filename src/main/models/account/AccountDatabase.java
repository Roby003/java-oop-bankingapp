package main.models.account;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.PreparableStatement;


public class AccountDatabase {
    static private Connection connection;
    private static AccountDatabase INSTANCE;
    private AccountDatabase(Connection connection) {
        this.connection = connection;
    }

    public static AccountDatabase getInstance(Connection connection)
    {
        if (INSTANCE ==null)
        {
            INSTANCE=new AccountDatabase(connection);
        }
        return INSTANCE;
    }

    public List<Account> getAccounts(int customerId) {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account where customer_id = \"" + customerId + "\";");

            while (resultSet.next()) {
                AccountType type=AccountType.valueOf(resultSet.getString("type"));
                switch (type) {
                    case CHECKING:
                        Account account = new CheckingAccount(resultSet);
                        Statement statement2 = connection.createStatement();
                        ResultSet resultSet2 = statement2.executeQuery("SELECT * FROM transaction where no_account = \"" + account.no_account + "\";");
                        while(resultSet2.next())
                        {
                            account.addTransaction(resultSet2);
                        }
                        Statement statement3 = connection.createStatement();
                        ResultSet resultSet3 = statement3.executeQuery("SELECT * FROM card where no_account = \"" + account.no_account + "\";");
                        while(resultSet3.next())
                        {
                            ((CardAccount)account).addCard(resultSet3);
                        }
                        accounts.add(account);
                        break;
                    case CREDIT:
                        Account account2 = new CreditAccount(resultSet);
                        Statement statement4 = connection.createStatement();
                        ResultSet resultSet4 = statement4.executeQuery("SELECT * FROM transaction where no_account = \"" + account2.no_account + "\";");
                        while(resultSet4.next())
                        {
                            account2.addTransaction(resultSet4);
                        }
                        Statement statement5 = connection.createStatement();
                        ResultSet resultSet5 = statement5.executeQuery("SELECT * FROM card where no_account = \"" + account2.no_account + "\";");
                        while(resultSet5.next())
                        {
                            ((CardAccount)account2).addCard(resultSet5);
                        }
                        accounts.add(account2);

                        break;
                    case SAVING:
                        Account account3 = new SavingsAccount(resultSet);
                        Statement statement6 = connection.createStatement();
                        ResultSet resultSet6 = statement6.executeQuery("SELECT * FROM transaction where no_account = \"" + account3.no_account + "\";");
                        while(resultSet6.next())
                        {
                            account3.addTransaction(resultSet6);
                        }
                        accounts.add(account3);

                        break;
                
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    public void update(Account newAccount,AccountType type)
    {
        try
        {
            String query="UPDATE account SET balance= ?, interest_rate= ?, customer_id= ?,type=? WHERE no_account= ?;";
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setDouble(1, newAccount.balance);
            stmt.setFloat(2, newAccount.interest_rate);
            stmt.setInt(3, newAccount.customerId);
            stmt.setString(4, newAccount.no_account);
            stmt.setString(5,type.name());
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void create(Account account,AccountType type)
    {
        try
        {
            String query="INSERT INTO account (no_account, creation_date, balance, interest_rate, customer_id,type,credit_limit,transaction_limit,transactions_left) VALUES (?, ?, ?, ?, ?,?,?,?,?);";
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setString(1, account.no_account);
            stmt.setDate(2, account.creation_date);
            stmt.setDouble(3, account.balance);
            stmt.setFloat(4, account.interest_rate);
            stmt.setInt(5, account.customerId);
            stmt.setString(6,type.name());

            if (account instanceof CreditAccount)
            {
                stmt.setDouble(7,((CreditAccount)account).getCreditLimit());
            }
            else
                stmt.setNull(7, Types.NULL);

            if (account instanceof SavingsAccount)
            {
                stmt.setDouble(8,((SavingsAccount)account).getTransactionLimit());
                stmt.setDouble(9, ((SavingsAccount)account).getTransactionsLeft());
            }
            else
               {
                   stmt.setNull(8, Types.NULL);
                   stmt.setNull(9, Types.NULL);
               }
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void delete(Account account)
    {
        try
        {
            String query="DELETE FROM account WHERE no_account= ?;";
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setString(1, account.no_account);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    public void updateBalance(Account account,double amount)
    {
        try
        {
            String query="UPDATE account SET balance= ? WHERE no_account= ?;";
            PreparedStatement stmt=connection.prepareStatement(query);
            stmt.setDouble(1, account.balance);
            stmt.setString(2, account.no_account);
            stmt.executeUpdate();
            stmt.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
