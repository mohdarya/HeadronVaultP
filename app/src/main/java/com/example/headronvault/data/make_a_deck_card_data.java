package com.example.headronvault.data;

public class make_a_deck_card_data
{
    String cardName;
    int imageID;
    private int count = 0;
    private final int cardCount;
    boolean isSelected = false;
    public make_a_deck_card_data(String cardName, int imageID, int cardCount)
    {
        this.cardCount = cardCount;
        this.cardName = cardName;
        this.imageID = imageID;
    }

    public int getImageID()
    {
        return imageID;
    }

    public String getCardName()
    {
        return cardName;
    }

    public boolean addCount()
    {
        if(count < cardCount)
        {
            count++;
            return true;

        }
        return false;
    }
    public boolean removeCount()
    {
        if(count > 0)
        {
            count--;
            return true;
        }
        return false;
    }

    public int getCount()
    {
        return count;
    }
}
