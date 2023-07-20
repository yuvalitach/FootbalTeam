package com.example.footbalteam;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class ShowGroupsActivity extends AppCompatActivity {

    private StringBuilder teamString = new StringBuilder();
    ;
    private MaterialTextView team;
    private Spinner spinner;
    private MaterialButton get_button;
    private ArrayList<String> teamPlayers = new ArrayList<>();
    private String selectedOption = "";
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_groups);
        findViews();
        getGroups();
    }

    private void getGroups() {
        //Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamPlayers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        FootbalService playerService = new FootbalService();
        playerService.getAllTeams(new FootbalService.TeamsCallback() {

            @Override
            public void onPlayersReceived(List<Integer> teams) {
                for (int i = 0; i < teams.size(); i++) {
                    adapter.add(String.valueOf(teams.get(i)));
                }
                adapter.notifyDataSetChanged();
            }

                @Override
            public void onFailure(Throwable t) {

            }
        });


        get_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedOption = spinner.getSelectedItem().toString();
                playerService.getTeamById(Integer.parseInt(selectedOption), new FootbalService.PlayersCallback() {
                    @Override
                    public void onPlayersReceived(List<Player> players) {
                        for (Player player : players) {
                            teamString.append(player.toString()+"\n");
                        }
                        team.setText(teamString);
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
        team = findViewById(R.id.Team);
        get_button = findViewById(R.id.get_button);

    }
}