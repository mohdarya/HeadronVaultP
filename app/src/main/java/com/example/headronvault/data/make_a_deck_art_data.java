package com.example.headronvault.data;

public class make_a_deck_art_data
{
    public String deckName;
    public int imageID;
    boolean isSelected = false;
    public make_a_deck_art_data(String deckName, int imageID)
    {
        this.deckName = deckName;
        this.imageID = imageID;
    }

    public int getImageID()
    {
        return imageID;
    }

    public String getDeckName()
    {
        return deckName;
    }
}
