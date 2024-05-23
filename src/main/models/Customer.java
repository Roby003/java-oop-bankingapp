package main.models;

import main.models.account.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {

    public int customer_id;
    String name;
    String phone_number;
    String email;
    static int  id_counter=0;
    List<Account> accounts;

    static {

        id_counter=CustomerDatabase.getMaxId();
    }
    public Customer(String name, String phone_number, String email) {
        this.customer_id=++id_counter;
        this.name = name;
        this.phone_number = phone_number;
        this.email = email;
        this.accounts=new ArrayList<>();

    }

    public Customer(ResultSet in) throws SQLException
    {
        this.customer_id=in.getInt("customer_id");
        this.name=in.getString("name");
        this.phone_number=in.getString("phone_number");
        this.email=in.getString("email");
        this.accounts=new ArrayList<>();
    }
    public Account addAccount( AccountType type, float interest_rate, double credit_limit)
    {
        Account account;
        switch (type)
        {   
            case CHECKING:
                account=new CheckingAccount(customer_id);
                accounts.add(account);
                break;
            case SAVING:
                account=new SavingsAccount(customer_id,interest_rate,20);
                accounts.add(account);
                break;
            case CREDIT:
                account=new CreditAccount(customer_id,credit_limit);
                accounts.add(account);
                break;
            default:
                account=null;
        }
        return account;
    }
    public void removeAccount(Account account)
    {
        try {
            if (accounts.remove(account)) {
                System.out.println("account successfully removed");

            } else {
                System.out.println("account not in list");
            }

        } catch (Exception ex) {
            System.out.println("object is null");
        }
    }
    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(phone_number, customer.phone_number) && Objects.equals(email, customer.email) && Objects.equals(accounts, customer.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone_number, email, accounts);
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getEmail() {
        return email;
    }

    public static int getId_counter() {
        return id_counter;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
