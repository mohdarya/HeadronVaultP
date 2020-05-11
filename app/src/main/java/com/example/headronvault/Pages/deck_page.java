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

import com.example.headronvault.API.deck_page_data_parce;
import com.example.headronvault.R;
import com.example.headronvault.data.deck_page_data;
import com.example.headronvault.deck_pageDirections;
import com.example.headronvault.adapters.deck_recycle_view_adapter;
import com.example.headronvault.adapters.recycle_view_click_listener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link deck_page.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link deck_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class deck_page extends Fragment
{

    private OnFragmentInteractionListener mListener;

    private deck_recycle_view_adapter adapter;

    static deck_recycle_view_adapter copyOfAdapter;

    MainActivity main = (MainActivity) getActivity();
    boolean fetched = false;
    public deck_page()
    {
        // Required empty public constructor
    }


    public static deck_page newInstance(String param1, String param2)
    {
        deck_page fragment = new deck_page();

        return fragment;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState)
    {


        RecyclerView deckrv = (RecyclerView) view.findViewById(R.id.deck_page_recyclerView);

        adapter = new deck_recycle_view_adapter(MainActivity.deck_data);
        deckrv.setAdapter(adapter);
        setCopyOfFragment();
        deckrv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        deckrv.addOnItemTouchListener(new recycle_view_click_listener(getContext(), deckrv, new recycle_view_click_listener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                deck_pageDirections.DeckPageToDeckDetailPage action =   deck_pageDirections.deckPageToDeckDetailPage(new deck_page_data_parce(MainActivity.deck_data.get(position)));
                Navigation.findNavController(view).navigate(action);


            }

            @Override
            public void onLongItemClick(View view, int position)
            {

            }
        }));

        FloatingActionButton makeADeck = view.findViewById(R.id.deck_page_make_a_deck);
        makeADeck.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v)
            {
                    Navigation.findNavController(view).navigate(deck_pageDirections.deckPageToMakeADeck());
            }
        });
        //adapter.notifyItemChanged(adapter.getItemCount());

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.page_deck_main, container, false);
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


    static void updateRecycleView()
    {
        copyOfAdapter.notifyDataSetChanged();
    }

    public  void addItemToRecycleView(deck_page_data deck)
    {
        MainActivity.deck_data.add(deck);
        updateRecycleView();
    }

    private int getID(String id, String defType)
    {
        return getResources().getIdentifier(id, defType, getContext().getPackageName());
    }

    private void setCopyOfFragment()
    {
        copyOfAdapter = adapter;
    }
}
