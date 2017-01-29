package com.joyyou.zhaojiaqizhao.notes_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewMyNote extends AppCompatActivity {

    public static CellData[] data = new CellData[100];
    public static int celldata_len;
    private RecyclerView rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_note);


        new AsyncTask<String,Void,Void>(){
            @Override
            protected Void doInBackground(String... params) {
                try{
                    //    StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
                    System.out.println("Wait Connect");
                    String String_username = "zhaojiaqizhao";
                    URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    System.out.println("Wait Connect   1");

                    //connect setting
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setUseCaches(false);
                    connection.setRequestMethod("POST");
                    System.out.println("Wait Connect   2");
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    System.out.println("Wait Connect   3");

                    //json
                    JSONObject obj = new JSONObject();
                    obj.put("username",String_username);
                    out.writeBytes(obj.toString());
                    System.out.println("Wait Connect   4");
                    out.flush();
                    out.close();


                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;

                    System.out.println("Wait Reading");
                    while((line = reader.readLine())!=null){

                        line = new String(line.getBytes(), "utf-8");
                        JSONObject cs = new JSONObject(line);

                        int Len_Json = cs.getInt("count");

                        celldata_len = Len_Json;
                        System.out.println("len_Json = "+Len_Json);


                        System.out.println("datalen = "+data.length);
                        for(int i=0;i<Len_Json;i++){
                            System.out.println("i = "+i);
                            String locaton = ""+i;
                            JSONObject cd = cs.getJSONObject(locaton);



                            data[i] = new CellData(cd.getString("title"),cd.getString("text"),cd.getString("author"),cd.getString("createtime"),cd.getString("updatetime"));
                            System.out.println("title : "+data[i].title);
                            System.out.println("text : "+data[i].text);
                            System.out.println("author : "+data[i].author);
                            System.out.println("createtime : "+data[i].createtime);
                            System.out.println("updatetime : "+data[i].updatetime);

                        }
                        /*
                        将获得的数据进行拆分
                        */
                    }
                    reader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute("http://192.168.12.101/diary/displaypersonaldiary.php");

        rv = new RecyclerView(this);
        setContentView(rv);
        rv.setLayoutManager(new LinearLayoutManager(this));  //线性布局
        rv.setAdapter(new MyAdapter());
    }
}
