package countryguess.com.countryguess;

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


public class Test extends AppCompatActivity {
    String formula_value;
    private ArrayList<String> formList;
    JSONArray m_jArry;
    EditText edit;
    TextView text,time;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_bound_game);
        edit = (EditText)findViewById(R.id.edittext);
        text = (TextView)findViewById(R.id.systemname);
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
            if(!displayedList.equals(null)) {
                Log.d("Came","RandomName");
                if (!displayedList.contains(randomStr)) {
                    text.setText(randomStr);
                    displayedList.add(randomStr);
                    countDown();
                }
            }
            Log.d("Came",randomStr);
        }
        return randomStr;
    }

    public String getname(){
        String randomString = null;

        for(int i=0;i<formList.size();i++) {
            randomString = formList.get(i);

            if(!displayedList.equals(null)) {
                if (!displayedList.contains(randomString)) {
                    text.setText(randomString);
                    displayedList.add(randomString);
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
                getname();
            }

        }.start();

    }

    public void putname() {
        String name = edit.getText().toString();
        getName(name);
        for (int j = 0; j < m_jArry.length(); j++) {
            if (name.equalsIgnoreCase(formList.get(j)) && cc==1) {
                Toast.makeText(getApplicationContext(), "Correct Answer", Toast.LENGTH_SHORT).show();

                count += 5;
                scorecount.setText("" + count);

                start.cancel();
                countDown();

            }
        }
        if (!formList.contains(name)) {
            Toast.makeText(getApplicationContext(), "No such country exists", Toast.LENGTH_SHORT).show();
            if (count != 0) {
                count -= 5;
            }
            scorecount.setText("" + count);
            start.cancel();
            getname();
        }
    }

    private String getName(String name) {
        String input = null;
        int position = name.length()-1;
        char startCharacter = name.charAt(position);
        for(int i=0;i<formList.size();i++) {
            input = formList.get(i);
            char inputstart = input.charAt(0);
            char lowercaseinputstart = Character.toLowerCase(inputstart);
            if(lowercaseinputstart==startCharacter) {
                if (!displayedList.contains(input)) {
                    Log.d("lastletter", input);
                    cc = 1;
                    text.setText(input);
                    //countDown();
                    return input;
                }
            }

        }
        return input;
    }
}



