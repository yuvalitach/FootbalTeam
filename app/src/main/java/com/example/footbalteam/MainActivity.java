package com.example.footbalteam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExtendedFloatingActionButton main_BTN_allGroups, main_BTN_addPlayer,main_DeletePlayer, main_BTN_UpdatePlayer,main_BTN_ShowGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initButtons();
        init();
    }

    private void initButtons() {

        main_DeletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeletePlayerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        main_BTN_addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPlayerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        main_BTN_ShowGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowGroupsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        main_BTN_UpdatePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdatePlayerActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void findViews() {
        main_BTN_addPlayer = findViewById(R.id.main_BTN_addPlayer);
        main_DeletePlayer = findViewById(R.id.main_DeletePlayer);
        main_BTN_UpdatePlayer = findViewById(R.id.main_BTN_UpdatePlayer);
        main_BTN_ShowGroups = findViewById(R.id.main_BTN_ShowGroups);
    }

    private void init() {
        ArrayList<String> list = new ArrayList<>();
        FootbalService footbalService = new FootbalService();
        footbalService.getAllPlayers(new FootbalService.PlayersCallback() {
            @Override
            public void onPlayersReceived(List<Player> playersResponse) {
                for (int i = 0; i < playersResponse.size(); i++) {
                    list.add(playersResponse.get(i).getName());
                    Log.d("pttt", playersResponse.get(i).getName());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // Handle the failure case
                // Display an error message or perform any other necessary operations
                Log.e("pttt", t.getMessage());
            }
        });

    }
}