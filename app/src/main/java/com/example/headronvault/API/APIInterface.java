package com.example.headronvault.API;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface APIInterface
{


    @GET("Cards/{card_name}/CardID")
    Call<List<card_id_fetch_data>> searchForCardID(@Path("card_name") String cardName);

    @GET("Users/{user_id}/Decks")
    Call<List<Deck>> getUserDecks(@Path("user_id") Integer userID);

    @GET("Users/{user_id}/Decks/Cards/{card_id}")
    Call<List<Deck>> getUserDecksWithCard(@Path("user_id") Integer userID, @Path("card_id") Integer card_id);

    @DELETE("/Users/{user_id}/Decks/{deck_id}")
    Call<ApiMessage> deleteUserDeck(@Path("user_id") Integer userID, @Path("deck_id") Integer deckID);

    @GET("/Users/{user_id}/Decks/{deck_id}/Cards")
    Call<List<deck_card>> getCardsInDeck(@Path("user_id") Integer userID, @Path("deck_id") Integer deckID);

    @POST("/Users/{user_id}/Decks")
    Call<response_deck_creation> createDeck(@Path("user_id") Integer userid, @Query("DeckName") String deck_name, @Query("DeckCoverArt") String deck_cover_art, @Query("DeckColor") String deck_color);

    @DELETE("/Users/{user_id}/Decks/{deck_id}/{card_id}")
    Call<ApiMessage> deleteCardFrmDeck(@Path("user_id") Integer userID, @Path("deck_id") Integer deckID, @Path("card_id") Integer cardID);

    @POST("/Users/{user_id}/Decks/{deck_id}/{card_id}")
    Call<ApiMessage> addCardToDeck(@Path("user_id") Integer userID, @Path("deck_id") Integer deckID, @Path("card_id") Integer cardID, @Query("Count") Integer Count);

    @PATCH("/Users/{user_id}/Decks/{deck_id}/{card_id}")
    Call<ApiMessage> updateCardInDeck(@Path("user_id") Integer userID, @Path("deck_id") Integer deckID, @Path("card_id") Integer cardID, @Query("Count") Integer Count);

    @GET("/Users/{user_id}/Collections/Cards")
    Call<ArrayList<collection_card>> getCardsInCollection(@Path("user_id") Integer userID);

    @POST("/Users/{user_id}/Collections/{Card_id}")
    Call<ApiMessage> addCardToCollection(@Path("user_id") Integer userID, @Path("Card_id") Integer cardID, @Query("Count") Integer Count);

    @Multipart
    @POST("/Cards/Image")
    Call<image_message> useImageRecognition(@Part MultipartBody.Part image);


}

