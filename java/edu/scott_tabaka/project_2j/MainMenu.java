package edu.scott_tabaka.project_2j;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class MainMenu extends AppCompatActivity {
    int difficulty = 1;
    ToggleButton easyButton;
    ToggleButton mediumButton;
    ToggleButton hardButton;

    private void setDifficultyButtons(ToggleButton easyButton, ToggleButton mediumButton, ToggleButton hardButton) {
        switch (difficulty) {
            case 1:
                easyButton.setChecked(true);
                mediumButton.setChecked(false);
                hardButton.setChecked(false);
                break;
            case 2:
                easyButton.setChecked(false);
                mediumButton.setChecked(true);
                hardButton.setChecked(false);
                break;
            case 3:
                easyButton.setChecked(false);
                mediumButton.setChecked(false);
                hardButton.setChecked(true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyButton= findViewById(R.id.btnMMEasy);
        mediumButton= findViewById(R.id.btnMMMedium);
        hardButton= findViewById(R.id.btnMMHard);
        Button startButton= findViewById(R.id.btnMMStartGame);
        Button viewScoresButton= findViewById(R.id.btnMMViewScores);

        setDifficultyButtons(easyButton, mediumButton, hardButton);

        easyButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                difficulty = 1;
                setDifficultyButtons(easyButton, mediumButton, hardButton);
            }
        });

        mediumButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                difficulty = 2;
                setDifficultyButtons(easyButton, mediumButton, hardButton);
            }
        });

        hardButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                difficulty = 3;
                setDifficultyButtons(easyButton, mediumButton, hardButton);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MainMenu.this,GameActivity.class);
                intent.putExtra("key", difficulty);
                startActivity(intent);
            }
        });

        viewScoresButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                startActivity(new Intent(MainMenu.this,ScoresActivity.class));
            }
        });
    }
}
