package com.example.mvaguimaraes.quemquetolhe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;

import java.util.Random;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private ImageView tolher,tolhedores;
    public static int randomNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        RelativeLayout layout =(RelativeLayout)findViewById(R.id.activity_main);

        Random rand = new Random();
        randomNum = rand.nextInt((7 - 3) + 1) + 3;

        if (randomNum == 3){
            layout.setBackgroundResource(R.drawable.bg3);
        } else if (randomNum == 4){
            layout.setBackgroundResource(R.drawable.bg4);
        } else if (randomNum == 5){
            layout.setBackgroundResource(R.drawable.bg5);
        } else if (randomNum == 6){
            layout.setBackgroundResource(R.drawable.bg8);
        } else if (randomNum == 7){
            layout.setBackgroundResource(R.drawable.bg10);
        }

        tolher = (ImageView)findViewById(R.id.tolher_btn);

        tolher.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), wheel.class);
                startActivity(i);
            }
        });

        tolhedores = (ImageView) findViewById(R.id.tolhedores_btn);

        tolhedores.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ListaTolhedores.class);
                startActivity(i);
            }
        });

    }
}
