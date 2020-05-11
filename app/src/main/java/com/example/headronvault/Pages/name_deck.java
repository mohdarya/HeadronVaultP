package com.example.headronvault.Pages;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.headronvault.API.APIClient;
import com.example.headronvault.API.APIInterface;
import com.example.headronvault.API.ApiMessage;
import com.example.headronvault.API.collection_page_data_parce;
import com.example.headronvault.API.response_deck_creation;
import com.example.headronvault.R;
import com.example.headronvault.data.deck_page_data;
import com.example.headronvault.name_deckArgs;
import com.example.headronvault.name_deckDirections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link name_deck.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link name_deck#newInstance} factory method to
 * create an instance of this fragment.
 */
public class name_deck extends Fragment
{
    String deckColor;
    String deckArtName;
    Map<String,String> deckArtAssetName = new HashMap<String, String>();
    ArrayList<collection_page_data_parce> selectedCards;
    private APIInterface apiInterface;
    private OnFragmentInteractionListener mListener;
    static int count =0;
    public name_deck()
    {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment name_deck.
     */
    // TODO: Rename and change types and number of parameters
    public static name_deck newInstance(String param1, String param2)
    {
        name_deck fragment = new name_deck();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        deckColor ="Black,Blue";
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        getDeckNameAssetName();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.page_make_deck_naming, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        selectedCards = new ArrayList<>(Arrays.asList(name_deckArgs.fromBundle(getArguments()).getSelectedCards()));

        deckArtName = name_deckArgs.fromBundle(getArguments()).getDeckArt();
        TextView deckArtNameView = view.findViewById(R.id.deck_naming_deck_art_name);
        deckArtNameView.setText(deckArtName);
        deckArtNameView.setTextSize(24);
        deckArtNameView.setTextColor(getActivity().getColor(R.color.black));

        Button doneButton = view.findViewById(R.id.deck_naming_submit_button);
        doneButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {

                EditText deckNameField = getActivity().findViewById(R.id.deck_naming_deck_name);
                createDeck(deckNameField.getText().toString());
            }
        });

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

    private void createDeck(final String deckName)
    {
        Call<response_deck_creation> createDeck = apiInterface.createDeck(1,deckName,deckArtAssetName.get(deckArtName),deckColor);
        createDeck.enqueue(new Callback<response_deck_creation>()
        {
            @Override
            public void onResponse(Call<response_deck_creation> call, Response<response_deck_creation> response)
            {

                response_deck_creation results = response.body();
                if(getContext() != null)
                {
                    MainActivity.deck_data.add(new deck_page_data(deckName, getID(deckArtAssetName.get(deckArtName), "mipmap"), Integer.valueOf(results.deckID), deckColor));
                    postDeckToDatabase(results.deckID);
                }

            }

            @Override
            public void onFailure(Call<response_deck_creation> call, Throwable t)
            {
                String error = t.getMessage();
            }
        });

    }

    private void getDeckNameAssetName()
    {
        deckArtAssetName.put("Baige", "deck_box_baige");
        deckArtAssetName.put("Black", "deck_box_black");
        deckArtAssetName.put("Brown", "deck_box_brown");
        deckArtAssetName.put("Dark Blue", "deck_box_darkblue");
        deckArtAssetName.put("Green", "deck_box_green");
        deckArtAssetName.put("Grey","deck_box_grey");
        deckArtAssetName.put("Light Blue", "deck_box_lightblue");
        deckArtAssetName.put("Purple", "deck_box_purple");
        deckArtAssetName.put("Red", "deck_box_red");
        deckArtAssetName.put("Yellow","deck_box_yellow");
    }

    private void postDeckToDatabase(String deckID)
    {


        count = 0;

        for(int i = 0; i < selectedCards.size(); i++)
        {
            int test = selectedCards.get(i).getCount();
            Call<ApiMessage> createDeck = apiInterface.addCardToDeck(1,Integer.valueOf(deckID),selectedCards.get(i).cardID,test);
            createDeck.enqueue(new Callback<ApiMessage>()
            {

                @Override
                public void onResponse(Call<ApiMessage> call, Response<ApiMessage> response)
                {

                    count++;
                    if(count == selectedCards.size())
                    {
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                        Navigation.findNavController(getView()).navigate(name_deckDirections.popToDeckPage());
                    }

                }
                @Override
                public void onFailure(Call<ApiMessage> call, Throwable t)
                {

                }
            });
        }


    }
    public int getID(String id, String defType)
    {
        return getResources().getIdentifier(id, defType, getActivity().getPackageName());
    }

}
