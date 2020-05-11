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

import com.example.headronvault.API.collection_card_parce;
import com.example.headronvault.API.collection_page_data_parce;
import com.example.headronvault.R;
import com.example.headronvault.collection_pageDirections;
import com.example.headronvault.adapters.collection_recycle_view_adapter;
import com.example.headronvault.adapters.recycle_view_click_listener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link collection_page.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link collection_page#newInstance} factory method to
 * create an instance of this fragment.
 */
public class collection_page extends Fragment
{

    private collection_recycle_view_adapter adapter;


    MainActivity main = (MainActivity) getActivity();
    private OnFragmentInteractionListener mListener;


    public collection_page()
    {
        // Required empty public constructor
    }


    public static collection_page newInstance(String param1, String param2)
    {
        collection_page fragment = new collection_page();
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {


        final RecyclerView collectionrv = (RecyclerView) view.findViewById(R.id.collection_page_recyclerView);

        adapter = new collection_recycle_view_adapter( MainActivity.collection_data);
        collectionrv.setAdapter(adapter);
        collectionrv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        collectionrv.addOnItemTouchListener(new recycle_view_click_listener(getContext(), collectionrv, new recycle_view_click_listener.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, int position)
            {
                 collection_pageDirections.CollectionPageToCardDetailPage action = collection_pageDirections.collectionPageToCardDetailPage( new collection_card_parce(MainActivity.collectionApiResults.get(position)),new collection_page_data_parce(MainActivity.collection_data.get(position)));
                  Navigation.findNavController(view).navigate(action);


            }

            @Override
            public void onLongItemClick(View view, int position)
            {


            }
        }));


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
        return inflater.inflate(R.layout.page_collection_main, container, false);
    }

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


    public interface OnFragmentInteractionListener
    {
        void onFragmentInteraction(Uri uri);
    }


    public void updateRecycleView()
    {
        adapter.notifyDataSetChanged();
    }


    private int getID(String id, String defType)
    {
        return getResources().getIdentifier(id, defType, getContext().getPackageName());
    }


}
