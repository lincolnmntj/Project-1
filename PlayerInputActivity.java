package com.example.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlayerInputActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_input);

        EditText nicknameEditText = findViewById(R.id.nicknameInput);
        Button startGameButton = findViewById(R.id.startGameButton);

        startGameButton.setOnClickListener(v -> {
            String playerNickname = nicknameEditText.getText().toString().trim();
            if (playerNickname.isEmpty()) {
                Toast.makeText(PlayerInputActivity.this, "Please enter your nickname", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(PlayerInputActivity.this, GameActivity.class);
                intent.putExtra("player_nickname", playerNickname);
                startActivity(intent);
            }
        });
    }
}
