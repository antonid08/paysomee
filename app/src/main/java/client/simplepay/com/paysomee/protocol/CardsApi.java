package client.simplepay.com.paysomee.protocol;

import java.util.List;

import client.simplepay.com.paysomee.protocol.models.CardMto;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CardsApi {

    @GET("/bank/getcards")
    Call<List<CardMto>> getCards();
}
