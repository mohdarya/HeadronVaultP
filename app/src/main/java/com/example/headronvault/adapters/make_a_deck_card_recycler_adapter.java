package com.example.headronvault.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headronvault.R;
import com.example.headronvault.data.edit_cards_in_deck_data;
import com.example.headronvault.data.make_a_deck_card_data;

import java.util.ArrayList;

public class make_a_deck_card_recycler_adapter extends RecyclerView.Adapter<make_a_deck_card_recycler_adapter.ViewHolder>
{



    public ArrayList<make_a_deck_card_data> data;
    public ArrayList<edit_cards_in_deck_data> editData;
    String dataToUse;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onAddClicked(int position);
        void onItemClick(int position);
        void onRemovedClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView cardImage;
        TextView cardSelected;
        TextView cardName;
        ImageView addCard;
        ImageView removeCard;
        public ViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            cardImage = itemView.findViewById(R.id.page_item_make_deck_art_image);
            cardName = itemView.findViewById(R.id.page_item_make_deck_art_text);
            cardSelected = itemView.findViewById(R.id.page_item_make_deck_selected);
            addCard = itemView.findViewById(R.id.page_item_make_deck_add);
            removeCard = itemView.findViewById(R.id.page_item_make_deck_remove);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            addCard.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v)
                {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onAddClicked(position);
                        }
                    }

                }
            });


            removeCard.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v)
                {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onRemovedClicked(position);
                        }
                    }

                }
            });



        }

    }
    public make_a_deck_card_recycler_adapter(ArrayList<make_a_deck_card_data> data)
    {
        this.data = data;
        dataToUse = "data";

    }
    public make_a_deck_card_recycler_adapter()
    {

    }

    public void setEditData(ArrayList<edit_cards_in_deck_data> editData)
    {
        this.editData = editData;
        dataToUse = "edit";
    }

    @NonNull
    @Override
    public make_a_deck_card_recycler_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View collectionView = inflater.inflate(R.layout.page_items_make_deck, parent, false);

        ViewHolder viewholder = new ViewHolder(collectionView, mListener);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull make_a_deck_card_recycler_adapter.ViewHolder holder, int position)
    {
        if(dataToUse.equals("data"))
        {
            make_a_deck_card_data data = this.data.get(position);
            holder.cardName.setText(data.getCardName());
            holder.cardImage.setImageResource(data.getImageID());
            holder.cardSelected.setText(String.valueOf(data.getCount()));
        }
        else
        {
            edit_cards_in_deck_data data = this.editData.get(position);
            holder.cardName.setText(data.name);
            holder.cardImage.setImageResource(data.imageId);
            holder.cardSelected.setText(String.valueOf(data.count));

        }
    }



    @Override
    public int getItemCount()
    {
        if(dataToUse.equals("data"))
        {
            return data.size();
        }
        else
        {
           return editData.size();

        }

    }

   




    }
