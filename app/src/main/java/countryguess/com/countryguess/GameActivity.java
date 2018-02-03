package countryguess.com.countryguess;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    ImageView newgame,gamerules,quitgame;

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(),"To quit the game, select exit",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        newgame = (ImageView)findViewById(R.id.new_game);
        gamerules = (ImageView)findViewById(R.id.game_rules);
        quitgame = (ImageView)findViewById(R.id.quit_game);
        newgame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               switch(event.getAction()){
                   case MotionEvent.ACTION_DOWN:
                       v.setBackgroundColor(getResources().getColor(R.color.selected));
                       Intent intentToGameSelection = new Intent(GameActivity.this,GameSelectionActivity.class);
                       startActivity(intentToGameSelection);
                       break;
                   case MotionEvent.ACTION_UP:
                       v.setBackgroundColor(getResources().getColor(R.color.transparent));
                       break;
               }

                return true;
            }
        });
        gamerules.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(R.color.selected));
                        Intent intentToRules = new Intent(GameActivity.this,RulesActivity.class);
                        startActivity(intentToRules);
                        break;
                    case MotionEvent.ACTION_UP:
                        v.setBackgroundColor(getResources().getColor(R.color.transparent));
                        break;
                }

                return true;
            }
        });
        quitgame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(getResources().getColor(R.color.selected));
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(GameActivity.this);
                        builder1.setMessage("Do you want to quit?");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        finishAffinity();

                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
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
