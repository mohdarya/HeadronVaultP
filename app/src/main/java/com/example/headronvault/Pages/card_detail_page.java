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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.headronvault.API.APIClient;
import com.example.headronvault.API.APIInterface;
import com.example.headronvault.API.Deck;
import com.example.headronvault.API.collection_card_parce;
import com.example.headronvault.API.collection_page_data_parce;
import com.example.headronvault.API.deck_page_data_parce;
import com.example.headronvault.R;
import com.example.headronvault.card_detail_pageArgs;
import com.example.headronvault.card_detail_pageDirections;
import com.example.headronvault.adapters.card_detail_page_recycler_view_adapter;
import com.example.headronvault.data.deck_page_data;
import com.example.headronvault.adapters.recycle_view_click_listener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link card_detail_page.OnFragmentInteractionListener} interface
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class card_detail_page extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private collection_card_parce cardDetail;
    private collection_page_data_parce cardName;
    private static final String cardDetail1Pram = "CardDetail";
    private static final String cardNamePram = "CardName";
    ArrayList<deck_page_data> data = new ArrayList<>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private card_detail_page_recycler_view_adapter adapter;
    private APIInterface apiInterface;

    public card_detail_page()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment card_detail_page.
     */
    // TODO: Rename and change types and number of parameters


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        if (getArguments() != null)
        {

        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {

        int textColor =  getActivity().getColor(R.color.black);
        float textSize = 16;
        cardDetail = card_detail_pageArgs.fromBundle(getArguments()).getCardDetails();
        cardName = card_detail_pageArgs.fromBundle(getArguments()).getCardName();
        getUserDecks();
        ImageView cardImage = view.findViewById(R.id.card_detail_card_image);
        TextView cardCount = view.findViewById(R.id.card_detail_card_count);
        cardCount.setTextColor(textColor);
        TextView cardRarity = view.findViewById(R.id.card_detail_rarity);
        TextView cardNameText = view.findViewById(R.id.card_detail_card_name);
        TextView cardType = view.findViewById(R.id.card_detail_card_type);
        TextView cardSet = view.findViewById(R.id.card_detail_card_set);
        TextView cardText  = view.findViewById(R.id.card_detail_card_text);
        cardImage.setImageResource(cardName.imageID);
        cardCount.setText(Integer.toString(cardDetail.count));
        cardRarity.setText(getActivity().getString(cardName.cardRarityText));
        cardRarity.setTextColor(getActivity().getColor(cardName.cardRarityColor));
        cardNameText.setText(cardDetail.name);
        cardType.setText(cardDetail.type);
        cardSet.setText("demo set");
        if(cardDetail.text != "null")
        {
            cardText.setText(cardDetail.text);
            cardText.setTextSize(textSize);
        }
        else
        {
            cardText.setText("");
        }
        cardNameText.setTextColor(textColor);
        cardType.setTextColor(textColor);
        cardSet.setTextColor(textColor);
        cardText.setTextColor(textColor);
        cardNameText.setTextSize(textSize);
        cardType.setTextSize(textSize);
        cardSet.setTextSize(textSize);
        RecyclerView deckrv = (RecyclerView) view.findViewById(R.id.card_detail_decks);

        data.clear();
        adapter = new card_detail_page_recycler_view_adapter(data);
        deckrv.setAdapter(adapter);
        deckrv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        deckrv.addOnItemTouchListener(new recycle_view_click_listener(getContext(), deckrv, new recycle_view_click_listener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                // navigate to deck detail page
                card_detail_pageDirections.ActionCardDetailPageToDeckDetailPage action = card_detail_pageDirections.actionCardDetailPageToDeckDetailPage(new deck_page_data_parce(data.get(position)));
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.page_card_detail, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri)
    {
        if (mListener != null)
        {
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
    public void onDetach()
    {
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
    public interface OnFragmentInteractionListener
    {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getUserDecks()
    {
        Call<List<Deck>> call = apiInterface.getUserDecksWithCard(1, cardDetail.ID);
        call.enqueue(new Callback<List<Deck>>()
        {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response)
            {
                List<Deck> userDecks = response.body();
                for (int i = 0; i < userDecks.size(); i++)
                {
                    if(getContext() != null)
                    {
                        data.add(new deck_page_data(userDecks.get(i).name, getID(userDecks.get(i).coverArt, "mipmap"), userDecks.get(i).ID, userDecks.get(i).deckColor));
                    }
                }
                if(getContext() != null)
                {
                    updateRecycleView();
                }
            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t)
            {
                t.getCause();
            }
        });



    }
    private  void updateRecycleView()
    {
        adapter.notifyDataSetChanged();
    }

    public  void addItemToRecycleView(deck_page_data deck)
    {
        data.add(deck);
        updateRecycleView();
    }

    private int getID(String id, String defType)
    {
        return getResources().getIdentifier(id, defType, getActivity().getPackageName());
    }


}
