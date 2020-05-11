package com.example.headronvault.data;

import com.example.headronvault.API.deck_page_data_parce;

public class deck_page_data
{
    private String deckName;
    private int imageID;
    private int deckID;
    private String deckColor;
    private static int lastCardID = 0;
    public deck_page_data(String deckName, int imageID, int deckID, String deckColor)
    {
        this.deckName = deckName;
        this.imageID = imageID;
        this.deckID = deckID;
        this.deckColor = deckColor;
    }

    deck_page_data(deck_page_data_parce data)
    {
        deckName  = data.deckName;
        imageID = data.imageID;
        deckID = data.deckID;
        deckColor = data.deckColor;
    }

    public String getDeckName()
    {
        return deckName;
    }

    public int getimageID()
    {
        return imageID;
    }

    public String getDeckColor()
    {
        return deckColor;
    }

    public int getDeckID()
    {
        return deckID;
    }
}
