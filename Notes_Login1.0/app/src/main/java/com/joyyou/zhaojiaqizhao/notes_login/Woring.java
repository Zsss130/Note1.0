package com.joyyou.zhaojiaqizhao.notes_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Woring extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woring);


        findViewById(R.id.Button_MySetting).setOnClickListener(this);
        findViewById(R.id.Button_MyNote).setOnClickListener(this);
        findViewById(R.id.Button_Square).setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Button_MySetting:
                startActivity(new Intent(Woring.this,MySetting.class));
                break;

            case R.id.Button_MyNote:
                startActivity(new Intent(Woring.this,MyNote.class));
                break;

            case R.id.Button_Square:
                startActivity(new Intent(Woring.this,Square.class));
                break;
        }

    }
}
