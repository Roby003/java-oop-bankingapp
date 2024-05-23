package BankingAppPackage;

import main.models.*;
import main.models.account.*;
import main.models.transaction.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static Connection getConnection()
    {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/main_schema",
                    "root",
                    "password"
            );

        }catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {

        Connection connection=getConnection();
        TransactionDatabase transactionDatabase=TransactionDatabase.getInstance(connection);
        CardDatabase cardDatabase=CardDatabase.getInstance(connection);
        AccountDatabase accountDatabase=AccountDatabase.getInstance(connection);
        CustomerDatabase customerDatabase=CustomerDatabase.getInstance(connection,accountDatabase);


        var App=new BankingApp(customerDatabase,accountDatabase,transactionDatabase,cardDatabase);
        App.start();


    }
}