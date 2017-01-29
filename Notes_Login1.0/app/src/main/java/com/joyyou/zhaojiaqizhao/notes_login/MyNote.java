package com.joyyou.zhaojiaqizhao.notes_login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_note);


        findViewById(R.id.Button_NewNote).setOnClickListener(new View.OnClickListener() { //新建
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNote.this,NewNote.class));
            }
        });


        findViewById(R.id.Button_Viewmynote).setOnClickListener(new View.OnClickListener() { //显示
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyNote.this,ViewMyNote.class));
            }
        });
    }
}
