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

import java.util.ArrayList;
import java.util.List;

public class DeletePlayerActivity extends AppCompatActivity {

    private Spinner spinner;
    private MaterialButton delete_button;

    private ArrayList<String> playersNames = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private int selectedOption;
    private View view;
    private ImageButton backBUtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_player);
        findViews();
        init();
    }
    private void init() {
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

        FootbalService footballService = new FootbalService();
        footballService.getAllPlayers(new FootbalService.PlayersCallback() {
            @Override
            public void onPlayersReceived(List<Player> playersResponse) {
                for (int i = 0; i < playersResponse.size(); i++) {
                    players.add(playersResponse.get(i));
                    adapter.add("שם השחקן: "+playersResponse.get(i).getName()+", תז: "+(playersResponse.get(i).getId()));
                    Log.d("pttt", playersResponse.get(i).getName());
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


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=0;
                String[] parts = spinner.getSelectedItem().toString().split("תז: ");
                String idSelected = parts[1];
                selectedOption = Integer.parseInt(idSelected);
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getId()==(selectedOption)) {
                        id = players.get(i).getId();
                    }
                }

                int finalId = id;
                footballService.deletePlayer(id, new FootbalService.PlayersCallback() {
                    @Override
                    public void onPlayersReceived(List<Player> players) {
                        onAddShowPopupWindowClick(view);
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
        delete_button = findViewById(R.id.main_BTN_deletePlayer);
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