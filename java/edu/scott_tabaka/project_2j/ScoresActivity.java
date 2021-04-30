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


public class ScoresActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    HashMap<Integer,String> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

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

        TextView score0 = findViewById(R.id.textViewScore0);
        TextView score1 = findViewById(R.id.textViewScore1);
        TextView score2 = findViewById(R.id.textViewScore2);
        TextView score3 = findViewById(R.id.textViewScore3);
        TextView score4 = findViewById(R.id.textViewScore4);
        TextView score5 = findViewById(R.id.textViewScore5);
        TextView score6 = findViewById(R.id.textViewScore6);
        TextView score7 = findViewById(R.id.textViewScore7);
        TextView score8 = findViewById(R.id.textViewScore8);
        TextView score9 = findViewById(R.id.textViewScore9);
        score0.setText(String.valueOf(sharedpreferences.getInt(map.get(0), 0)));
        score1.setText(String.valueOf(sharedpreferences.getInt(map.get(1), 0)));
        score2.setText(String.valueOf(sharedpreferences.getInt(map.get(2), 0)));
        score3.setText(String.valueOf(sharedpreferences.getInt(map.get(3), 0)));
        score4.setText(String.valueOf(sharedpreferences.getInt(map.get(4), 0)));
        score5.setText(String.valueOf(sharedpreferences.getInt(map.get(5), 0)));
        score6.setText(String.valueOf(sharedpreferences.getInt(map.get(6), 0)));
        score7.setText(String.valueOf(sharedpreferences.getInt(map.get(7), 0)));
        score8.setText(String.valueOf(sharedpreferences.getInt(map.get(8), 0)));
        score9.setText(String.valueOf(sharedpreferences.getInt(map.get(9), 0)));

        Button button1= findViewById(R.id.btnScoresClear);
        button1.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ScoresActivity.this.sharedpreferences.edit().clear().apply();
                finish();
                Intent intent = new Intent(ScoresActivity.this,ScoresActivity.class);
                startActivity(intent);
            }
        });

        Button button2= findViewById(R.id.btnScoresMainMenu);
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}