package com.example.headronvault.API;

import com.google.gson.annotations.SerializedName;

public class GeneralCard
{
    @SerializedName("CardArt")
    public String cardArt;
    @SerializedName("CardID")
    public Integer ID;
    @SerializedName("CardName")
    public String name;
    @SerializedName("CardPower")
    public String power;
    @SerializedName("CardToughness")
    public String toughness;
    @SerializedName("CardRarity")
    public String rarity;
    @SerializedName("CardText")
    public String text;
    @SerializedName("CardType")
    public String type;
    @SerializedName("ManaCost")
    public String manaCost;
}
