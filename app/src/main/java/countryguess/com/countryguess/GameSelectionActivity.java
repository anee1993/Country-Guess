package countryguess.com.countryguess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class GameSelectionActivity extends AppCompatActivity {

    ImageView simplegame,timebound;

    @Override
    public void onBackPressed() {
        Intent intentToGameScreen = new Intent(GameSelectionActivity.this,GameActivity.class);
        startActivity(intentToGameScreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        simplegame = (ImageView)findViewById(R.id.simple_game);
        timebound = (ImageView)findViewById(R.id.time_bound);
        timebound.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(R.color.selected));
                        Intent intentToGameSelection = new Intent(GameSelectionActivity.this,TimeBoundGameActivity.class);
                        startActivity(intentToGameSelection);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(getResources().getColor(R.color.transparent));
                        break;
                }

                return true;
            }
        });
        simplegame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(R.color.selected));
                        Intent intentToGameSelection = new Intent(GameSelectionActivity.this,SimpleGameActivity.class);
                        startActivity(intentToGameSelection);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(getResources().getColor(R.color.transparent));
                        break;
                }

                return true;
            }
        });
    }
}
