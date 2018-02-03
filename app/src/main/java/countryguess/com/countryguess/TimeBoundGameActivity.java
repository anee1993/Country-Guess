package countryguess.com.countryguess;

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
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;


public class TimeBoundGameActivity extends AppCompatActivity {
    String formula_value;
    private ArrayList<String> formList;
    JSONArray m_jArry;
    EditText edit;
    TextView text,time,ai_name;
    Button button;
    private Random randomGen;
    boolean bool=false;
    CountDownTimer start;
    TextView scorecount;
    static int count = 0;
    int cc = 0;
    int index;
    ArrayList<String> displayedList = new ArrayList<>();

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(TimeBoundGameActivity.this);
        builder1.setMessage("Close current game?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intentToGame = new Intent(TimeBoundGameActivity.this,GameOverActivity.class);
                        intentToGame.putExtra("Score",count);
                        count=0;
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
        setContentView(R.layout.activity_time_bound_game);
        edit = (EditText)findViewById(R.id.edittext);
        text = (TextView)findViewById(R.id.systemname);
        ai_name = (TextView)findViewById(R.id.systemname);
        button = (Button)findViewById(R.id.button);
        time = (TextView)findViewById(R.id.timerView);
        scorecount=(TextView)findViewById(R.id.score);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            m_jArry = obj.getJSONArray("Countries");
            formList = new ArrayList<String>();
            //HashMap<String, String> m_li;

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject jo_inside = m_jArry.getJSONObject(i);
                Log.d("Details-->", jo_inside.getString("name"));
                formula_value = jo_inside.getString("name");
               /* m_li = new HashMap<String, String>();
                m_li.put("formule", formula_value);*/
                // m_li.put("url", url_value);
                formList.add(formula_value);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        randomName();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putname();
                edit.setText("");
            }
        });
    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("CountriesJson.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String randomName(){
        String randomStr = null;
        randomGen = new Random();
        if(formList.size()>0) {
            int index = randomGen.nextInt(formList.size());
            randomStr = formList.get(index);
            if(!displayedList.isEmpty()) {
                Log.d("Came","RandomName");
                if (!displayedList.contains(randomStr)) {
                    text.setText(randomStr);
                    displayedList.add(randomStr);
                    countDown();
                }
            }
            else{
                text.setText(randomStr);
                displayedList.add(randomStr);
                countDown();
            }
            Log.d("Came",randomStr);
        }
        return randomStr;
    }

    public String getname(){
        String randomString = null;

        for(int i=0;i<formList.size();i++) {
            randomString = formList.get(i);

            if(!displayedList.isEmpty()) {
                if (!displayedList.contains(randomString)) {
                    text.setText(randomString);
                    displayedList.add(randomString);
                    return randomString;
                }
            }
            Log.d("Came",randomString);
        }

        return randomString;

    }

    public void countDown()
    {
        start = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                time.setText(""+millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent = new Intent(TimeBoundGameActivity.this,GameOverActivity.class);
                intent.putExtra("Score",count);
                count = 0;
                startActivity(intent);
            }

        }.start();

    }

    public void putname() {

        String name = edit.getText().toString();
        if(name.equals("")){
            Toast.makeText(TimeBoundGameActivity.this, "Please enter a proper country name", Toast.LENGTH_SHORT).show();
        }
        else if(!name.equals("")) {
            char beginchar = name.charAt(0);
            char userchar = Character.toLowerCase(beginchar);
            String sysname = ai_name.getText().toString();
            int position = sysname.trim().length() - 1;
            char syschar = sysname.charAt(position);
            char systemchar = Character.toLowerCase(syschar);
            if (userchar != systemchar) {
                Toast.makeText(TimeBoundGameActivity.this, "Country should start with Letter " + syschar, Toast.LENGTH_SHORT).show();
                if (count != 0) {
                    count -= 5;
                    scorecount.setText("" + count);
                }
            }
        }
        if(displayedList.contains(name)){
            Toast.makeText(this,"You have already used this country name",Toast.LENGTH_SHORT).show();
        }

        else {
            for (int j = 0; j < m_jArry.length(); j++) {
                if (name.trim().equalsIgnoreCase(formList.get(j))) {
                    Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();
                    if(!displayedList.contains(name)) {
                        displayedList.add(name.trim());
                    }
                    count += 5;
                    scorecount.setText("" + count);
                    start.cancel();
                    String input = getName(name);
                    text.setText(input);
                    countDown();
                    break;

                }
            }
        }
        if (!formList.contains(name.trim())&&!name.trim().equals("")&&!displayedList.contains(name)) {
            Toast.makeText(getApplicationContext(), "No such country exists", Toast.LENGTH_SHORT).show();
            if (count != 0) {
                count -= 5;
            }
            scorecount.setText("" + count);
            start.cancel();
            getname();
            countDown();
        }
    }

    private String getName(String name) {
        String input = null;
            int position = name.trim().length() - 1;
            char startCharacter = name.charAt(position);
            for (int i = 0; i < formList.size(); i++) {
                input = formList.get(i);
                if (!input.equals(name.trim())) {
                    char inputstart = input.charAt(0);
                    char lowercaseinputstart = Character.toLowerCase(inputstart);
                    if (lowercaseinputstart == startCharacter) {
                        if (!displayedList.contains(input)) {
                            Log.d("lastletter", input);
                            // cc = 1;
                            //text.setText(input);
                            displayedList.add(input);
                            //countDown();
                            return input;
                        }
                    }

                }
            }


        return input;
    }
}



