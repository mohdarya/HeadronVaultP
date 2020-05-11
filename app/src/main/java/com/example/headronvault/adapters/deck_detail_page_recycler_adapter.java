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
import com.example.headronvault.data.collection_page_data;

import java.util.List;

public class deck_detail_page_recycler_adapter extends RecyclerView.Adapter<deck_detail_page_recycler_adapter.ViewHolder>
{

    List<collection_page_data> data;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        public TextView cardName;
        public ImageView cardImage;

        public ViewHolder(View itemView)
        {
            super(itemView);

            cardName = (TextView) itemView.findViewById(R.id.page_items_deck_details_card_name);
            cardImage = (ImageView) itemView.findViewById(R.id.page_items_deck_details_card_image);

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

    public deck_detail_page_recycler_adapter(List<collection_page_data> data)
    {
        this.data = data;
    }

    @NonNull
    @Override
    public deck_detail_page_recycler_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View collectionView = inflater.inflate(R.layout.page_items_deck_details, parent, false);

        deck_detail_page_recycler_adapter.ViewHolder viewholder = new deck_detail_page_recycler_adapter.ViewHolder(collectionView);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull deck_detail_page_recycler_adapter.ViewHolder holder, int position)
    {
        collection_page_data data = this.data.get(position);



        holder.cardName.setText(data.getCardName());
        holder.cardImage.setImageResource(data.getimageID());
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }
}
