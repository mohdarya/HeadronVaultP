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
import com.example.headronvault.data.make_a_deck_art_data;

import java.util.ArrayList;

public class make_a_deck_art_recycler_adapter extends RecyclerView.Adapter<make_a_deck_art_recycler_adapter.ViewHolder>
{

    ArrayList<make_a_deck_art_data> data;
    @NonNull
    @Override
    public make_a_deck_art_recycler_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View collectionView = inflater.inflate(R.layout.page_items_make_deck, parent, false);

        ViewHolder viewholder = new ViewHolder(collectionView);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull make_a_deck_art_recycler_adapter.ViewHolder holder, int position)
    {
        make_a_deck_art_data data = this.data.get(position);



        holder.deckName.setText(data.getDeckName());
        holder.deckImage.setImageResource(data.getImageID());

    }

    public make_a_deck_art_recycler_adapter(ArrayList<make_a_deck_art_data> data)
    {
        this.data = data;
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        ImageView deckImage;
        TextView deckName;

        public ViewHolder(View itemView)
        {
            super(itemView);

            deckImage = itemView.findViewById(R.id.page_item_make_deck_art_image);
            deckName = itemView.findViewById(R.id.page_item_make_deck_art_text);


        }
        @Override
        public void onClick(View v)
        {

        }

        @Override
        public boolean onLongClick(View v)
        {
            return false;
        }
    }
}
