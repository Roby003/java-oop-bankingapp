package main.models.account;

public class CreditAccount extends CardAccount{
    double credit_limit;

    @Override
    public String toString() {
        return "CreditAccount{" +
                "credit_limit=" + credit_limit +
                ", no_account='" + no_account + '\'' +
                ", creation_date=" + creation_date +
                ", balance=" + balance +
                ", interest_rate=" + interest_rate +
                '}';
    }

    public CreditAccount(double credit_limit) {
        super(0);
        this.credit_limit = credit_limit;
        super.balance=credit_limit;


    }

}
