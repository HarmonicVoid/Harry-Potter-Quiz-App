package com.voi.harmonic.harrypotterquiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mTrueButton;
    Button mFalseButton;
    TextView mScoreTextView;
    TextView mQuestionTextView;
    ProgressBar mProgressBar;

    int mIndex;
    int mScore;
    int mQuestion;


    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, false),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, false),
            new TrueFalse(R.string.question_5, false),
            new TrueFalse(R.string.question_6, true),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, false),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, true),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,false)
    };

    final int PROGRESS_BAR_INCREMENT = (int) Math.ceil(100.0 / mQuestionBank.length);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
        } else {
            mScore  = 0;
        }

        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mQuestionTextView = findViewById(R.id.question_text_view);
        mScoreTextView = findViewById(R.id.score);
        mProgressBar = findViewById(R.id.progress_bar);

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateQuestion();
            }
        });
  }





    private void updateQuestion() {

        mIndex = (mIndex + 1) % mQuestionBank.length;
        if(mIndex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Quiz Complete");
            alert.setCancelable(false);

            if (mScore > 10) {

                alert.setMessage("You Scored " + mScore + " points!" +
                        "                                             Welcome Wizard! You shall receive news soon...");

                alert.setPositiveButton("Receive your wand!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, Treasure.class));
                        finish();
                    }
                });
                alert.show();

            } else {

                alert.setMessage("You Scored " + mScore + " points!" +
                        "                                     You are nothing but a Muggle!" +
                        "                                     Score higher than 10 to pass the quiz");

                alert.setPositiveButton("Try Again?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alert.show();


            }

        }

        mQuestion = mQuestionBank[mIndex].getQuestionID();
        mQuestionTextView.setText(mQuestion);
        mProgressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        mScoreTextView.setText("Score " + mScore + "/" + mQuestionBank.length);

    }


    private void checkAnswer(boolean userSelection) {

        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();

        if(userSelection == correctAnswer) {
            Toast.makeText(getApplicationContext(),R.string.correct_toast,Toast.LENGTH_SHORT).show();
            mScore = mScore + 1;
        } else {
            Toast.makeText(getApplicationContext(),R.string.incorrect_toast,Toast.LENGTH_SHORT).show();

        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);

    }


}
