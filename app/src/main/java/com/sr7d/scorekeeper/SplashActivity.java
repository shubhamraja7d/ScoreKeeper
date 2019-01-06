package com.sr7d.scorekeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_player1,et_player2;
    private String player1,player2;
    private Button btnPlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bindViews();
        readPlayerNames();
    }

    private void readPlayerNames() {
        player1 = et_player1.getText().toString();
        player2 = et_player2.getText().toString();
    }

    private void bindViews() {
        et_player1 = findViewById(R.id.editText_player1);
        et_player2 = findViewById(R.id.editText_player2);
        btnPlay = findViewById(R.id.button_play);
        btnPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_play) {
            readPlayerNames();
            if (player1.isEmpty() || player2.isEmpty()) {
                Toast.makeText(this, "Player name can not be empty", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(this,MainActivity.class);
                i.putExtra("player1",player1);
                i.putExtra("player2",player2);
                startActivity(i);
            }

        }
    }
}
