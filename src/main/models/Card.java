package main.models;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;

final public class Card {

    final String card_number;
    final Date creation_date;
    final Date expiration_date;

    final String cvv;
    private String pin;


    private String generateCardNumber(int n) {

        long randomNumber = new SecureRandom().nextLong();
        return String.format("%016d", randomNumber).substring(0, n);

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

    public Card() {
        card_number=generateCardNumber(15);
        creation_date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(creation_date);
        calendar.add(Calendar.YEAR, 5);
        expiration_date = calendar.getTime();
        cvv=generateCardNumber(2);
        pin="0000";

    }
    public void setPin(String new_pin){
        this.pin=new_pin;
    }



}
