package com.example.rflexes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public TextView serie;
    public Button start;
    public ConstraintLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.serie = findViewById(R.id.serie);
        this.start = findViewById(R.id.start);
        this.lay = findViewById(R.id.lay);

        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        start.setVisibility(View.GONE);
                        for (int i=0 ; i<5 ; i++){
                            int temps = 1000 + (int)(Math.random()*(5000-1000)+1);
                            int x = (int)(Math.random()*(200-0)+1);
                            int y = (int)(Math.random()*(200-0)+1);
                            try {
                                Thread.sleep(temps);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Button cacthi = new Button(this);
                            cacthi.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });




                        }
                    }
                }).start();
            }
        });
        

    }
}