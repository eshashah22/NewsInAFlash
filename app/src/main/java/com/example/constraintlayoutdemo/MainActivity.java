package com.example.constraintlayoutdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity {
    TextView title;
    TextView displayTitle;
    TextView instructions;
    EditText keyword;
    TextView displayDesc;
    Button go;
    String[] word;
    String APIKey;
    String description;
    String titleStr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.id_title);
        displayTitle =  findViewById(R.id.id_displayTitle);
        instructions = findViewById(R.id.id_instructions);
        keyword = findViewById(R.id.id_keyword);
        go = findViewById(R.id.id_go);
        displayDesc = findViewById(R.id.id_displaydesc);
        APIKey="dde15c694a0249f5b5b4ea8a43fd23e8";

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String str = keyword.getText().toString();
                    word = new String[]{str};
                    new MainTask().execute(word);

                } catch (Exception ex) {

                }
            }
        });
    }

    public String JSONtoString(BufferedReader b) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        String s = "";

        while((s = b.readLine()) != null)
        {
            sb.append(s + "\n");

        }
        return sb.toString();
    }
        private class MainTask extends AsyncTask<String, Void, Void>
        {

            @Override
            protected Void doInBackground(String... urls)
            {

                try {
                    URL url = new URL("https://newsdata.io/api/1/news?apikey=pub_12339feebfb7f3e96bda2f422537f16de284f&q="+urls[0]);
                    Log.d("TAG", String.valueOf(url));
                    HttpURLConnection uC = (HttpURLConnection) url.openConnection();



                    InputStream iC = uC.getInputStream();

                    BufferedReader buffer = new BufferedReader((new InputStreamReader(iC)));
                    String data = JSONtoString(buffer);
                    JSONObject obj = new JSONObject(data);
                    titleStr = obj.toString().substring(obj.toString().indexOf("title")+8,obj.toString().indexOf("link")-3);
                    description = obj.toString().substring(obj.toString().indexOf("description")+14,obj.toString().indexOf("content")-3);
                    Log.d("TAG1", titleStr);
                    Log.d("TAG2", description);



                }catch(IOException e)
                {
                    e.printStackTrace();;
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                displayTitle.setText("Title: "+titleStr);
                displayDesc.setText("Description: " + description);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }





}