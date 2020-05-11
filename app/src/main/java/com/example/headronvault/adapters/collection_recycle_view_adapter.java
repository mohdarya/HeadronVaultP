package com.example.headronvault.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.headronvault.R;
import com.example.headronvault.data.collection_page_data;

import java.util.List;

public class collection_recycle_view_adapter extends RecyclerView.Adapter<collection_recycle_view_adapter.ViewHolder>
{

   List<collection_page_data> data;
    private LayoutInflater mInflater;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {

        public TextView cardName;
        public ImageView cardImage;
        public ConstraintLayout layout;
        public ViewHolder(View itemView)
        {
            super(itemView);

            cardName = (TextView) itemView.findViewById(R.id.collection_page_items_text);
            cardImage = (ImageView) itemView.findViewById(R.id.collection_page_items_image);
            layout = (ConstraintLayout) itemView.findViewById(R.id.collection_page_items_layout);
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

    public collection_recycle_view_adapter(List<collection_page_data> data)
    {
        this.data = data;
    }


    @NonNull
    @Override
    public collection_recycle_view_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View collectionView = inflater.inflate(R.layout.page_items_collection, parent, false);

        ViewHolder viewholder = new ViewHolder(collectionView);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull collection_recycle_view_adapter.ViewHolder holder, int position)
    {
        collection_page_data data = this.data.get(position);



        holder.cardName.setText(data.getCardName());
        holder.cardImage.setImageResource(data.getimageID());
        if(data.isSelected())
        {
            holder.layout.setBackgroundColor(Color.parseColor("#000000"));
        }
    }

    @Override
    public int getItemCount()
    {
        return data.size();
    }


}
