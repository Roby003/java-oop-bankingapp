package main.models;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.sql.ResultSet;
import java.sql.SQLException;

final public class Card {

    private
    String no_account;
    final String card_number;
    final Date creation_date;
    final Date expiration_date;
    final String cvv;
    private String pin;


    private String generateCardNumber(int n) {


        long randomNumber = Math.abs(new SecureRandom().nextLong());
        return String.format("%016d", randomNumber).substring(0, n);

    }


    public Card(String no_account) {
        this.no_account = no_account;
        card_number=generateCardNumber(16);
        creation_date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creation_date);
        calendar.add(Calendar.YEAR, 5);
        expiration_date = calendar.getTime();
        cvv=generateCardNumber(3);
        pin="0000";

    }
    public Card(ResultSet in) throws SQLException {
        no_account=in.getString("no_account");
        card_number=in.getString("card_number");
        creation_date=in.getDate("creation_date");
        expiration_date=in.getDate("expiration_date");
        cvv=in.getString("cvv");
        pin=in.getString("pin");
    }
    public void setPin(String new_pin){
        this.pin=new_pin;
    }
    @Override
    public String toString() {
        return "Card{" +
                "card_number='" + card_number + '\'' +
                ", creation_date=" + creation_date +
                ", expiration_date=" + expiration_date +
                ", cvv='" + cvv + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(card_number, card.card_number) && Objects.equals(creation_date, card.creation_date) && Objects.equals(expiration_date, card.expiration_date) && Objects.equals(cvv, card.cvv) && Objects.equals(pin, card.pin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card_number, creation_date, expiration_date, cvv, pin);
    }

    public String getNo_account() {
        return no_account;
    }

    public String getCard_number() {
        return card_number;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public String getCvv() {
        return cvv;
    }

    public String getPin() {
        return pin;
    }
}

