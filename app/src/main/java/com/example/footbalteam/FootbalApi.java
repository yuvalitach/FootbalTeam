package com.example.footbalteam;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FootbalApi {
    @GET("/api/Players")
    Call<List<Player>> GetPlayers();

    @POST("api/Players")
    Call<Player> addPlayer(@Body Player player);

    @DELETE("api/Players/{id}")
    Call<Void> deletePlayer(@Path("id") int id);

    @PUT("api/Players/{id}")
    Call<Player> updatePlayer(@Path("id") int id, @Body Player player);

    @GET("api/Players/{id}")
    Call<Player> getPlayer(@Path("id") int id);

    @GET("api/Players/teams")
    Call<List<Integer>> getGroups();

    @GET("api/Players/group/{groupId}")
    Call<List<Player>> getGroupByTeamNum(@Path("groupId") int team);

}
