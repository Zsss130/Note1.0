package com.joyyou.zhaojiaqizhao.notes_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class User_Register extends AppCompatActivity {

    private static final int REGISTER_SUCCESS = 0;
    private static final int REGISTER_HAVEUSER = 1;

    private EditText Edit_username;
    private EditText Edit_pwd;
    private EditText Edit_repwd;


    private EditText Edit_name;
    private EditText Edit_birthday;
    private EditText Edit_interest;
    private EditText Edit_location;







    private Button Button_Register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__register);

        Button_Register = (Button) findViewById(R.id.Register_Button_Registeruser);
        Edit_username = (EditText) findViewById(R.id.Register_Edit_Username);
        Edit_pwd = (EditText) findViewById(R.id.Register_Edit_pwd);
        Edit_repwd = (EditText) findViewById(R.id.Register_Edit_repwd);//repwd == pwd?


        Edit_name = (EditText) findViewById(R.id.Register_Edit_name);
        Edit_birthday = (EditText) findViewById(R.id.Register_Edit_birthday);
        Edit_interest = (EditText) findViewById(R.id.Register_Edit_interest);
        Edit_location = (EditText) findViewById(R.id.Register_Edit_location);


        Button_Register.setOnClickListener(new View.OnClickListener() {  //login
            @Override
            public void onClick(View v) {
                System.out.println("---------->>>>>>>>>>>>>>>Register");
                System.out.println("username : "+Edit_username.getText());
                System.out.println("pwd : "+Edit_pwd.getText());
                System.out.println("repwd : "+Edit_repwd.getText());



                final String String_Username = Edit_username.getText().toString().trim();
                final String String_Pwd = Edit_pwd.getText().toString().trim();
                String String_RePwd = Edit_repwd.getText().toString().trim();
                final String String_Name = Edit_name.getText().toString().trim();
                final String String_Birthday = Edit_birthday.getText().toString().trim();
                final String String_Interest = Edit_interest.getText().toString().trim();
                final String String_Location = Edit_location.getText().toString().trim();



                System.out.println(TextUtils.isEmpty(String_Username));
                System.out.println(TextUtils.isEmpty(String_Pwd));
                System.out.println(TextUtils.isEmpty(String_RePwd));
                if(!TextUtils.isEmpty(String_Username)&&!TextUtils.isEmpty(String_Pwd)&&(Edit_repwd.getText().toString().equals(Edit_pwd.getText().toString()))) {
                    System.out.println("---------->>>>>>>>>>>>>>>Registering");
                    new AsyncTask<String, Void, Void>() {
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

                                System.out.println(">>>>>>>>>>AAAAAAAAAA");
                                //json



                                JSONObject obj = new JSONObject();
                                obj.put("username", String_Username);
                                obj.put("password", String_Pwd);
                                obj.put("name",String_Name);
                                obj.put("birthday",String_Birthday);
                                obj.put("location",String_Location);
                                obj.put("interest",String_Interest);



                                System.out.println("obj String>>>>>>>>" + obj.toString());


                                System.out.println(">>>>>>>>>>BBBBBBBBBB");
                                //send to server
                                out.writeBytes(obj.toString());


                                System.out.println(">>>>>>>>>>CCCCCCCCCC");
                                out.flush();
                                out.close();

                                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String line;
                                //  StringBuffer sb = new StringBuffer("");
                                while ((line = reader.readLine()) != null) {


                                    line = new String(line.getBytes(), "utf-8");
                                    System.out.println(">>>>>>>>>>>>>>>>>line  +   " + line);
                                    JSONObject cs = new JSONObject(line);
                                    int result_code = cs.getJSONObject("register").getInt("result_code");
                                    System.out.println(">>>>>>>>>>result_code : " + result_code);

                                    if(result_code == REGISTER_SUCCESS){ //register success
                                        startActivity(new Intent(User_Register.this,MainActivity.class));
                                    }
                                    else if(result_code==REGISTER_HAVEUSER){

                                    }

                                }
                                //  System.out.println(sb);
                                reader.close();
                                connection.disconnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute("http://192.168.12.101/diary/register.php");
                }
                else {
                    System.out.println("W W W　W W");
                    if(TextUtils.isEmpty(String_Username))  Toast.makeText(User_Register.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(String_Pwd))  Toast.makeText(User_Register.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    else if(!Edit_repwd.getText().toString().equals(Edit_pwd.getText().toString())) Toast.makeText(User_Register.this,"两次密码不相同",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
