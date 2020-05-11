package com.example.headronvault.Pages;


import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.headronvault.API.APIClient;
import com.example.headronvault.API.APIInterface;
import com.example.headronvault.API.ApiMessage;
import com.example.headronvault.API.collection_card;
import com.example.headronvault.API.collection_card_parce;
import com.example.headronvault.API.collection_page_data_parce;
import com.example.headronvault.API.deck_card;
import com.example.headronvault.API.deck_page_data_parce;
import com.example.headronvault.R;
import com.example.headronvault.data.collection_page_data;
import com.example.headronvault.deck_detail_pageArgs;
import com.example.headronvault.deck_detail_pageDirections;
import com.example.headronvault.adapters.deck_detail_page_recycler_adapter;
import com.example.headronvault.data.edit_cards_in_deck_data;
import com.example.headronvault.adapters.recycle_view_click_listener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class deck_detail_page extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    deck_page_data_parce data;
    private APIInterface apiInterface;
    private List<collection_page_data> deck_card_list = new ArrayList<>();
    private List<deck_card> apiResults;
    private collection_card apiResultsCollection = new collection_card();
    private deck_detail_page_recycler_adapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public deck_detail_page() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        setHasOptionsMenu(true);

        if (getArguments() != null) {

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        data = deck_detail_pageArgs.fromBundle(getArguments()).getDeckData();
        deck_card_list.clear();
        getCardsInDeck();
        ImageView deckArt = view.findViewById((R.id.deck_page_deck_box));
        TextView deckName = view.findViewById(R.id.deck_page_deck_name);
        deckArt.setImageResource(data.imageID);
        deckName.setText(data.deckName);
        if(data.deckColor.contains("Black"))
        {
            ImageView color = view.findViewById(R.id.deck_page_black_color);
            color.setVisibility(View.VISIBLE);
        }
        if(data.deckColor.contains("Green"))
        {
            ImageView color = view.findViewById(R.id.deck_page_green_color);
            color.setVisibility(View.VISIBLE);
        }
        if(data.deckColor.contains("Red"))
        {
            ImageView color = view.findViewById(R.id.deck_page_red_color);
            color.setVisibility(View.VISIBLE);
        }
        if(data.deckColor.contains("Blue"))
        {
            ImageView color = view.findViewById(R.id.deck_page_blue_color);
            color.setVisibility(View.VISIBLE);
        }
        if(data.deckColor.contains("White"))
        {
            ImageView color = view.findViewById(R.id.deck_page_white_color);
            color.setVisibility(View.VISIBLE);
        }

        RecyclerView collectionrv = (RecyclerView) view.findViewById(R.id.deck_page_detail_recyclerView);
        adapter = new deck_detail_page_recycler_adapter(deck_card_list);
        collectionrv.setAdapter(adapter);
        collectionrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        collectionrv.addOnItemTouchListener(new recycle_view_click_listener(getContext(), collectionrv, new recycle_view_click_listener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                getCollectionCards(apiResults.get(position));
                deck_detail_pageDirections.ActionDeckDetailPageToCardDetailPage action = deck_detail_pageDirections.actionDeckDetailPageToCardDetailPage(new collection_card_parce(apiResultsCollection), new collection_page_data_parce(deck_card_list.get(position)));
                Navigation.findNavController(view).navigate(action);


            }

            @Override
            public void onLongItemClick(View view, int position)
            {

            }


        }));


    }
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page_deck_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.deck_detail_page_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.deck_detail_menu_edit_cards:
                editDeckCards();
                return true;
            case R.id.deck_detail_menu_edit_art:
                return true;
            case R.id.deck_detail_menu_edit_name:
                return true;
            case R.id.deck_detail_menu_delete:
                deleteDeck();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void getCardsInDeck()
    {

        Call<List<deck_card>> call = apiInterface.getCardsInDeck(1,data.deckID);
        call.enqueue(new Callback<List<deck_card>>()
        {
            @Override
            public void onResponse(Call<List<deck_card>> call, Response<List<deck_card>> response)
            {
                apiResults = response.body();
                for (int i = 0; i < apiResults.size(); i++)
                {
                    if(getContext() != null)
                    {
                        deck_card_list.add(new collection_page_data(apiResults.get(i).name, getID(apiResults.get(i).cardArt, "mipmap"), getID(apiResults.get(i).rarity, "string"), getID(apiResults.get(i).rarity, "color"), apiResults.get(i).ID));
                    }
                }
                if(getContext() != null)
                {
                    updateRecycleView();
                }

            }

            @Override
            public void onFailure(Call<List<deck_card>> call, Throwable t)
            {
                t.getCause();
            }
        });

    }


    public void getCollectionCards(deck_card card)
    {

       apiResultsCollection.text = card.text;
       apiResultsCollection.cardArt = card.cardArt;
       apiResultsCollection.count = card.count;
       apiResultsCollection.ID = card.ID;
       apiResultsCollection.manaCost = card.manaCost;
       apiResultsCollection.name = card.name;
       apiResultsCollection.power = card.power;
       apiResultsCollection.rarity = card.rarity;
       apiResultsCollection.toughness = card.toughness;
       apiResultsCollection.type = card.type;
       apiResultsCollection.userID = card.userID;

    }

    private void updateRecycleView()
    {
        adapter.notifyDataSetChanged();
    }

    public void addItemToRecycleView(collection_page_data card)
    {
        deck_card_list.add(card);
        updateRecycleView();
    }

    private int getID(String id, String defType)
    {
        return getResources().getIdentifier(id, defType, getActivity().getPackageName());
    }

    private void deleteDeck()
    {
        Call<ApiMessage> call = apiInterface.deleteUserDeck(1,data.deckID);
        call.enqueue(new Callback<ApiMessage>()
        {
            @Override
            public void onResponse(Call<ApiMessage> call, Response<ApiMessage> response)
            {


                for(int i = 0; i < MainActivity.deck_data.size(); i++)
                {
                    if(MainActivity.deck_data.get(i).getDeckID() == data.deckID)
                    {
                        MainActivity.deck_data.remove(i);
                        break;
                    }
                }
                deck_page.updateRecycleView();
                Navigation.findNavController(getView()).navigate(deck_detail_pageDirections.popToDeckPage2());
            }

            @Override
            public void onFailure(Call<ApiMessage> call, Throwable t)
            {
                t.getCause();
            }
        });
    }

    private void editDeckCards()
    {
        deck_detail_pageDirections.DeckDetailToEditDeck action =  deck_detail_pageDirections.deckDetailToEditDeck(getData(), apiResults.get(0).deckID);
        Navigation.findNavController(getView()).navigate(action);
    }

    public edit_cards_in_deck_data[] getData()
    {
        ArrayList<edit_cards_in_deck_data> data = new ArrayList<>();

        for(int i = 0, k = 0; i < MainActivity.collection_data.size(); i++)
        {

                if (apiResults.get(k).ID == MainActivity.collection_data.get(i).getCardID())
                {

                    data.add(new edit_cards_in_deck_data(apiResults.get(k).count, getCardCount(apiResults.get(k).ID), deck_card_list.get(k).imageID, apiResults.get(k).deckID, apiResults.get(k).ID, apiResults.get(k).name));
                    if(k+1 < apiResults.size())
                    {
                        k++;
                    }
                }

            else{
                data.add(new edit_cards_in_deck_data(0, MainActivity.collectionApiResults.get(i).count, MainActivity.collection_data.get(i).imageID, -1,MainActivity.collectionApiResults.get(i).ID,MainActivity.collection_data.get(i).cardName));
            }
        }




        return data.toArray(new edit_cards_in_deck_data[0]);
    }

    public Integer getCardCount(int cardID)
    {
        int cardCount = 0;
        for(int i = 0; i < MainActivity.collectionApiResults.size(); i++)
        {
            if(MainActivity.collectionApiResults.get(i).ID == cardID)
            {
                cardCount = MainActivity.collectionApiResults.get(i).count;
            }
        }
        return cardCount;
    }

}
