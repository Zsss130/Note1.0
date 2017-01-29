package com.joyyou.zhaojiaqizhao.notes_login;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;

import static com.joyyou.zhaojiaqizhao.notes_login.ViewMyNote.celldata_len;


public class MainActivity extends AppCompatActivity {



    public static final int LOGIN_RESULT_LOGINING = 0;
    public static final int LOGIN_RESULT_NOUSER = 1;
    public static final int LOGIN_RESULT_WRONGPASSWORD = 2;

    private Button Button_start;
    private Button Button_registeruser;
    private EditText Edit_username;
    private EditText Edit_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button_start= (Button) findViewById(R.id.Button_Start);
        Button_registeruser= (Button) findViewById(R.id.Button_Registeruser);
        Edit_username= (EditText) findViewById(R.id.Edit_Username);
        Edit_pwd= (EditText) findViewById(R.id.Edit_Pwd);

        Button_registeruser.setOnClickListener(new View.OnClickListener() { //registeruser
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,User_Register.class));
            }
        });
        Button_start.setOnClickListener(new View.OnClickListener() {  //login
            @Override
            public void onClick(View v) {
                new AsyncTask<String,Void,Void>(){
                    @Override
                    protected Void doInBackground(String... params) {
                        try {
                            System.out.println(">>>>>>>>>>>>>>>>>>OnClick");
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
                            obj.put("username", Edit_username.getText());
                            obj.put("password", Edit_pwd.getText());
                            System.out.println("obj String>>>>>>>>" + obj.toString());


                            //send to server
                            out.writeBytes(obj.toString());
                             //    out.writeBytes("1234444");
                            out.flush();
                            out.close();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String line;
                            //  StringBuffer sb = new StringBuffer("");
                            while ((line = reader.readLine()) != null) {

                                line = new String(line.getBytes(), "utf-8");

                                JSONObject cs = new JSONObject(line);
                                ///"login"
                                int result_code = cs.getJSONObject("login").getInt("result_code");



                                System.out.println(">>>>>>>>>>result_code : "+result_code);
                                if(result_code==LOGIN_RESULT_LOGINING){//logining



                                    Intent i = new Intent(MainActivity.this,Woring.class);
                               //     Bundle b = new Bundle();
                                //    b.putString("username",Edit_username.getText().toString());
                                //    i.putExtras(b);

                                    ((Alldata)getApplicationContext()).setUsename(Edit_username.getText().toString());
                                    startActivity(i);
                                }
                                else if(result_code==LOGIN_RESULT_WRONGPASSWORD){//wrongpassword
                                    System.out.println("密码错误");
                              //      Toast.makeText(MainActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
                                    System.out.println(">>>>>>>>>>>>密码错误");
                                }
                                else if(result_code==LOGIN_RESULT_NOUSER){//nouser
                                    System.out.println("用户名不存在");
                             //       Toast.makeText(MainActivity.this,"用户名不存在",Toast.LENGTH_SHORT).show();
                                    System.out.println(">>>>>>>>>>>>用户名不存在");
                                }
                                //      sb.append(line);
                                //    System.out.println(">>>>>>>>>>>>>>>> Code : " + cs.getJSONObject("login").getInt("result_code"));

                            }
                          //  System.out.println(sb);
                            reader.close();
                            connection.disconnect();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                         catch(JSONException e){
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute("http://192.168.12.101/diary/login.php");
            }
        });

    }
}
