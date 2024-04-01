package main.models.account;

import main.models.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class CardAccount extends Account{
    public List<Card> cards;

    public CardAccount(float interest_rate) {
        super(interest_rate);
        this.cards =new ArrayList<Card>();

    }
    public void addCard()
    {
        this.cards.add(new Card());

    }
public void removeCard(Card card)
{
    try
    {
        if(cards.remove(card))
        {
            System.out.println("card successfully removed");

        }
        else
        {
            System.out.println("card not in list");
        }

    }
    catch (Exception ex)
    {
        System.out.println("object is null");
    }
}

public void printCards()
{
    for(var card : cards)
    {
        System.out.println(card);
    }

}



}
