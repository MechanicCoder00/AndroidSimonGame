package edu.scott_tabaka.project_2j;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private static Random rand = new Random();
    int[] moveArray = new int[1000];
    public int moveArrayElements = 0;
    public int elementsToMatch = 0;
    public int difficulty;
    public int score = 0;
    public int playerSelection = 0;
    int moveArrayIterator = 0;
    int startingColor = 0;
    int transitionColor = 0;
    int computerStartingColor = 0;
    int computerTransitionColor = 0;

    View greenButton;
    View redButton;
    View yellowButton;
    View blueButton;
    final Handler handler = new Handler();
    SharedPreferences sharedpreferences;

    private static int getRandomNumber(int min, int max)
    {
        return (min + rand.nextInt((max - min) + 1));
    }

    private void appendValueToArray() {
        for (; moveArrayIterator < moveArrayElements + 1; moveArrayIterator++) {
            moveArray[moveArrayIterator] = getRandomNumber(1,4);
        }
    }

    public void playGame() {        //computer move
        appendValueToArray();
        moveArrayElements++;
        for (int i = 0; i < moveArrayElements; i++) {
            computerMove(i);       //calls each element with a delay depending on index
        }
    }

    public void computerMove(final int index) {
        final Runnable r = new Runnable() {
            public void run() {
                if (moveArray[index] == 1) {
                    computerStartingColor = getColor(R.color.tranparentGreen);
                    computerTransitionColor = getColor(R.color.green);
                    animateComputerColorButton(greenButton);
                } else if (moveArray[index] == 2) {
                    computerStartingColor = getColor(R.color.tranparentRed);
                    computerTransitionColor = getColor(R.color.red);
                    animateComputerColorButton(redButton);
                } else if (moveArray[index] == 3) {
                    computerStartingColor = getColor(R.color.tranparentYellow);
                    computerTransitionColor = getColor(R.color.yellow);
                    animateComputerColorButton(yellowButton);
                } else if (moveArray[index] == 4) {
                    computerStartingColor = getColor(R.color.tranparentBlue);
                    computerTransitionColor = getColor(R.color.blue);
                    animateComputerColorButton(blueButton);
                }
            }
        };
        handler.postDelayed(r, ((1400 / difficulty) * index));          //delay between computer clicks
    }

    private void animateComputerColorButton(final View v) {
        final ValueAnimator animation = getValueAnimator(computerStartingColor, computerTransitionColor, v);
        animation.start();
    }

    private void animateColorButton(final View v) {
        final ValueAnimator animation = getValueAnimator(startingColor, transitionColor, v);
        animation.start();
    }

    View.OnTouchListener onTouch = new View.OnTouchListener() {     //Player click choice
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                switch (v.getId()) {
                    case R.id.btnGameUpperLeft:
                        v.performClick();
                        playerSelection = 1;
                        startingColor = getColor(R.color.tranparentGreen);
                        transitionColor = getColor(R.color.green);
                        break;
                    case R.id.btnGameUpperRight:
                        v.performClick();
                        playerSelection = 2;
                        startingColor = getColor(R.color.tranparentRed);
                        transitionColor = getColor(R.color.red);
                        break;
                    case R.id.btnGameLowerLeft:
                        v.performClick();
                        playerSelection = 3;
                        startingColor = getColor(R.color.tranparentYellow);
                        transitionColor = getColor(R.color.yellow);
                        break;
                    case R.id.btnGameLowerRight:
                        v.performClick();
                        playerSelection = 4;
                        startingColor = getColor(R.color.tranparentBlue);
                        transitionColor = getColor(R.color.blue);
                        break;
                }

                animateColorButton(v);

                //Wrong player choice
                if (moveArray[elementsToMatch] != playerSelection) {
                    final Runnable r = new Runnable() {
                        public void run() {
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putInt("currentScore", score);
                            editor.apply();
                            Intent intent = new Intent(GameActivity.this,ResultActivity.class);
                            intent.putExtra("difficultyKey", difficulty);
                            startActivity(intent);
                            finish();
                        }
                    };
                    handler.postDelayed(r, 500);    //delay before ending game
                } else {
                    //Correct player choice
                    elementsToMatch++;
                    if (moveArrayElements == elementsToMatch) {
                        elementsToMatch = 0;
                        if (moveArrayElements > score) {
                            score = moveArrayElements;
                        }
                        final Runnable r = new Runnable() {
                            public void run() {
                                playGame();
                            }
                        };
                        handler.postDelayed(r, 2000);    //delay in between rounds
                    }
                }
            }
            return true;
        }
    };

    private ValueAnimator getValueAnimator(int color1, int color2, final View button) {     //animate button
        final ValueAnimator animation = ValueAnimator.ofArgb(color2, color1);
        animation.setDuration(400);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {

                button.setBackgroundColor((Integer) valueAnimator.getAnimatedValue());
            }
        });
        animation.setRepeatMode(ValueAnimator.REVERSE);
        return animation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("key",1);

        Button btnToMainMenu = findViewById(R.id.btnGameMainMenu);

        greenButton = findViewById(R.id.btnGameUpperLeft);
        redButton = findViewById(R.id.btnGameUpperRight);
        yellowButton = findViewById(R.id.btnGameLowerLeft);
        blueButton = findViewById(R.id.btnGameLowerRight);

        greenButton.setOnTouchListener(onTouch);
        redButton.setOnTouchListener(onTouch);
        yellowButton.setOnTouchListener(onTouch);
        blueButton.setOnTouchListener(onTouch);

        final Runnable r = new Runnable() {
            public void run() {
                playGame();
            }
        };
        handler.postDelayed(r, 2000);   //initial start time delay

        btnToMainMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }
}