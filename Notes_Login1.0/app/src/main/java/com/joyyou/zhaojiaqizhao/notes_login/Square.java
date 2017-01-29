package com.joyyou.zhaojiaqizhao.notes_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Square extends AppCompatActivity {
    public static int update_time=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square);




        findViewById(R.id.Button_update).setOnClickListener(new View.OnClickListener() { //刷新
            @Override
            public void onClick(View v) {
                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        try {
                            System.out.println("-------------------------------");
                            String String_username = ((Alldata)getApplicationContext()).getUsename();
                            URL url = new URL(params[0]);
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            //connect setting
                            connection.setDoInput(true);
                            connection.setDoOutput(true);
                            connection.setUseCaches(false);
                            connection.setRequestMethod("POST");
                            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                            //json
                            JSONObject obj = new JSONObject();
                            obj.put("username",String_username);
                            obj.put("index",update_time+"");
                            update_time=update_time+1;
                            out.writeBytes(obj.toString());
                            out.flush();
                            out.close();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            //  StringBuffer sb = new StringBuffer("");
                            while ((line = reader.readLine()) != null) {

                                line = new String(line.getBytes(), "utf-8");

                                JSONObject cs = new JSONObject(line);

                                System.out.println(line);
                            }

                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                        return null;
                    }


                }.execute("http://192.168.12.101/diary/displaypublicdiary.php");


            }
        });
    }
}
