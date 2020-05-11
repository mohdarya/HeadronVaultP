package com.example.headronvault.Pages;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.headronvault.API.APIClient;
import com.example.headronvault.API.APIInterface;
import com.example.headronvault.API.Deck;
import com.example.headronvault.API.collection_card;
import com.example.headronvault.API.deck_card;
import com.example.headronvault.Pages.camera_page;
import com.example.headronvault.R;
import com.example.headronvault.camera_pageDirections;
import com.example.headronvault.data.collection_page_data;
import com.example.headronvault.data.deck_page_data;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private static APIInterface apiInterface;
    protected static ArrayList<collection_page_data> collection_data = new ArrayList<>();
    protected  static    ArrayList<deck_page_data> deck_data = new ArrayList<>();
    protected static List<deck_card> DeckApiResults;
    protected static ArrayList<collection_card> collectionApiResults = new ArrayList<>() ;
    {
    };
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sync();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.wishlist_page, R.id.database_page,
                R.id.account_page)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){
            finish();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private static void getUserDecks()
    {
        Call<List<Deck>> call = apiInterface.getUserDecks(1);
        call.enqueue(new Callback<List<Deck>>()
        {
            @Override
            public void onResponse(Call<List<Deck>> call, Response<List<Deck>> response)
            {
                List<Deck> userDecks = response.body();
                for (int i = 0; i < userDecks.size(); i++)
                {

                    deck_data.add(new deck_page_data(userDecks.get(i).name, getID(userDecks.get(i).coverArt,"mipmap"), userDecks.get(i).ID, userDecks.get(i).deckColor));
                }

            }

            @Override
            public void onFailure(Call<List<Deck>> call, Throwable t)
            {
                t.getCause();
            }
        });
    }
    private static void getUserCollection()
    {
        Call<ArrayList<collection_card>> call = apiInterface.getCardsInCollection(1);
        call.enqueue(new Callback<ArrayList<collection_card>>()
        {
            @Override
            public void onResponse(Call<ArrayList<collection_card>> call, Response<ArrayList<collection_card>> response)
            {
                collectionApiResults = response.body();
                for (int i = 0; i < collectionApiResults.size(); i++)
                {
                    collection_data.add(new collection_page_data(collectionApiResults.get(i).name, getID(collectionApiResults.get(i).cardArt, "mipmap"), getID(collectionApiResults.get(i).rarity, "string"), getID(collectionApiResults.get(i).rarity, "color"), collectionApiResults.get(i).ID));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<collection_card>> call, Throwable t)
            {
                t.getCause();
            }
        });

    }
    public static void sync()
    {
        collectionApiResults = new ArrayList<>();
        collection_data = new ArrayList<>();
        deck_data= new ArrayList<>();
        getUserCollection();
        getUserDecks();
    }



    public static int getID(String id, String defType)
    {
        return mContext.getResources().getIdentifier(id, defType,  mContext.getPackageName());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case camera_page.CAMERA_CODE:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else
                {
                    Navigation.findNavController(camera_page.view).navigate(camera_pageDirections.cameraPopToCollection());
                }
                return;
            }

        }
    }


}

