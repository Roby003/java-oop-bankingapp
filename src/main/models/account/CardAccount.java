package main.models.account;

import main.models.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CardAccount extends Account {
    public List<Card> cards;

    public CardAccount(int customerId,float interest_rate) {
        super(customerId,interest_rate);
        this.cards = new ArrayList<Card>();

    }

    public void addCard(ResultSet in) throws SQLException {
        cards.add(new Card(in));
    }
    public Card addCard() {
        Card card = new Card(no_account);
        this.cards.add(card);
        return card;

    }


    public CardAccount(ResultSet in) throws SQLException {
        super(in);
        this.cards = new ArrayList<Card>();
    }


    public void removeCard(Card card) {
        try {
            if (cards.remove(card)) {
                System.out.println("card successfully removed");

            } else {
                System.out.println("card not in list");
            }

        } catch (Exception ex) {
            System.out.println("object is null");
        }
    }

    public void printCards() {
        for (var card : cards) {
            System.out.println(card);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        CardAccount that = (CardAccount) o;
        return Objects.equals(cards, that.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cards);
    }
}
