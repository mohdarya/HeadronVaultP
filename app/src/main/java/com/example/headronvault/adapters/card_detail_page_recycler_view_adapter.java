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
import com.example.headronvault.data.deck_page_data;

import java.util.ArrayList;

public class card_detail_page_recycler_view_adapter extends RecyclerView.Adapter<card_detail_page_recycler_view_adapter.ViewHolder>
{

    ArrayList<deck_page_data> data;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        public TextView cardName;
        public ImageView cardImage;

        public ViewHolder(View itemView)
        {
            super(itemView);

            cardName = (TextView) itemView.findViewById(R.id.card_detail_page_deck_item_name);
            cardImage = (ImageView) itemView.findViewById(R.id.card_detail_page_deck_item_image);

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

    @NonNull
    @Override
    public card_detail_page_recycler_view_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View cardDetailView = inflater.inflate(R.layout.page_items_card_detail_deck, parent, false);

       ViewHolder viewholder = new ViewHolder(cardDetailView);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull card_detail_page_recycler_view_adapter.ViewHolder holder, int position)
    {
        deck_page_data data = this.data.get(position);



        holder.cardName.setText(data.getDeckName());
        holder.cardImage.setImageResource(data.getimageID());
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

    public card_detail_page_recycler_view_adapter(ArrayList<deck_page_data> data)
    {
        this.data = data;
    }
}
