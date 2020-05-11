package com.example.headronvault.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.headronvault.data.deck_page_data;

public class deck_page_data_parce implements Parcelable
{
    public String deckName;
    public int imageID;
    public int deckID;
    public String deckColor;
     static int lastCardID = 0;
    deck_page_data_parce(String deckName, int imageID, int deckID,String deckColor)
    {
        this.deckName = deckName;
        this.imageID = imageID;
        this.deckID = deckID;
        this.deckColor = deckColor;
    }

    public deck_page_data_parce(deck_page_data data)
    {
        this.deckName = data.getDeckName();
        this.imageID = data.getimageID();
        this.deckID = data.getDeckID();
        this.deckColor = data.getDeckColor();
    }
    protected deck_page_data_parce(Parcel in)
    {
        deckName = in.readString();
        imageID = in.readInt();
        deckID = in.readInt();
        deckColor = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(deckName);
        dest.writeInt(imageID);
        dest.writeInt(deckID);
        dest.writeString(deckColor);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<deck_page_data_parce> CREATOR = new Creator<deck_page_data_parce>()
    {
        @Override
        public deck_page_data_parce createFromParcel(Parcel in)
        {
            return new deck_page_data_parce(in);
        }

        @Override
        public deck_page_data_parce[] newArray(int size)
        {
            return new deck_page_data_parce[size];
        }
    };
}
