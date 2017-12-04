package client.simplepay.com.paysomee.protocol;

import java.util.List;

import client.simplepay.com.paysomee.protocol.models.AddCardRequestBody;
import client.simplepay.com.paysomee.protocol.models.CardMto;
import client.simplepay.com.paysomee.protocol.models.ConfirmCardRequestBody;
import client.simplepay.com.paysomee.protocol.models.RefreshTokensRequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CardsApi {

    @GET("getcards")
    Call<List<CardMto>> getCards(@Query("DeviceHash") String deviceId);

    @POST("addcard")
    Call<Void> addCard(@Body AddCardRequestBody body);

    @POST("ConfirmCardBy")
    Call<Void> confirmCard(@Body ConfirmCardRequestBody body);

    @POST("RefreshTokenSet")
    Call<List<String>> refreshTokens(@Body RefreshTokensRequestBody body);

}
