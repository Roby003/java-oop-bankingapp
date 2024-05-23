package main.models;

import main.models.account.*;
import main.models.transaction.*;

import java.util.*;

import main.utils.ActionLogger;

public class BankingApp {
    private List<Customer> customerList;
    private CustomerDatabase customerDatabase;
    private AccountDatabase accountDatabase;
    private CardDatabase cardDatabase;
    private TransactionDatabase transactionDatabase;

    public BankingApp(CustomerDatabase customerDatabase, AccountDatabase accountDatabase,
            TransactionDatabase transactionDatabase, CardDatabase cardDatabase) {
        this.customerDatabase = customerDatabase;
        this.accountDatabase = accountDatabase;
        this.transactionDatabase = transactionDatabase;
        this.cardDatabase = cardDatabase;
        customerList = customerDatabase.getCustomers();
    }

    public BankingApp() {
        customerList = new ArrayList<>();
    }

    public void addCustomer(String name, String phone, String mail) {

        var new_customer = new Customer(name, phone, mail);
        customerList.add(new_customer);
        customerDatabase.create(new_customer);
        Collections.sort(customerList, new Comparator<Customer>() {
                    @Override
                    public int compare(Customer o1, Customer o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
        );

        ActionLogger.logAction("added customer account with id:" + Integer.toString(new_customer.getCustomer_id()));

    }
    private int getValidatedIntInput(Scanner scanner, String prompt) {
        int input;
        while (true) {
            System.out.println(prompt);
            try {
                input = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return input;
    }
    private double getValidatedDoubleInput(Scanner scanner, String prompt) {
        double input;
        while (true) {
            System.out.println(prompt);
            try {
                input = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid double.");
            }
        }
        return input;
    }
    

    private Customer logIn(Scanner scanner) {
        for (int i = 0; i < customerList.size(); i++) {
            System.out.println(Integer.toString(i) + " " + customerList.get(i).toString());

        }
        int index = getValidatedIntInput(scanner,"enter index:");

        if (index < 0 || index >= customerList.size()) {
            System.out.println("index out of range");
            return null;
        }
        return customerList.get(index);
    }

    public void start() {
        boolean customer_bool = true;
        while (customer_bool) {
            Customer current_customer;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Banking App");

            System.out.println("1. Add Customer");
            System.out.println("2. log into customer account");
            System.out.println("3. Stop");

            int choice;
            choice = getValidatedIntInput(scanner, "Enter your choice: ");
            if (choice == 1) {
                System.out.println("Enter Name");
                String name = scanner.nextLine();
                System.out.println("Enter Phone");
                String phone = scanner.nextLine();
                System.out.println("Enter Mail");
                String mail = scanner.nextLine();
                addCustomer(name, phone, mail);
                continue;
            } else if (choice == 2) {
                current_customer = logIn(scanner);
                ActionLogger
                        .logAction("logged in with customer id:" + Integer.toString(current_customer.getCustomer_id()));
                if (current_customer == null)
                    continue;

                boolean account_bool = true;
                while (account_bool) {

                    System.out.println("1. Add Account");
                    System.out.println("2. View Account");
                    System.out.println("3. Remove Account");
                    System.out.println("4. Exit");
                    choice = getValidatedIntInput(scanner,"Enter your choice:");
                    if (choice == 1) {
                        AccountType account_type = inputAccountType();
                        if (account_type == null) {
                            continue;
                        }
                        Account new_account = null;
                        if (account_type == AccountType.CHECKING) {
                            new_account = current_customer.addAccount(account_type, 0, 0);
                            accountDatabase.create(new_account, account_type);

                        } else if (account_type == AccountType.SAVING) {
                            System.out.println("enter interest rate");
                            int interest_rate = scanner.nextInt();
                            scanner.nextLine();
                            new_account = current_customer.addAccount(account_type, interest_rate, 0);
                            accountDatabase.create(new_account, account_type);
                        } else {

                            System.out.println("enter credit limit");
                            int credit_limit = scanner.nextInt();
                            scanner.nextLine();
                            new_account = current_customer.addAccount(account_type, 0, credit_limit);
                            accountDatabase.create(new_account,   account_type);

                        }
                        ActionLogger.logAction("added account with id:" + new_account.getNo_account());

                    } else if (choice == 2) {
                        Account account = inputAccount(scanner,current_customer);
                        if (account == null) {
                            System.out.println("Account not found");
                        } else {
                            boolean account_operations = true;
                            while (account_operations) {
                                if (account instanceof CardAccount) {
                                    System.out.println("0. See Cards");
                                }
                                System.out.println("1. Initiate a transaction");
                                System.out.println("2. View Balance");
                                System.out.println("3. Exit");
                                int account_choice =getValidatedIntInput(scanner,"Enter your choice:");


                                if (account instanceof CardAccount) {
                                    if (account_choice == 0) {
                                        boolean card_operations = true;
                                        while (card_operations) {
                                            System.out.println("0.See Cards' Details");
                                            System.out.println("1.Add Card");
                                            System.out.println("2.Remove Card");

                                            int card_choice =  getValidatedIntInput(scanner,"Enter your choice:");

                                            if (card_choice == 0) {
                                                ((CardAccount) account).printCards();
                                            } else if (card_choice == 1) {
                                                Card new_card = ((CardAccount) account).addCard();
                                                cardDatabase.create(new_card);
                                                ActionLogger.logAction("added card with card_number:" + new_card.getCard_number());
                                                System.out.println("card created successfully");

                                            } else if (card_choice == 2) {
                                                Card card_to_remove = inputRemoveCard(scanner,(CardAccount) account);
                                                if (card_to_remove != null) {
                                                    ((CardAccount) account).removeCard(card_to_remove);
                                                    cardDatabase.delete(card_to_remove);
                                                    ActionLogger.logAction("removed card with card_number:" + card_to_remove.getCard_number());
                                                }

                                            } else {
                                                System.out.println("invalid choice");
                                            }
                                            card_operations = false;

                                        }
                                    }
                                }

                                if (account_choice == 1) {
                                    TransactionType transaction_type = inputTransactionType();
                                    double amount = getValidatedDoubleInput(scanner,"Enter amount:");;
                                    if (transaction_type == null)
                                        continue;
                                    if (transaction_type == TransactionType.TRANSFER) {
                                        System.out.println("enter account number for transfer");
                                        String no_t_account = scanner.nextLine();
                                        Account t_account = findAccount(no_t_account);
                                        if (t_account == null) {
                                            System.out.println("account number is invalid");
                                            continue;
                                        } else {
                                            System.out.println("enter description");
                                            String description = scanner.nextLine();
                                            Transaction transaction = account.makeTransaction(transaction_type,
                                                    t_account, amount, description);
                                            if (transaction != null) {
                                                transactionDatabase.create(transaction, transaction_type);
                                                ActionLogger.logAction("added transaction");
                                                accountDatabase.updateBalance(account, -amount);
                                                accountDatabase.updateBalance(t_account, amount);
                                            }

                                        }
                                    } else {
                                        Transaction transaction = account.makeTransaction(transaction_type, account,
                                                amount, null);
                                        if (transaction != null) {
                                            transactionDatabase.create(transaction, transaction_type);
                                            ActionLogger.logAction("added transaction");

                                            if (transaction_type == TransactionType.DEPOSIT) {
                                                accountDatabase.updateBalance(account, amount);
                                            } else
                                                accountDatabase.updateBalance(account, -amount);

                                        }

                                    }

                                } else if (account_choice == 2) {
                                    System.out.println("Balance: " + account.getBalance());
                                } else if (account_choice == 3) {
                                    account_operations = false;
                                } else if (!(account instanceof CardAccount)) {
                                    System.out.println("Invalid Choice000");
                                }
                            }
                        }
                    } else if (choice == 3) {
                        Account account = inputAccount(scanner,current_customer);
                        if (account == null) {
                            System.out.println("Account not found");
                        } else {
                            accountDatabase.delete(account);
                            ActionLogger.logAction("removed account with id:" + account.getNo_account());
                            current_customer.removeAccount(account);
                        }
                    } else if (choice == 4) {
                        account_bool = false;
                    } else {
                        System.out.println("Invalid Choice");
                    }
                }

            } else {
                customer_bool = false;
            }
        }
    }

    private Card inputRemoveCard(Scanner scanner,CardAccount account) {

        for (int i = 0; i < account.cards.size(); i++) {
            System.out.println(i + " " + account.cards.get(i).toString());

        }
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 0 || choice >= account.cards.size()) {
            return null;

        }
        return account.cards.get(choice);

    }

    private Account findAccount(String noTAccount) {
        for (var customer : customerList)
            for (var account : customer.accounts) {
                if (account.getNo_account().equals(noTAccount))
                    return account;
            }
        return null;
    }

    private TransactionType inputTransactionType() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("select transaction type");
        for (int i = 0; i < TransactionType.values().length; i++) {
            System.out.println(Integer.toString(i) + " " + TransactionType.values()[i]);

        }
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 0 || choice >=TransactionType.values().length)
            return null;
        else
            return TransactionType.values()[choice];

    }

    private Account inputAccount(Scanner scanner,Customer customer) {
        System.out.println("select account");
        for (int i = 0; i < customer.accounts.size(); i++) {
            System.out.println(i + " " + customer.accounts.get(i).toString());
        }
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 0 || choice >= customer.accounts.size()) {

            return null;
        } else
            return customer.accounts.get(choice);

    }

    private AccountType inputAccountType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("select account type");
        for (var i = 0; i < AccountType.values().length; i++) {
            System.out.println(Integer.toString(i) + " " + AccountType.values()[i]);

        }
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 0 || choice >= AccountType.values().length) {
            System.out.println("invalid choice");
            return null;
        } else
            return AccountType.values()[choice];

    }

}
