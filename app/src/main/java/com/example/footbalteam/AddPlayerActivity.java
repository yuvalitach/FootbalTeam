package com.example.footbalteam;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddPlayerActivity extends AppCompatActivity {

    private TextInputEditText main_EDT_name, main_EDT_Id, main_EDT_rate, main_EDT_team, main_EDT_position;
    private ExtendedFloatingActionButton main_BTN_addPlayer;
    private View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        findViews();
        init();
    }

    private void findViews() {
        main_EDT_name = findViewById(R.id.main_EDT_name);
        main_EDT_Id = findViewById(R.id.main_EDT_Id);
        main_EDT_rate = findViewById(R.id.main_EDT_rate);
        main_EDT_team = findViewById(R.id.main_EDT_team);
        main_EDT_position = findViewById(R.id.main_EDT_position);
        main_BTN_addPlayer = findViewById(R.id.main_BTN_addPlayer);
        view = findViewById(R.id.all);
    }

    private void init() {
        main_BTN_addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Player player = new Player(Integer.parseInt(main_EDT_Id.getText().toString()),
                        main_EDT_name.getText().toString(),
                        Integer.parseInt(main_EDT_rate.getText().toString()),
                        Integer.parseInt(main_EDT_team.getText().toString()),
                        main_EDT_position.getText().toString());

                FootbalService footbalService = new FootbalService();
                footbalService.addPlayer(player, new FootbalService.PlayersCallback() {
                    @Override
                    public void onPlayersReceived(List<Player> players) {
                        for (Player myPlayer : players) {
                            if (myPlayer.getId()==Integer.parseInt(main_EDT_Id.getText().toString())) {
                                onAddShowPopupWindowClick(view);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(AddPlayerActivity.this, "הוספת השחקן נכשלה!", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
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
            }
        });
    }

}