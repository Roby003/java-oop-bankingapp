import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class MyJDBC {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/main_schema",
                    "root",
                    "password"
            );


        }catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
