package main.models;

import main.models.account.Account;
import main.models.account.AccountType;
import main.models.account.CardAccount;
import main.models.transaction.TransactionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankingApp {
    List<Customer> customerList;

    public BankingApp()
    {
        customerList=new ArrayList<>();
    }

    public void addCustomer(String name,String phone,String mail)
    {
        customerList.add(new Customer(name,phone,mail));

    }
    private Customer logIn()
    {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < customerList.size(); i++) {
                System.out.println(Integer.toString(i ) + " " + customerList.get(i).toString());

            }
            System.out.println("enter index");
            int index = scanner.nextInt();
            if (index < 0 || index >= customerList.size()) {
                System.out.println("index out of range");
                return null;
            }
            return customerList.get(index);
    }

    public void start()
    {
        boolean customer_bool=true;
        while(customer_bool) {
            Customer current_customer;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Banking App");

            System.out.println("1. Add Customer");
            System.out.println("2. log into account");
            System.out.println("3. Stop");

            int choice;
            choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println("Enter Name");
                scanner.next();
                String name = scanner.nextLine();
                System.out.println("Enter Phone");
                String phone = scanner.nextLine();
                System.out.println("Enter Mail");
                String mail = scanner.nextLine();
                addCustomer(name, phone, mail);
                continue;
            } else if (choice == 2) {
                current_customer = logIn();
                if (current_customer == null) {
                    continue;

                    }
                else if (choice == 3) {
                    customer_bool=false;
                    continue;
                    }

            } else {
                System.out.println("Invalid Choice");
                continue;
            }



            boolean account_bool=true;
                while(account_bool)
                {

                    System.out.println("1. Add Account");
                    System.out.println("2. View Account");
                    System.out.println("3. Exit");
                    choice=scanner.nextInt();
                    if(choice==1)
                    {
                        AccountType account_type=inputAccountType();
                        if (account_type==null){
                            continue;
                        }
                        if(account_type==AccountType.CHECKING)
                        {
                            current_customer.addAccount(account_type,0,0);
                        } else if (account_type==AccountType.SAVING)
                        {
                            System.out.println("enter interest rate");
                            int interest_rate=scanner.nextInt();
                            current_customer.addAccount(account_type,interest_rate,0);
                        }
                        else {
                            System.out.println("enter credit limit");
                            int credit_limit=scanner.nextInt();
                            current_customer.addAccount(account_type,0,credit_limit);

                        }



                    }
                    else if(choice==2)
                    {
                        Account account=inputAccount(current_customer);
                        if(account==null)
                        {
                            System.out.println("Account not found");
                        }
                        else
                        {
                            boolean account_operations=true;
                            while(account_operations)
                            {
                                if(account instanceof CardAccount)
                                {
                                    System.out.println("0. See Cards");
                                }
                                System.out.println("1. Initiate a transaction");
                                System.out.println("2. View Balance");
                                System.out.println("3. Exit");
                                int account_choice=scanner.nextInt();
                                 if(account instanceof CardAccount)
                                 {
                                     if (account_choice==0)
                                     {
                                        boolean card_operations=true;
                                        while(card_operations)
                                        {
                                            System.out.println("0.See Cards' Details");
                                            System.out.println("1.Add Card");
                                            System.out.println("2.Remove Card");

                                            int card_choice=scanner.nextInt();
                                            if(card_choice==0)
                                            {
                                                ((CardAccount) account).printCards();
                                            } else if (card_choice==1) {
                                                ((CardAccount) account).addCard();

                                            }
                                            else if(card_choice==2)
                                            {
                                                Card card_to_remove=inputRemoveCard((CardAccount)account);
                                                ((CardAccount) account).removeCard(card_to_remove);
                                            }
                                            else
                                            {
                                                System.out.println("invalid choice");
                                            }
                                            card_operations=false;

                                        }
                                     }
                                 }

                                if(account_choice==1)
                                {
                                    TransactionType transaction_type=inputTransactionType();
                                    System.out.println("enter amount");
                                    double amount=scanner.nextDouble();
                                    if(transaction_type==null)
                                        continue;
                                    if( transaction_type ==TransactionType.TRANSFER)
                                    {
                                        System.out.println("enter account number for transfer");
                                        String no_t_account= scanner.nextLine();
                                        Account t_account=findAccount(no_t_account);
                                        if (t_account == null)
                                        {System.out.println("account number is invalid");
                                        continue;
                                        }
                                        else
                                        {
                                            String description= scanner.nextLine();
                                            account.makeTransaction(transaction_type,t_account,amount,description);
                                        }
                                    }
                                    else
                                        account.makeTransaction(transaction_type,account,amount,null);



                                }
                                else if(account_choice==2)
                                {
                                    System.out.println("Balance: "+account.getBalance());
                                }
                                else if(account_choice==3)
                                {
                                    account_operations=false;
                                }
                                else
                                {
                                    System.out.println("Invalid Choice");
                                }
                            }
                        }
                    }
                    else if(choice==3)
                    {
                        account_bool=false;
                    }
                    else
                    {
                        System.out.println("Invalid Choice");
                    }
        }


        }
    }

    private Card inputRemoveCard(CardAccount account) {
        Scanner scanner = new Scanner(System.in);

        for(int i=0;i<account.cards.size();i++)
        {
            System.out.println(i+" "+account.cards.get(i).toString());

        }
        int choice=scanner.nextInt();
        if(choice<0 || choice>=account.cards.size())
        {
            return null;

        }
        return account.cards.get(choice);



    }

    private Account findAccount(String noTAccount) {
        for(var customer: customerList)
            for (var account :customer.accounts)
            {
                if(account.getNo_account().equals(noTAccount))
                    return account;
            }
        return null;
    }

    private TransactionType inputTransactionType() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("select transaction type");
        for(int i=0; i<TransactionType.values().length;i++)
        {
            System.out.println(Integer.toString(i)+" "+TransactionType.values()[i]);

        }
        int choice=scanner.nextInt();
        if(choice<0 || choice>TransactionType.values().length)
            return null;
        else
            return TransactionType.values()[choice];

    }

    private Account inputAccount(Customer customer) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("select account");
        for( int i=0;i<customer.accounts.size();i++)
        {
            System.out.println(i+" " +customer.accounts.get(i).toString());
        }
        int choice=scanner.nextInt();
        if( choice<0 || choice>=customer.accounts.size())
        {

            return null;
        }
        else
            return customer.accounts.get(choice);

    }

    private AccountType inputAccountType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("select account type");
        for(var i=0;i<AccountType.values().length;i++)
        {
            System.out.println(Integer.toString(i)+" "+AccountType.values()[i]);

        }
        int choice=scanner.nextInt();
        if (choice <0|| choice>=AccountType.values().length)
        {
            System.out.println("invalid choice");
            return null;
        }
        else
            return AccountType.values()[choice];

    }


}
