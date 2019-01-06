package com.sr7d.scorekeeper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_pl1_correct, btn_pl1_wrong, btn_pl2_correct, btn_pl2_wrong;
    private TextView tv_player1_score, tv_player2_score, tv_player1_name, tv_player2_name;
    int player1_score, player2_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bindViews();
        initScore();
    }

    private void handleIntent() {
        Intent i = getIntent();
        tv_player1_name.setText(i.getStringExtra("player1"));
        tv_player2_name.setText(i.getStringExtra("player2"));
    }

    private void bindViews() {
        btn_pl1_correct = findViewById(R.id.btn_player1_correct);
        btn_pl1_wrong = findViewById(R.id.btn_player1_wrong);
        btn_pl2_correct = findViewById(R.id.btn_player2_correct);
        btn_pl2_wrong = findViewById(R.id.btn_player2_wrong);

        tv_player1_score = findViewById(R.id.text_view_score_player1);
        tv_player2_score = findViewById(R.id.text_view_score_player2);

        tv_player1_name = findViewById(R.id.text_view_player1);
        tv_player2_name = findViewById(R.id.text_view_player2);

        btn_pl1_correct.setOnClickListener(this);
        btn_pl1_wrong.setOnClickListener(this);
        btn_pl2_correct.setOnClickListener(this);
        btn_pl2_wrong.setOnClickListener(this);

        handleIntent();
        addTextChangeListener();
    }

    private void addTextChangeListener() {
        tv_player1_score.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tv_player2_score.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                changeColor();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void incrementScore(String player) {
        if (player.equals("player1")) {
            player1_score = getScreenScore("player1") + 1;
            updateScore(player, player1_score);
        } else if (player.equals("player2")) {
            player2_score = getScreenScore("player2") + 1;
            updateScore(player, player2_score);
        }
    }

    private void decrementScore(String player) {
        if (player.equals("player1")) {
            player1_score = getScreenScore("player1") - 1;
            updateScore(player, player1_score);
        } else if (player.equals("player2")) {
            player2_score = getScreenScore("player2") - 1;
            updateScore(player, player1_score);
        }
    }

    private int getScreenScore(String player) {
        switch (player) {
            case "player1":
                return Integer.parseInt(tv_player1_score.getText().toString());
            case "player2":
                return Integer.parseInt(tv_player2_score.getText().toString());
            default:
                return -1;
        }
    }

    private void updateScore(String player, int score) {
        SharedPreferences sharedpreferences = getSharedPreferences("ScoreKeeper", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(player + "_score", score);
        editor.apply();
        updateScreenScore();
    }

    private void initScore() {
        SharedPreferences sharedpreferences = getSharedPreferences("ScoreKeeper", Context.MODE_PRIVATE);
        player1_score = sharedpreferences.getInt("player1_score", 0);
        player2_score = sharedpreferences.getInt("player2_score", 0);

        updateScreenScore();
    }

    private void updateScreenScore() {
        tv_player1_score.setText(String.valueOf(player1_score));
        tv_player2_score.setText(String.valueOf(player2_score));
    }

    private void changeColor() {
        if (player1_score > player2_score) {
            tv_player1_score.setTextColor(Color.parseColor("#7FFF00"));
//            tv_player2_score.setBackgroundColor(Color.rgb(55,123,90));
            tv_player2_score.setTextColor(Color.parseColor("#B22222"));
        } else if (player2_score > player1_score) {
            tv_player1_score.setTextColor(Color.parseColor("#B22222"));
            tv_player2_score.setTextColor(Color.parseColor("#7FFF00"));
        } else {
            tv_player1_score.setTextColor(Color.parseColor("#000000"));
            tv_player2_score.setTextColor(Color.parseColor("#000000"));
        }
    }

    private void reset_game() {
        player1_score = 0;
        player2_score = 0;
        updateScore("player1", player1_score);
        updateScore("player2", player2_score);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_player1_correct:
                incrementScore("player1");
                break;
            case R.id.btn_player1_wrong:
                decrementScore("player1");
                break;
            case R.id.btn_player2_correct:
                incrementScore("player2");
                break;
            case R.id.btn_player2_wrong:
                decrementScore("player2");
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game_menu:
                startActivity(new Intent(this,SplashActivity.class));
                return true;
            case R.id.reset_game_menu:
                reset_game();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
