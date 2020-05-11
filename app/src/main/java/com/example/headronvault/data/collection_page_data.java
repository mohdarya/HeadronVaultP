package com.example.headronvault.data;

public class collection_page_data
{
    public String cardName;
    public int imageID;
    private int cardRarityText;
    private int cardRarityColor;
    private int cardID;
    private static int lastCardID = 0;
   public boolean isSelected = false;
    public collection_page_data(String cardName, int imageID, int cardRarityText, int cardRarityColor, int cardID)
    {
        this.cardName = cardName;
        this.imageID = imageID;
        this.cardRarityText = cardRarityText;
        this.cardRarityColor = cardRarityColor;
        this.cardID = cardID;
    }
    collection_page_data()
    {

    }
    public String getCardName()
    {
        return cardName;
    }

    public int getimageID()
    {
        return imageID;
    }


    public int getCardRarityColor()
    {
        return cardRarityColor;
    }

    public int getCardRarityText()
    {
        return cardRarityText;
    }

    public int getCardID()
    {
        return cardID;
    }

    public void setSelected(boolean selected)
    {
        isSelected = selected;
    }

    public boolean isSelected()
    {
        return isSelected;
    }
}
