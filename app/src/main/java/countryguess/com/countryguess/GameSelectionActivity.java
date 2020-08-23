package countryguess.com.countryguess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class GameSelectionActivity extends AppCompatActivity {

    ImageView simplegame, timebound;

    @Override
    public void onBackPressed() {
        Intent intentToGameScreen = new Intent(GameSelectionActivity.this, GameActivity.class);
        startActivity(intentToGameScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        simplegame = (ImageView) findViewById(R.id.simple_game);
        timebound = (ImageView) findViewById(R.id.time_bound);

        simplegame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameSelectionActivity.this, SimpleGameActivity.class);
                startActivity(intent);
            }
        });

        timebound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameSelectionActivity.this, SimpleGameActivity.class);
                intent.putExtra("timeBound", true);
                startActivity(intent);
            }
        });
    }
}
