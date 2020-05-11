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
import com.example.headronvault.data.make_a_deck_art_data;
import com.example.headronvault.adapters.make_a_deck_art_recycler_adapter;
import com.example.headronvault.adapters.recycle_view_click_listener;
import com.example.headronvault.select_deck_artArgs;
import com.example.headronvault.select_deck_artDirections;

import java.util.ArrayList;

public class select_deck_art extends Fragment
{

    private ArrayList<make_a_deck_art_data> data = new ArrayList<>();
    private make_a_deck_art_recycler_adapter adapter;

    private OnFragmentInteractionListener mListener;

    public select_deck_art()
    {
        // Required empty public constructor
    }


    public static select_deck_art newInstance(String param1, String param2)
    {
        select_deck_art fragment = new select_deck_art();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        prepareData();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.page_make_deck_art, container, false);
    }

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

        final collection_page_data_parce selectedCards[] = select_deck_artArgs.fromBundle(getArguments()).getSelectedCards();

        final RecyclerView deckartrv = (RecyclerView) view.findViewById(R.id.make_a_deck_art_recycler);

        adapter = new make_a_deck_art_recycler_adapter(data);
        deckartrv.setAdapter(adapter);
        deckartrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        deckartrv.addOnItemTouchListener(new recycle_view_click_listener(getContext(), deckartrv, new recycle_view_click_listener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
               select_deck_artDirections.ActionSelectDeckArtToNameDeck action = select_deck_artDirections.actionSelectDeckArtToNameDeck(selectedCards, data.get(position).deckName);
                Navigation.findNavController(view).navigate(action);
            }

            @Override
            public void onLongItemClick(View view, int position)
            {

            }
        }));


    }


    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener
    {

        void onFragmentInteraction(Uri uri);
    }

    private void prepareData()
    {
        data.add(new make_a_deck_art_data("Baige", getID("deck_box_baige", "mipmap")));
        data.add(new make_a_deck_art_data("Black", getID("deck_box_black", "mipmap")));
        data.add(new make_a_deck_art_data("Brown", getID("deck_box_brown", "mipmap")));
        data.add(new make_a_deck_art_data("Dark Blue", getID("deck_box_darkblue", "mipmap")));
        data.add(new make_a_deck_art_data("Green", getID("deck_box_green", "mipmap")));
        data.add(new make_a_deck_art_data("Grey", getID("deck_box_grey", "mipmap")));
        data.add(new make_a_deck_art_data("Light Blue", getID("deck_box_lightblue", "mipmap")));
        data.add(new make_a_deck_art_data("Purple", getID("deck_box_purple", "mipmap")));
        data.add(new make_a_deck_art_data("Red", getID("deck_box_red", "mipmap")));
        data.add(new make_a_deck_art_data("Yellow", getID("deck_box_yellow", "mipmap")));
    }

    private int getID(String id, String defType)
    {
        return getResources().getIdentifier(id, defType, getContext().getPackageName());
    }

}
