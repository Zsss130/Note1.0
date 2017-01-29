package com.joyyou.zhaojiaqizhao.notes_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.R.attr.data;

public class MySetting extends AppCompatActivity {


    private TextView Text_Show_username;
    private TextView Text_Show_name;
    private TextView Text_Show_birthday;
    private TextView Text_Show_location;
    private TextView Text_Show_interest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        Show_Message();
        findViewById(R.id.Button_Changepassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MySetting.this,Changepassword.class);
                startActivity(i);
            }
        });

    }
    private void Show_Message(){
        Text_Show_username = (TextView) findViewById(R.id.Text_Show_username);
        Text_Show_name = (TextView) findViewById(R.id.Text_Show_name);
        Text_Show_birthday = (TextView) findViewById(R.id.Text_Show_birthday);
        Text_Show_location = (TextView) findViewById(R.id.Text_Show_location);
        Text_Show_interest = (TextView) findViewById(R.id.Text_Show_interest);


        new AsyncTask<String,Void,Void>(){
            @Override
            protected Void doInBackground(String... params) {


                try {
                    String vs = ((Alldata)getApplicationContext()).getUsename();



                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    //connect setting
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");

                    //  connection.connect();
                    DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
                    JSONObject obj = new JSONObject();
                    obj.put("username",vs);
                    dos.writeBytes(obj.toString());


                    dos.close();
                    dos.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {



                        line = new String(line.getBytes(),"utf-8");
                        JSONObject cs = new JSONObject(line);

                        Text_Show_username.setText(cs.getJSONObject("user").getString("username"));
                        Text_Show_name.setText(cs.getJSONObject("user").getString("name"));
                        Text_Show_birthday.setText(cs.getJSONObject("user").getString("birthday"));
                        Text_Show_location.setText(cs.getJSONObject("user").getString("location"));
                        Text_Show_interest.setText(cs.getJSONObject("user").getString("interest"));
                    }
                    reader.close();

                }catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute("http://192.168.12.101/diary/myinformation.php");
    }
}
