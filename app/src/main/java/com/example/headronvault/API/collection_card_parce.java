package com.example.headronvault.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class collection_card_parce implements Parcelable
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
    @SerializedName("Count")
    public Integer count;
    @SerializedName("ManaCost")
    public String manaCost;
    @SerializedName("UserID")
    public Integer userID;

    public collection_card_parce(collection_card data)
    {
        cardArt = data.cardArt;
        ID = data.ID;
        name = data.name;
        power = data.power;
        toughness = data.toughness;
        rarity = data.rarity;
        text = data.text;
        type = data.type;
        count = data.count;
        manaCost = data.manaCost;
        userID = data.userID;
    }
    protected collection_card_parce(Parcel in)
    {
        cardArt = in.readString();
        if (in.readByte() == 0) { ID = null; } else { ID = in.readInt(); }
        name = in.readString();
        power = in.readString();
        toughness = in.readString();
        rarity = in.readString();
        text = in.readString();
        type = in.readString();
        if (in.readByte() == 0) { count = null; } else { count = in.readInt(); }
        manaCost = in.readString();
        if (in.readByte() == 0) { userID = null; } else { userID = in.readInt(); }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(cardArt);
        if (ID == null) { dest.writeByte((byte) 0); } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(ID);
        }
        dest.writeString(name);
        dest.writeString(power);
        dest.writeString(toughness);
        dest.writeString(rarity);
        dest.writeString(text);
        dest.writeString(type);
        if (count == null) { dest.writeByte((byte) 0); } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(count);
        }
        dest.writeString(manaCost);
        if (userID == null) { dest.writeByte((byte) 0); } else
        {
            dest.writeByte((byte) 1);
            dest.writeInt(userID);
        }
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<collection_card_parce> CREATOR = new Creator<collection_card_parce>()
    {
        @Override
        public collection_card_parce createFromParcel(Parcel in)
        {
            return new collection_card_parce(in);
        }

        @Override
        public collection_card_parce[] newArray(int size)
        {
            return new collection_card_parce[size];
        }
    };
}
