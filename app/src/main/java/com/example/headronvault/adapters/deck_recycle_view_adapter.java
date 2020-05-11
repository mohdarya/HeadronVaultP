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

import java.util.List;

public class deck_recycle_view_adapter extends RecyclerView.Adapter<deck_recycle_view_adapter.ViewHolder>
{

   List<deck_page_data> data;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView deckName;
        public ImageView deckBox;

        public ViewHolder(View itemView)
        {
            super(itemView);

            deckName = (TextView) itemView.findViewById(R.id.deck_page_items_text);
            deckBox = (ImageView) itemView.findViewById(R.id.deck_page_items_image);

        }

    }

    public deck_recycle_view_adapter(List<deck_page_data> data)
    {
        this.data = data;
    }


    @NonNull
    @Override
    public deck_recycle_view_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View collectionView = inflater.inflate(R.layout.page_item_deck, parent, false);

        ViewHolder viewholder = new ViewHolder(collectionView);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        deck_page_data data = this.data.get(position);



        holder.deckName.setText(data.getDeckName());
        holder.deckBox.setImageResource(data.getimageID());
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }

}
