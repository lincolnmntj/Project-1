package com.example.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardActivity extends AppCompatActivity {
    private TextView nicknameTextView;
    private TextView scoreTextView;
    private TextView answersSummaryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        // Initialize views
        nicknameTextView = findViewById(R.id.nicknameTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        answersSummaryTextView = findViewById(R.id.answersSummaryTextView);
        Button restartButton = findViewById(R.id.restartGameButton);

        // Retrieve data passed from GameActivity
        String playerNickname = getIntent().getStringExtra("player_nickname");
        int playerScore = getIntent().getIntExtra("player_score", 0);
        ArrayList<Question> questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        ArrayList<String> playerAnswers = getIntent().getStringArrayListExtra("player_answers");

        // Display the nickname and score
        nicknameTextView.setText("Player: " + playerNickname);
        scoreTextView.setText("Score: " + playerScore + " / " + questions.size());

        // Display the summary of questions, player answers, and correct answers
        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String playerAnswer = playerAnswers.get(i);
            String correctAnswer = question.getCorrectAnswer();
            summary.append("Q").append(i + 1).append(": ").append(question.getQuestionText()).append("\n");
            summary.append("Your Answer: ").append(playerAnswer).append("\n");
            summary.append("Correct Answer: ").append(correctAnswer).append("\n\n");
        }
        answersSummaryTextView.setText(summary.toString());

        restartButton.setOnClickListener(v -> {
            // Shuffle questions
            Collections.shuffle(questions);

            // Restart the game by sending the player back to the PlayerInputActivity (or MainActivity)
            Intent restartIntent = new Intent(ScoreboardActivity.this, MainActivity.class);
            restartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all activities above PlayerInputActivity
            startActivity(restartIntent);
            finish(); // Optionally finish the ScoreboardActivity so it doesn't stay in the stack
        });
    }
}
