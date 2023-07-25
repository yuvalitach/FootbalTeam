package com.example.footbalteam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class UpdatePlayerActivity extends AppCompatActivity {

    private Spinner spinner;
    private TextInputEditText main_EDT_name, main_EDT_Id, main_EDT_rate, main_EDT_team, main_EDT_position;
    private MaterialButton main_BTN_updatePlayer, main_BTN_upsateAndChoosePlayer;
    private ArrayList<String> playersNames = new ArrayList<>();
    private int selectedOption;
    private View view;
    private Player selectedPlayer = new Player();
    private int existId = -1;
    private ImageButton backBUtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_player);
        findViews();
        updatePlayer();
    }

    private void updatePlayer() {
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


        main_BTN_upsateAndChoosePlayer.setOnClickListener(new View.OnClickListener() {
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
                                existId = player.getId();
                                main_EDT_name.setText(player.getName());
                                main_EDT_rate.setText(String.valueOf(player.getRate()));
                                main_EDT_Id.setText(String.valueOf(player.getId()));
                                main_EDT_team.setText(String.valueOf(player.getTeam()));
                                main_EDT_position.setText(player.getPosition());

                            }
                        }
                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

            }
        });

        main_BTN_updatePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedPlayer.setName(main_EDT_name.getText().toString());
                selectedPlayer.setId(Integer.parseInt(main_EDT_Id.getText().toString()));
                selectedPlayer.setRate(Integer.parseInt(main_EDT_rate.getText().toString()));
                selectedPlayer.setPosition(main_EDT_position.getText().toString());
                selectedPlayer.setTeam(Integer.parseInt(main_EDT_team.getText().toString()));


                footbalService.updatePlayer(selectedPlayer, existId,
                        new FootbalService.PlayersCallback() {
                            @Override
                            public void onPlayersReceived(List<Player> players) {
                                onAddShowPopupWindowClick(view);
                                adapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
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
        main_EDT_name = findViewById(R.id.main_EDT_name);
        main_EDT_Id = findViewById(R.id.main_EDT_Id);
        main_EDT_rate = findViewById(R.id.main_EDT_rate);
        main_EDT_team = findViewById(R.id.main_EDT_team);
        main_EDT_position = findViewById(R.id.main_EDT_position);
        main_BTN_upsateAndChoosePlayer = findViewById(R.id.main_BTN_upsateAndChoosePlayer);
        main_BTN_updatePlayer = findViewById(R.id.main_BTN_updatePlayer);
        view = findViewById(R.id.all);
        backBUtton = findViewById(R.id.backButton);
    }

    private void onAddShowPopupWindowClick(View view) {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);


        boolean focusable = true;
        PopupWindow popupWindow = new PopupWindow(popupView, 980, 500, focusable);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        ExtendedFloatingActionButton dialog_save_game = popupView.findViewById(R.id.dialog_save_game);
        dialog_save_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}