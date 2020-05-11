package com.example.headronvault.data;

import android.os.Parcel;
import android.os.Parcelable;

public class edit_cards_in_deck_data implements Parcelable
{
    public int count;
    final int cardCount;
    public int imageId;
    public int deckId;
    public int cardId;
    public String name;
    public boolean edited;
    public edit_cards_in_deck_data(int count, int cardCount, int imageId, int deckId, int cardId, String name)
    {
        this.count = count;
        this.cardCount = cardCount;
        this.imageId = imageId;
        this.deckId = deckId;
        this.cardId = cardId;
        this.name = name;
    }

    protected edit_cards_in_deck_data(Parcel in)
    {
        count = in.readInt();
        cardCount = in.readInt();
        imageId = in.readInt();
        deckId = in.readInt();
        cardId = in.readInt();
        name = in.readString();
    }

    public static final Creator<edit_cards_in_deck_data> CREATOR = new Creator<edit_cards_in_deck_data>()
    {
        @Override
        public edit_cards_in_deck_data createFromParcel(Parcel in)
        {
            return new edit_cards_in_deck_data(in);
        }

        @Override
        public edit_cards_in_deck_data[] newArray(int size)
        {
            return new edit_cards_in_deck_data[size];
        }
    };

    public boolean addCount()
    {
        edited = true;
        if(count < cardCount)
        {
            count++;
            return true;


        }
        return false;
    }
    public boolean removeCount()
    {
        edited = true;
        if(count > 0)
        {
            count--;
            return true;
        }
        return false;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(count);
        dest.writeInt(cardCount);
        dest.writeInt(imageId);
        dest.writeInt(deckId);
        dest.writeInt(cardId);
        dest.writeString(name);
    }
}
