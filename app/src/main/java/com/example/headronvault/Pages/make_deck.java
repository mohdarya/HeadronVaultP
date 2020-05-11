package com.example.headronvault.Pages;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.headronvault.API.collection_page_data_parce;
import com.example.headronvault.R;
import com.example.headronvault.data.make_a_deck_card_data;
import com.example.headronvault.adapters.make_a_deck_card_recycler_adapter;
import com.example.headronvault.make_deckDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class make_deck extends Fragment
{


    private ArrayList<make_a_deck_card_data> data = new ArrayList<>();
    private make_a_deck_card_recycler_adapter adapter;
    RecyclerView makeADeckrv;
    private OnFragmentInteractionListener mListener;

    public make_deck()
    {
        
    }
    
    public static make_deck newInstance(String param1, String param2)
    {
        make_deck fragment = new make_deck();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        fetchData();
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.page_make_deck, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        buildRecyclerView();


    }

    
    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void updateRecycleView()
    {
        adapter.notifyItemChanged(adapter.getItemCount());
    }
    

    private int getID(String id, String defType)
    {
        return getResources().getIdentifier(id, defType, getContext().getPackageName());
    }
    
    private void fetchData()
    {
        for (int i = 0; i < MainActivity.collection_data.size(); i++)
        {
            data.add(new make_a_deck_card_data(MainActivity.collection_data.get(i).getCardName(), MainActivity.collection_data.get(i).getimageID(), MainActivity.collectionApiResults.get(i).count));
        }
    }

    private collection_page_data_parce[] getCardsSelects()
    {
        ArrayList<collection_page_data_parce> selected = new ArrayList<>();
        for(int i = 0, k= 0; i < data.size(); i++)
        {
            if(data.get(i).getCount() > 0)
            {

                selected.add(new collection_page_data_parce(MainActivity.collection_data.get(i)));
                selected.get(k).setCount(data.get(i).getCount());
                k++;

            }
        }

        return selected.toArray(new collection_page_data_parce[0]);
    }

    public void buildRecyclerView()
    {

        makeADeckrv = (RecyclerView) getActivity().findViewById(R.id.make_a_deck_art_recycler);
        adapter = new make_a_deck_card_recycler_adapter(data);

        makeADeckrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        makeADeckrv.setAdapter(adapter);

        adapter.setOnItemClickListener(new make_a_deck_card_recycler_adapter.OnItemClickListener()
        {
            @Override
            public void onAddClicked(int position)
            {
                adapter.data.get(position).addCount();
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onRemovedClicked(int position)
            {
                adapter.data.get(position).removeCount();
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onItemClick(int position)
            {
                System.out.println("test");
            }


        });


        FloatingActionButton makeADeck = getActivity().findViewById(R.id.make_a_deck_art_fob);
        makeADeck.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                make_deckDirections.ActionMakeDeckToSelectDeckArt action = make_deckDirections.actionMakeDeckToSelectDeckArt(getCardsSelects());
                Navigation.findNavController(v).navigate(action);

            }
        });
    }

}
