package countryguess.com.countryguess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    ImageView goToMenu;
    TextView score;

    @Override
    public void onBackPressed() {
        Intent intentToGameScreen = new Intent(GameOverActivity.this,GameActivity.class);
        startActivity(intentToGameScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        goToMenu = (ImageView)findViewById(R.id.gotomenu);
        score = (TextView)findViewById(R.id.score_val);
        int highscore= Integer.parseInt(getIntent().getExtras().get("Score").toString());
        score.setText(""+highscore);
        goToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToMain = new Intent(GameOverActivity.this,GameActivity.class);
                startActivity(intentToMain);
            }
        });
    }
}
