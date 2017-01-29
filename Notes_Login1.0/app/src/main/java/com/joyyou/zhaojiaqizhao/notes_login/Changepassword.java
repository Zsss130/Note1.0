package com.joyyou.zhaojiaqizhao.notes_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
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

public class Changepassword extends AppCompatActivity {

    //1 没修改  2 原密码是错误 0成功  3 数据操作有问题
    private static final int CHANGEPASSWORD_AC=0;
    private static final int CHANGEPASSWORD_WA=2;
    private EditText Edit_oldpassword;
    private EditText Edit_newpassword;
    private EditText Edit_renewpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        Edit_oldpassword = (EditText) findViewById(R.id.Edit_Oldpassword);
        Edit_newpassword = (EditText) findViewById(R.id.Edit_Newpassword);
        Edit_renewpassword = (EditText) findViewById(R.id.Edit_Renewpassword);


        findViewById(R.id.Button_Sendpassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(">>>>>> change password    onClick");
                final String String_oldpassword =Edit_oldpassword.getText().toString().trim();
                final String String_newpassword =Edit_newpassword.getText().toString().trim();
                String String_renewpassword =Edit_renewpassword.getText().toString().trim();

                System.out.println("oldpassword : "+String_oldpassword);
                System.out.println("newpassword : "+String_newpassword);
                System.out.println("renewpassword ： "+String_renewpassword);

                System.out.println(!TextUtils.isEmpty(String_oldpassword));
                System.out.println(!TextUtils.isEmpty(String_newpassword));

                if(!TextUtils.isEmpty(String_oldpassword)&&!TextUtils.isEmpty(String_newpassword)&&(Edit_newpassword.getText().toString().equals(Edit_renewpassword.getText().toString()))){

                    System.out.println(">>>>>> change password    onClicking");
                    new AsyncTask<String,Void,Void>(){
                        @Override
                        protected Void doInBackground(String... params) {
                            try{

                                System.out.println(">>>>>> change password    onClicking123");

                                String vs = ((Alldata)getApplicationContext()).getUsename();
                                System.out.println(">>>>>>>>>>>>>>>>>>>>> : "+vs);

                                URL url = new URL(params[0]);
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                                //connect setting
                                connection.setDoInput(true);
                                connection.setDoOutput(true);
                                connection.setUseCaches(false);
                                connection.setRequestMethod("POST");
                                DataOutputStream out = new DataOutputStream(connection.getOutputStream());


                                JSONObject obj = new JSONObject();
                                obj.put("username",vs);
                                obj.put("oldpassword",String_oldpassword);
                                obj.put("newpassword",String_newpassword);



                                out.writeBytes(obj.toString());

                                System.out.println(">>>>>>>>>>>>>>>>>>> + "+obj.toString());
                                out.flush();
                                out.close();

                                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String line;
                                System.out.println(">>>>>>>>>>>>>>>>  and while");
                                while ((line = reader.readLine()) != null) {

                                    line = new String(line.getBytes(), "utf-8");

                                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>line "+ line);
                                    JSONObject cs = new JSONObject(line);

                                    int result_code = cs.getJSONObject("passwordmodify").getInt("result_code");

                                    System.out.println("result code : "+result_code);

                                    if(result_code==CHANGEPASSWORD_AC){
                                        startActivity(new Intent(Changepassword.this,MainActivity.class));
                                    }
                                    else if(result_code==CHANGEPASSWORD_WA){
                                      ///  Toast.makeText(Changepassword.this,"原密码错误",Toast.LENGTH_SHORT).show();


                                    }


                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute("http://192.168.12.101/diary/passwordmodify.php");


                }
                else {
                    Toast.makeText(Changepassword.this,"新密码不同或密码为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
