package com.joyyou.zhaojiaqizhao.notes_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewNote extends AppCompatActivity {

    private EditText Edit_title;
    private EditText Edit_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        Edit_title = (EditText) findViewById(R.id.Edit_NewNote_title);
        Edit_text = (EditText) findViewById(R.id.Edit_NewNote_text);

        findViewById(R.id.button_Newnote_Newnote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String String_username = ((Alldata)getApplicationContext()).getUsename();
                final String String_title = Edit_title.getText().toString().trim();
                final String String_text = Edit_text.getText().toString().trim();




                if(!TextUtils.isEmpty(String_title)) {


                    new AsyncTask<String, Void, Void>() {
                        @Override
                        protected Void doInBackground(String... params) {
                            try {

                                URL url = new URL(params[0]);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                                //connect setting
                                connection.setDoInput(true);
                                connection.setDoOutput(true);
                                connection.setUseCaches(false);
                                connection.setRequestMethod("POST");
                                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                                JSONObject obj = new JSONObject();
                                obj.put("username", String_username);
                                obj.put("title", String_title);
                                obj.put("text", String_text);
                                obj.put("authority", 1);
                                obj.put("author", String_username);

                                out.writeBytes(obj.toString());
                                out.flush();
                                out.close();


                                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String line;


                                while ((line = reader.readLine()) != null) {

                                    line = new String(line.getBytes(), "utf-8");
                                    JSONObject cs = new JSONObject(line);
                                    int result_code = cs.getJSONObject("creatediary").getInt("result_code");

                                    if (result_code == 0) {
                                        startActivity(new Intent(NewNote.this, MyNote.class));
                                    } else {
                                        System.out.println("Error NewNote");
                                    }

                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute("http://192.168.12.101/diary/creatediary.php");
                }
            }
        });

    }
}
