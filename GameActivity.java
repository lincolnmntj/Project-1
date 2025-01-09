package com.example.triviagame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends AppCompatActivity {

    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private ArrayList<String> playerAnswers = new ArrayList<>();
    private String playerNickname;
    private int score = 0;
    private TextView nicknameTextView;
    private TextView questionView;
    private RadioGroup answerOptions;
    private Button submitButton;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private final long QUESTION_TIME = 30000; // 30 seconds per question

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize views
        nicknameTextView = findViewById(R.id.nicknameTextView);
        questionView = findViewById(R.id.questionView);
        answerOptions = findViewById(R.id.answerOptions);
        submitButton = findViewById(R.id.submitAnswerButton);
        timerTextView = findViewById(R.id.timerTextView);

        // Retrieve the nickname passed from MainActivity
        playerNickname = getIntent().getStringExtra("player_nickname");
        if (playerNickname == null || playerNickname.isEmpty()) {
            playerNickname = "Player";
        }

        Log.d("GameActivity", "Player nickname: " + playerNickname);
        nicknameTextView.setText("Player: " + playerNickname);

        // Load and shuffle questions
        questions = createQuestions();
        Collections.shuffle(questions);

        // Display the first question
        displayQuestion();
    }

    private void displayQuestion() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionView.setText(currentQuestion.getQuestionText());

            answerOptions.clearCheck();
            answerOptions.removeAllViews();

            for (int i = 0; i < currentQuestion.getOptions().length; i++) {
                RadioButton optionButton = new RadioButton(this);
                optionButton.setText(currentQuestion.getOptions()[i]);
                optionButton.setId(i);
                answerOptions.addView(optionButton);
            }

            startTimer();

            submitButton.setOnClickListener(v -> {
                int selectedId = answerOptions.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    Toast.makeText(GameActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                } else {
                    playerAnswers.add(currentQuestion.getOptions()[selectedId]);
                    if (currentQuestion.getOptions()[selectedId].equals(currentQuestion.getCorrectAnswer())) {
                        score++;
                    }
                    currentQuestionIndex++;

                    if (currentQuestionIndex >= questions.size()) {
                        showScores();
                    } else {
                        displayQuestion();
                    }
                }
            });
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(QUESTION_TIME, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                Toast.makeText(GameActivity.this, "Time's up!", Toast.LENGTH_SHORT).show();
                currentQuestionIndex++;
                if (currentQuestionIndex >= questions.size()) {
                    showScores();
                } else {
                    displayQuestion();
                }
            }
        }.start();
    }

    private void showScores() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        Intent scoreboardIntent = new Intent(GameActivity.this, ScoreboardActivity.class);
        scoreboardIntent.putExtra("player_nickname", playerNickname);
        scoreboardIntent.putExtra("player_score", score);
        scoreboardIntent.putExtra("questions", questions);
        scoreboardIntent.putExtra("player_answers", playerAnswers);
        startActivity(scoreboardIntent);
    }


        private ArrayList<Question> createQuestions() {
            ArrayList<Question> questions = new ArrayList<>();

            questions.add(new Question("What is the capital of France?",
                    new String[]{"Paris", "Berlin", "Madrid", "Rome"}, "Paris"));
            questions.add(new Question("Who wrote 'Hamlet'?",
                    new String[]{"Shakespeare", "Dickens", "Hemingway", "Orwell"}, "Shakespeare"));
            questions.add(new Question("What is 5 + 7?",
                    new String[]{"10", "11", "12", "13"}, "12"));
            questions.add(new Question("Which planet is known as the Red Planet?",
                    new String[]{"Mars", "Venus", "Jupiter", "Saturn"}, "Mars"));
            questions.add(new Question("What is the largest ocean on Earth?",
                    new String[]{"Atlantic", "Indian", "Pacific", "Arctic"}, "Pacific"));
            questions.add(new Question("What is the boiling point of water in Celsius?",
                    new String[]{"100°C", "90°C", "110°C", "120°C"}, "100°C"));
            questions.add(new Question("Who painted the Mona Lisa?",
                    new String[]{"Van Gogh", "Leonardo da Vinci", "Picasso", "Rembrandt"}, "Leonardo da Vinci"));
            questions.add(new Question("Which is the smallest country in the world?",
                    new String[]{"Monaco", "Vatican City", "Malta", "Liechtenstein"}, "Vatican City"));
            questions.add(new Question("What is the chemical symbol for gold?",
                    new String[]{"Au", "Ag", "Fe", "Hg"}, "Au"));
            questions.add(new Question("What year did the Titanic sink?",
                    new String[]{"1905", "1912", "1920", "1918"}, "1912"));
            questions.add(new Question("What is the capital of Japan?", new String[]{"Tokyo", "Beijing", "Seoul", "Bangkok"}, "Tokyo"));
            questions.add(new Question("Who is the author of '1984'?", new String[]{"George Orwell", "J.K. Rowling", "Ernest Hemingway", "Mark Twain"}, "George Orwell"));
            questions.add(new Question("What is the square root of 64?", new String[]{"6", "7", "8", "9"}, "8"));
            questions.add(new Question("Which element has the chemical symbol 'O'?", new String[]{"Oxygen", "Hydrogen", "Gold", "Iron"}, "Oxygen"));
            questions.add(new Question("What is the tallest mountain in the world?", new String[]{"Mount Everest", "K2", "Kangchenjunga", "Lhotse"}, "Mount Everest"));
            questions.add(new Question("What is the largest planet in our solar system?", new String[]{"Jupiter", "Saturn", "Earth", "Mars"}, "Jupiter"));
            questions.add(new Question("Who painted 'Starry Night'?", new String[]{"Vincent van Gogh", "Pablo Picasso", "Claude Monet", "Leonardo da Vinci"}, "Vincent van Gogh"));
            questions.add(new Question("What is the hardest natural substance on Earth?", new String[]{"Diamond", "Gold", "Iron", "Graphite"}, "Diamond"));
            questions.add(new Question("Which country is known as the Land of the Rising Sun?", new String[]{"Japan", "China", "South Korea", "Thailand"}, "Japan"));
            questions.add(new Question("What is the main ingredient in guacamole?", new String[]{"Avocado", "Tomato", "Onion", "Garlic"}, "Avocado"));
            questions.add(new Question("Who was the first President of the United States?", new String[]{"George Washington", "Thomas Jefferson", "Abraham Lincoln", "John Adams"}, "George Washington"));
            questions.add(new Question("What is the chemical formula for water?", new String[]{"H2O", "CO2", "O2", "NaCl"}, "H2O"));
            questions.add(new Question("In which year did World War II end?", new String[]{"1945", "1939", "1918", "1950"}, "1945"));
            questions.add(new Question("Which is the largest continent on Earth?", new String[]{"Asia", "Africa", "Europe", "North America"}, "Asia"));
            questions.add(new Question("What is the longest river in the world?", new String[]{"Nile", "Amazon", "Yangtze", "Mississippi"}, "Nile"));
            questions.add(new Question("Who discovered penicillin?", new String[]{"Alexander Fleming", "Marie Curie", "Isaac Newton", "Louis Pasteur"}, "Alexander Fleming"));
            questions.add(new Question("What is the speed of light?", new String[]{"300,000 km/s", "150,000 km/s", "200,000 km/s", "250,000 km/s"}, "300,000 km/s"));
            questions.add(new Question("Which artist is known for the painting 'The Persistence of Memory'?", new String[]{"Salvador Dalí", "Vincent van Gogh", "Claude Monet", "Pablo Picasso"}, "Salvador Dalí"));
            questions.add(new Question("What is the largest mammal in the world?", new String[]{"Blue whale", "Elephant", "Giraffe", "Hippopotamus"}, "Blue whale"));
            questions.add(new Question("Who developed the theory of relativity?", new String[]{"Albert Einstein", "Isaac Newton", "Nikola Tesla", "Galileo Galilei"}, "Albert Einstein"));
            questions.add(new Question("What is the capital of Australia?", new String[]{"Canberra", "Sydney", "Melbourne", "Perth"}, "Canberra"));
            questions.add(new Question("Which country is famous for tulips?", new String[]{"Netherlands", "France", "Italy", "Germany"}, "Netherlands"));
            questions.add(new Question("Who wrote 'Pride and Prejudice'?", new String[]{"Jane Austen", "Charlotte Brontë", "Mary Shelley", "Emily Brontë"}, "Jane Austen"));
            questions.add(new Question("What is the freezing point of water in Celsius?", new String[]{"0°C", "32°C", "100°C", "50°C"}, "0°C"));
            questions.add(new Question("Who is known as the father of modern physics?", new String[]{"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Niels Bohr"}, "Albert Einstein"));
            questions.add(new Question("Which country gifted the Statue of Liberty to the United States?", new String[]{"France", "United Kingdom", "Germany", "Canada"}, "France"));
            questions.add(new Question("What is the capital of Canada?", new String[]{"Ottawa", "Toronto", "Vancouver", "Montreal"}, "Ottawa"));
            questions.add(new Question("What is the largest organ in the human body?", new String[]{"Skin", "Liver", "Heart", "Lungs"}, "Skin"));
            questions.add(new Question("Which gas is most abundant in Earth's atmosphere?", new String[]{"Nitrogen", "Oxygen", "Carbon Dioxide", "Argon"}, "Nitrogen"));
            questions.add(new Question("Who invented the telephone?", new String[]{"Alexander Graham Bell", "Thomas Edison", "Nikola Tesla", "James Watt"}, "Alexander Graham Bell"));
            questions.add(new Question("What is the capital of Brazil?", new String[]{"Brasília", "Rio de Janeiro", "São Paulo", "Salvador"}, "Brasília"));
            questions.add(new Question("Which is the longest bone in the human body?", new String[]{"Femur", "Humerus", "Tibia", "Fibula"}, "Femur"));
            questions.add(new Question("What is the currency of Japan?", new String[]{"Yen", "Dollar", "Euro", "Won"}, "Yen"));
            questions.add(new Question("Who wrote 'The Catcher in the Rye'?", new String[]{"J.D. Salinger", "Ernest Hemingway", "F. Scott Fitzgerald", "Mark Twain"}, "J.D. Salinger"));
            questions.add(new Question("What is the smallest planet in our solar system?", new String[]{"Mercury", "Venus", "Mars", "Pluto"}, "Mercury"));
            questions.add(new Question("What is the capital of Italy?", new String[]{"Rome", "Milan", "Florence", "Venice"}, "Rome"));
            questions.add(new Question("Who discovered gravity?", new String[]{"Isaac Newton", "Albert Einstein", "Galileo Galilei", "Niels Bohr"}, "Isaac Newton"));
            questions.add(new Question("What is the main ingredient in a Caesar salad?", new String[]{"Romaine lettuce", "Spinach", "Kale", "Iceberg lettuce"}, "Romaine lettuce"));
            questions.add(new Question("Which element has the atomic number 1?", new String[]{"Hydrogen", "Helium", "Oxygen", "Carbon"}, "Hydrogen"));
            questions.add(new Question("What is the longest reigning monarch of the United Kingdom?", new String[]{"Queen Elizabeth II", "Queen Victoria", "King George III", "King Henry VIII"}, "Queen Elizabeth II"));
            questions.add(new Question("What is the chemical symbol for silver?", new String[]{"Ag", "Au", "Si", "Al"}, "Ag"));
            questions.add(new Question("Which planet is known as the Morning Star?", new String[]{"Venus", "Mercury", "Mars", "Jupiter"}, "Venus"));
            questions.add(new Question("What is the capital of Russia?", new String[]{"Moscow", "Saint Petersburg", "Novosibirsk", "Yekaterinburg"}, "Moscow"));
            questions.add(new Question("Who wrote 'To Kill a Mockingbird'?", new String[]{"Harper Lee", "Mark Twain", "F. Scott Fitzgerald", "J.K. Rowling"}, "Harper Lee"));
            questions.add(new Question("Which is the largest desert in the world?", new String[]{"Sahara", "Arctic", "Gobi", "Kalahari"}, "Sahara"));
            questions.add(new Question("What is the main language spoken in Brazil?", new String[]{"Portuguese", "Spanish", "French", "English"}, "Portuguese"));
            questions.add(new Question("Who painted 'The Last Supper'?", new String[]{"Leonardo da Vinci", "Michelangelo", "Raphael", "Donatello"}, "Leonardo da Vinci"));
            questions.add(new Question("What is the main ingredient in sushi?", new String[]{"Rice", "Fish", "Seaweed", "Soy Sauce"}, "Rice"));
            questions.add(new Question("Which country is famous for its pyramids?", new String[]{"Egypt", "Mexico", "China", "Peru"}, "Egypt"));
            questions.add(new Question("What is the chemical formula for table salt?", new String[]{"NaCl", "KCl", "CaCl2", "MgCl2"}, "NaCl"));
            questions.add(new Question("Who wrote 'The Odyssey'?", new String[]{"Homer", "Virgil", "Ovid", "Sophocles"}, "Homer"));
            questions.add(new Question("What is the capital of South Korea?", new String[]{"Seoul", "Pyongyang", "Tokyo", "Beijing"}, "Seoul"));
            questions.add(new Question("Who discovered electricity?", new String[]{"Benjamin Franklin", "Thomas Edison", "Nikola Tesla", "Michael Faraday"}, "Benjamin Franklin"));

            return questions;
        }
    }
