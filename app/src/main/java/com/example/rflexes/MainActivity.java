package com.example.rflexes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public TextView serie;
    public Button start;
    public ConstraintLayout lay;
    public long starttime;
    public ArrayList<Long> scores;
    public Button catchi;
    public long temps;
    public int tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.serie = findViewById(R.id.serie);
        this.start = findViewById(R.id.start);
        this.lay = findViewById(R.id.lay);

        this.catchi = new Button(this);
        this.lay.addView(this.catchi);
        this.catchi.setVisibility(View.INVISIBLE);


        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tour = 0;
                start.setVisibility(View.GONE);
                startGame();
            }
        });

        this.catchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temps = System.currentTimeMillis() - starttime;
                catchi.setVisibility(View.GONE);
                serie.setText(String.valueOf(temps)+ "ms");
                if (tour<=5) {
                    startGame();
                }
            }
        });
    }


    public void startGame(){
        new Thread() {
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int temps = 1000 + (int) (Math.random() * (5000 - 1000) + 1);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int x = (int) (Math.random() * (200 - 0) + 1);
                        int y = (int) (Math.random() * (200 - 0) + 1);
                        catchi.setX(x);
                        catchi.setY(y);
                        catchi.setVisibility(View.VISIBLE);
                        starttime = System.currentTimeMillis();
                    }
                });
            }
        }.start();





    }

}