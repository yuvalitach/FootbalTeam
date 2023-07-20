package com.example.footbalteam;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FootbalService {
    private RetrofitService retrofitService = new RetrofitService();
    private FootbalApi footballApi = retrofitService.getRetrofit().create(FootbalApi.class);

    public interface PlayersCallback {
        void onPlayersReceived(List<Player> players);

        void onFailure(Throwable t);
    }

    public interface TeamsCallback {
        void onPlayersReceived(List<Integer> teams);

        void onFailure(Throwable t);
    }


    public void getAllTeams(final TeamsCallback callback) {
        Call<List<Integer>> allPlayersCall = footballApi.getGroups();
        allPlayersCall.enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                List<Integer> teams = response.body();
                if (teams != null) {
                    callback.onPlayersReceived(teams);
                }
            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

            }
        });
    }

    public void addPlayer(Player player, final PlayersCallback callback) {
        Call<Player> addPlayerCall = footballApi.addPlayer(player);
        addPlayerCall.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                Player player = response.body();
                if (player != null) {
                    List<Player> players = new ArrayList<>();
                    players.add(player);
                    callback.onPlayersReceived(players);
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
            }
        });
    }

    public void deletePlayer(int id, final PlayersCallback callback) {
        Call<Void> deletePlayerCall = footballApi.deletePlayer(id);
        deletePlayerCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                getAllPlayers(callback);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public void updatePlayer(Player player, int id, final PlayersCallback callback) {
        Call<Player> updatePlayerCall = footballApi.updatePlayer(id, player);
        updatePlayerCall.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                Player player = response.body();
                if (player != null) {
                    List<Player> players = new ArrayList<>();
                    players.add(player);
                    callback.onPlayersReceived(players);
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {

            }
        });

    }

    public void getPlayerData(int id, final PlayersCallback callback) {
        Call<Player> getPlayerDataCall = footballApi.getPlayer(id);
        getPlayerDataCall.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                Player player = response.body();
                if (player != null) {
                    List<Player> players = new ArrayList<>();
                    players.add(player);
                    callback.onPlayersReceived(players);
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }


    public void getTeamById(int id, final PlayersCallback callback) {
        Call<List<Player>> getPlayerDataCall = footballApi.getGroupByTeamNum(id);
        getPlayerDataCall.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                List<Player> players = response.body();
                if (players != null) {
                    callback.onPlayersReceived(players);

                }
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
            }
        });
    }

    public void getAllPlayers(final PlayersCallback callback) {
        Call<List<Player>> allPlayersCall = footballApi.GetPlayers();
        allPlayersCall.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                List<Player> players = response.body();
                if (players != null) {
                    callback.onPlayersReceived(players);
                }
            }
            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}

