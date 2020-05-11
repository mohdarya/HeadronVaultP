package com.example.headronvault.API;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.headronvault.data.collection_page_data;

public class collection_page_data_parce implements Parcelable
{
     String cardName;
     public int imageID;
     public int cardRarityText;
     public int cardRarityColor;
     public int cardID;
     public int count;
    private static int lastCardID = 0;
    collection_page_data_parce(String cardName, int imageID, int cardRarityText, int cardRarityColor, int cardID)
    {
        this.cardName = cardName;
        this.imageID = imageID;
        this.cardRarityText = cardRarityText;
        this.cardRarityColor = cardRarityColor;
        this.cardID = cardID;
    }
    collection_page_data_parce()
    {

    }
    public collection_page_data_parce(collection_page_data data)
    {
        this.cardName = data.cardName;
        this.cardID = data.getCardID();
        this.cardRarityColor = data.getCardRarityColor();
        this.cardRarityText = data.getCardRarityText();
        this.imageID = data.getimageID();
    }

    protected collection_page_data_parce(Parcel in)
    {
        cardName = in.readString();
        imageID = in.readInt();
        cardRarityText = in.readInt();
        cardRarityColor = in.readInt();
        cardID = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(cardName);
        dest.writeInt(imageID);
        dest.writeInt(cardRarityText);
        dest.writeInt(cardRarityColor);
        dest.writeInt(cardID);

    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    public static final Creator<collection_page_data_parce> CREATOR = new Creator<collection_page_data_parce>()
    {
        @Override
        public collection_page_data_parce createFromParcel(Parcel in)
        {
            return new collection_page_data_parce(in);
        }

        @Override
        public collection_page_data_parce[] newArray(int size)
        {
            return new collection_page_data_parce[size];
        }
    };

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getCount()
    {
        return count;
    }
}
