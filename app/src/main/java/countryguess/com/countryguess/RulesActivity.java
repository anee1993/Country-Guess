package countryguess.com.countryguess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RulesActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        Intent intentToGame = new Intent(RulesActivity.this,GameActivity.class);
        startActivity(intentToGame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
    }
}
