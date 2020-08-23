package countryguess.com.countryguess;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class SimpleGameActivity extends AppCompatActivity {

    String countryName;
    ArrayList<String> countryList;
    JSONArray countryAndCodeArray;
    EditText type_a_country;
    TextView country_name;
    Button submit;
    TextView scoreValue;
    TextView time;
    CountDownTimer countDown;
    static int count = 0;
    boolean isTimeBoundGame = false;
    ArrayList<String> displayedList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(SimpleGameActivity.this);
        builder1.setMessage("Close current game?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intentToGame = new Intent(SimpleGameActivity.this, GameOverActivity.class);
                        intentToGame.putExtra("Score", count);
                        count = 0;
                        if(null != countDown){
                            countDown.cancel();
                        }
                        startActivity(intentToGame);
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_game);
        type_a_country = (EditText) findViewById(R.id.entry_country_name);
        country_name = (TextView) findViewById(R.id.country_name);
        submit = (Button) findViewById(R.id.submit_answer);
        scoreValue = (TextView) findViewById(R.id.score);
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            isTimeBoundGame = extras.getBoolean("timeBound");
            if (isTimeBoundGame) {
                time = (TextView) findViewById(R.id.time);
                assert time != null;
                time.setVisibility(View.VISIBLE);
                countDown = countDown();
            }
        }

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            countryAndCodeArray = obj.getJSONArray("Countries");
            countryList = new ArrayList<>();

            for (int i = 0; i < countryAndCodeArray.length(); i++) {
                JSONObject jo_inside = countryAndCodeArray.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("name"));
                countryName = jo_inside.getString("name");
                countryList.add(countryName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        randomName();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putname();
                type_a_country.setText("");
            }
        });
    }


    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = getAssets().open("CountriesJson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void randomName() {
        String randomStr;
        Random randomStringGenerator = new Random();
        if (countryList.size() > 0) {
            int index = randomStringGenerator.nextInt(countryList.size());
            randomStr = countryList.get(index);
            Log.d("Came", "RandomName");
            if (!displayedList.contains(randomStr)) {
                country_name.setText(randomStr);
                if (null != countDown) {
                    countDown.start();
                }
                displayedList.add(randomStr);
            } else {
                randomName();
            }
            Log.d("Came", randomStr);
        }
    }

    @SuppressLint("SetTextI18n")
    public void putname() {

        String name = type_a_country.getText().toString();
        if (name.equals("")) {
            Toast.makeText(SimpleGameActivity.this, "Please enter a proper country name", Toast.LENGTH_SHORT).show();
        } else {
            char beginchar = name.charAt(0);
            char userchar = Character.toLowerCase(beginchar);
            String sysname = country_name.getText().toString();
            int position = sysname.trim().length() - 1;
            char syschar = sysname.charAt(position);
            char systemchar = Character.toLowerCase(syschar);
            if (userchar != systemchar) {
                Toast.makeText(SimpleGameActivity.this, "Country should start with Letter " + syschar, Toast.LENGTH_SHORT).show();
                if (count != 0) {
                    count -= 5;
                    scoreValue.setText("" + count);
                }
            }
        }

        if (displayedList.contains(name)) {
            Toast.makeText(this, "You have already used this country name", Toast.LENGTH_SHORT).show();
        } else {
            if (countryList.contains(name.trim())) {
                Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                if (!displayedList.contains(name)) {
                    displayedList.add(name.trim());
                }
                count += 5;
                scoreValue.setText("" + count);
                if (null != countDown) {
                    countDown.cancel();
                }
                String input = getName(name);
                country_name.setText(input);
                if (null != countDown) {
                    countDown.start();
                }
            } else {
                Toast.makeText(getApplicationContext(), "No such country exists", Toast.LENGTH_SHORT).show();
                if (count != 0) {
                    count -= 5;
                }
                scoreValue.setText("" + count);
                if (null != countDown) {
                    countDown.cancel();
                }
                randomName();
                if (null != countDown) {
                    countDown.start();
                }
            }
        }
    }

    private String getName(String name) {
        String input = null;
        int position = name.trim().length() - 1;
        char startCharacter = name.charAt(position);
        for (int i = 0; i < countryList.size(); i++) {
            input = countryList.get(i);
            if (!input.equals(name.trim())) {
                char inputstart = input.charAt(0);
                char lowercaseinputstart = Character.toLowerCase(inputstart);
                if (lowercaseinputstart == startCharacter) {
                    if (!displayedList.contains(input)) {
                        Log.d("lastletter", input);
                        displayedList.add(input);
                        return input;
                    }
                }
            }
        }
        return input;
    }

    public CountDownTimer countDown() {
        return new CountDownTimer(30000, 1000) {

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                time.setText("");
                time.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent = new Intent(SimpleGameActivity.this, GameOverActivity.class);
                intent.putExtra("Score", count);
                count = 0;
                if(null != countDown){
                    countDown.cancel();
                }
                startActivity(intent);
            }

        };
    }
}
