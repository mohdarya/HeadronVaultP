package com.example.headronvault.API;

import com.google.gson.annotations.SerializedName;

public class Deck {

    @SerializedName("DeckCoverArt")
    public String coverArt;
    @SerializedName("DeckID")
    public Integer ID;
    @SerializedName("DeckName")
    public String name;
    @SerializedName("DeckColor")
    public String deckColor;
}
