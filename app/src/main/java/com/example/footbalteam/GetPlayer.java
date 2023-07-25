package com.example.footbalteam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class GetPlayer extends AppCompatActivity {

    ;
    private MaterialTextView playerData;
    private Spinner spinner;
    private MaterialButton get_button;
    private ArrayList<String> playersNames = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private int selectedOption;
    private View view;

    private ImageButton backBUtton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_player);
        findViews();
        getPlayer();
    }

    private void getPlayer() {
        backBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, playersNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        FootbalService footbalService = new FootbalService();
        footbalService.getAllPlayers(new FootbalService.PlayersCallback() {
            @Override
            public void onPlayersReceived(List<Player> playerResponse) {
                for (int i = 0; i < playerResponse.size(); i++) {
                    adapter.add("שם השחקן: "+playerResponse.get(i).getName()+", תז: "+(playerResponse.get(i).getId()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle the failure case
                // Display an error message or perform any other necessary operations
                Log.e("pttt", "Failed to retrieve players: " + t.getMessage());
            }
        });



        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] parts = spinner.getSelectedItem().toString().split("תז: ");
                String idSelected = parts[1];
                selectedOption = Integer.parseInt(idSelected);
                footbalService.getPlayerData(selectedOption, new FootbalService.PlayersCallback() {
                    @Override
                    public void onPlayersReceived(List<Player> players) {
                        for (Player player : players) {
                            if (player.getId()==(selectedOption)) {
                                playerData.setText(player.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });


    }

    private void findViews() {
        spinner = findViewById(R.id.spinner);
        view = findViewById(R.id.all);
        playerData = findViewById(R.id.Team);
        get_button = findViewById(R.id.get_button);
        backBUtton = findViewById(R.id.backButton);
    }
}