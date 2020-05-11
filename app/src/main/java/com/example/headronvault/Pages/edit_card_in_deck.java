package com.example.headronvault.Pages;

import android.content.Context;
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

import com.example.headronvault.API.APIClient;
import com.example.headronvault.API.APIInterface;
import com.example.headronvault.API.ApiMessage;
import com.example.headronvault.R;
import com.example.headronvault.edit_card_in_deckArgs;
import com.example.headronvault.edit_card_in_deckDirections;
import com.example.headronvault.data.edit_cards_in_deck_data;
import com.example.headronvault.adapters.make_a_deck_card_recycler_adapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link edit_card_in_deck.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link edit_card_in_deck#newInstance} factory method to
 * create an instance of this fragment.
 */
public class edit_card_in_deck extends Fragment
{

    static int count;
    int deckId;
    RecyclerView cardsRv;
    private APIInterface apiInterface;
    ArrayList<edit_cards_in_deck_data> data;
    private OnFragmentInteractionListener mListener;
    make_a_deck_card_recycler_adapter adapter;
    public edit_card_in_deck()
    {
        // Required empty public constructor
    }

    public static edit_card_in_deck newInstance(String param1, String param2)
    {
        edit_card_in_deck fragment = new edit_card_in_deck();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        data = new ArrayList<>(Arrays.asList(edit_card_in_deckArgs.fromBundle(getArguments()).getCards()));
        deckId = edit_card_in_deckArgs.fromBundle(getArguments()).getDeckId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.page_edit_cards_in_deck, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        buildRecyclerView();
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
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

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

    public void buildRecyclerView()
    {


        cardsRv = (RecyclerView) getActivity().findViewById(R.id.edit_cards_in_deck_recycler);
        adapter = new make_a_deck_card_recycler_adapter();
        adapter.setEditData(data);
        cardsRv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        cardsRv.setAdapter(adapter);
        adapter.setOnItemClickListener(new make_a_deck_card_recycler_adapter.OnItemClickListener()
        {
            @Override
            public void onAddClicked(int position)
            {
                adapter.editData.get(position).addCount();
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onItemClick(int position)
            {

            }

            @Override
            public void onRemovedClicked(int position)
            {
                adapter.editData.get(position).removeCount();
                adapter.notifyItemChanged(position);
            }




        });


        FloatingActionButton makeADeck = getActivity().findViewById(R.id.edit_cards_in_deck_fob);
        makeADeck.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                updateCardsInDatabase();
            }
        });


    }


    private void updateCardsInDatabase()
    {
        count = 0;
        final int editedCount = getSelectedCount();
        for(int i = 0; i < data.size(); i++)
        {

            int test = data.get(i).count;
            Call<ApiMessage> createDeck;
            if(data.get(i).edited = true)
            {
                if(data.get(i).count > 0)
                {
                    if (data.get(i).deckId != -1)
                    {
                        createDeck = apiInterface.updateCardInDeck(1, deckId, data.get(i).cardId, test);
                    } else
                    {
                        createDeck = apiInterface.addCardToDeck(1, deckId, data.get(i).cardId, test);
                    }
                    createDeck.enqueue(new Callback<ApiMessage>()
                    {

                        @Override
                        public void onResponse(Call<ApiMessage> call, Response<ApiMessage> response)
                        {

                            count++;
                            if(count == editedCount)
                            {

                                Navigation.findNavController(getView()).navigate(edit_card_in_deckDirections.editDeckPopToDeckDetail());
                            }

                        }
                        @Override
                        public void onFailure(Call<ApiMessage> call, Throwable t)
                        {

                        }
                    });
                }
                else
                {
                    if (data.get(i).deckId != -1)
                    {
                        createDeck = apiInterface.deleteCardFrmDeck(1, deckId, data.get(i).cardId);
                        createDeck.enqueue(new Callback<ApiMessage>()
                        {

                            @Override
                            public void onResponse(Call<ApiMessage> call, Response<ApiMessage> response)
                            {

                                count++;
                                if(count == editedCount)
                                {

                                    Navigation.findNavController(getView()).navigate(edit_card_in_deckDirections.editDeckPopToDeckDetail());
                                }

                            }
                            @Override
                            public void onFailure(Call<ApiMessage> call, Throwable t)
                            {

                            }
                        });
                    }

                }


            }


        }
        if(count == editedCount)
        {

            Navigation.findNavController(getView()).navigate(edit_card_in_deckDirections.editDeckPopToDeckDetail());
        }
    }

    private int getSelectedCount()
    {
        int count = 0;
        for(int i = 0; i < data.size(); i++)
        {
            if(data.get(i).edited)
            {
                count++;
            }
        }
        return count;

    }

}
