package edu.scott_tabaka.project_2j;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;


public class ResultActivity extends AppCompatActivity {

    public int difficulty;
    public int score;
    SharedPreferences sharedpreferences;
    HashMap<Integer,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //*****sets up hash map
        String baseString = "score";
        String tempString;
        for(int i=0;i<10;i++)
        {
            tempString = baseString;
            tempString += i;
            map.put(i,tempString);
        }
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedpreferences.edit();

        Intent intent = getIntent();
        difficulty = intent.getIntExtra("difficultyKey",1);
        score = sharedpreferences.getInt("currentScore", 0);

        int[] tempScoreArray = new int[10];
        for(int i=0;i<10;i++)
        {
            tempScoreArray[i] = sharedpreferences.getInt(map.get(i), 0);
        }

        int newHighScore = 0;
        int tempScore = score;
        for(int i=0;i<10;i++)
        {
            if((tempScore > 0) && (tempScore > tempScoreArray[i]))
            {
                newHighScore = 1;
                int tempInt = tempScoreArray[i];
                tempScoreArray[i] = tempScore;
                tempScore = tempInt;
            }
        }

        TextView scoreView = findViewById(R.id.scoreView);
        if(newHighScore == 1)
        {
            TextView highScoreView = findViewById(R.id.newHighScoreView);
            scoreView.setText(String.valueOf(score));
            highScoreView.setText("You achieved a new high score!");
        } else {
            scoreView.setText(String.valueOf(score));
        }

        for(int i=0;i<10;i++)
        {
            editor.putInt(map.get(i), tempScoreArray[i]);
        }
        editor.apply();

        Button button1= findViewById(R.id.btnResultPlayAgain);
        Button button2= findViewById(R.id.btnResultScores);
        Button button3= findViewById(R.id.btnResultMainMenu);

        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                Intent intent = new Intent(ResultActivity.this,GameActivity.class);
                intent.putExtra("key", difficulty);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
                Intent intent = new Intent(ResultActivity.this,ScoresActivity.class);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}