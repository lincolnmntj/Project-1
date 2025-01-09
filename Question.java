package com.example.triviagame;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    public String questionText;
    public String[] options;
    public String correctAnswer; // Store the correct answer as a string

    // Constructor
    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    // Parcelable constructor
    protected Question(Parcel in) {
        questionText = in.readString();
        options = in.createStringArray();
        correctAnswer = in.readString();
    }

    // Parcelable Creator
    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(questionText);
        dest.writeStringArray(options);
        dest.writeString(correctAnswer);
    }

    // Getter for question text
    public String getQuestionText() {
        return questionText;
    }

    // Getter for options
    public String[] getOptions() {
        return options;
    }

    // Getter for correct answer
    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
