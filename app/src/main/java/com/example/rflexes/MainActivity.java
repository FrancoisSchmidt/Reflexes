package com.example.rflexes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.SystemClock;
import android.text.Layout;
import android.util.DisplayMetrics;
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
    public DisplayMetrics metrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.serie = findViewById(R.id.serie);
        this.start = findViewById(R.id.start);
        this.lay = findViewById(R.id.lay);
        this.scores = new ArrayList<Long>();

        this.catchi = findViewById(R.id.catchi);

        this.catchi.setVisibility(View.INVISIBLE);
        /*this.lay.addView(this.catchi);
        this.catchi.setWidth(50);
        this.catchi.setHeight(50);*/

        this.metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(this.metrics);

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tour = 0;
                scores = new ArrayList<Long>();
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
                scores.add(temps);
                if (tour<4) {   //tour<(n-1) avec n le nombre de parties souhaitées
                    tour ++;
                    startGame();
                }else{
                    serie.setText(String.valueOf(scores));
                    start.setVisibility(View.VISIBLE);
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
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int x = (int) (Math.random() * (200 - 0) + 1);
                        int y = (int) (Math.random() * (200 - 0) + 1);
                        x =metrics.widthPixels/2;
                        y=metrics.heightPixels/2;
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