package com.example.rflexes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    public TextView joueur1;
    public TextView joueur2;
    public Button catchi1;
    public Button catchi2;
    public DisplayMetrics metrics;
    public int round;
    public long starttime;
    public long temps1;
    public long temps2;
    public boolean but1;
    public boolean but2;
    public AlertDialog.Builder score;
    public int score1;
    public int score2;
    public ImageView win1;
    public ImageView win2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        this.joueur1 = findViewById(R.id.joueur1);
        this.joueur2 = findViewById(R.id.joueur2);
        this.catchi1 = findViewById(R.id.catchi1);
        this.catchi2 = findViewById(R.id.catchi2);

        this.catchi1.setVisibility(View.INVISIBLE);
        this.catchi2.setVisibility(View.INVISIBLE);

        this.round = 0;
        this.but1 = false;
        this.but2 = false;
        this.score1 = 0;
        this.score2 = 0;

        this.win1 = findViewById(R.id.win1);
        this.win2 = findViewById(R.id.win2);
        win1.setVisibility(View.GONE);
        win2.setVisibility(View.GONE);

        this.metrics = new DisplayMetrics();                //Récupération taille de l'écran
        getWindowManager().getDefaultDisplay().getMetrics(this.metrics);

        start_game();

        this.catchi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                catchi1.setVisibility(View.GONE);
                temps1 = System.currentTimeMillis()-starttime;
                //catchi2.setVisibility(View.GONE);
                joueur1.setText(temps1+"ms");
                //joueur2.setText("Joueur 1 le plus rapide");
                //start_round();
                but1 = true;
                if (but2){
                    joueur1.setTextColor(Color.parseColor("#FF0000"));
                    start_round();
                    score2 ++;
                } else{
                    joueur1.setTextColor(Color.parseColor("#00FF00"));
                }
            }
        });
        this.catchi2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temps2 = System.currentTimeMillis()-starttime;
                //catchi1.setVisibility(View.GONE);
                catchi2.setVisibility(View.GONE);
                //joueur1.setText("Joueur 2 le plus rapide");
                joueur2.setText(temps2+"ms");
                //start_round();
                but2 = true;
                if (but1){
                    joueur2.setTextColor(Color.parseColor("#FF0000"));
                    start_round();
                    score1 ++;
                } else{
                    joueur2.setTextColor(Color.parseColor("#00FF00"));
                }

            }
        });
    }

    public void start_game(){
        new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            joueur1.setText("Prêt ?");
                            joueur2.setText("Prêt ?");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        joueur1.setText("GO !");
                        joueur2.setText("GO !");
                        start_round();
                    }
                });
            }
        }.start();
    }



    public void start_round(){
        new Thread() {
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (round<5) {
                            round +=1;
                            but1 = false;
                            but2 = false;
                            int temps = 1000 + (int) (Math.random() * (4000 - 1000) + 1);   //Temps d'apparition aléatoire
                            try {
                                Thread.sleep(temps);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            joueur1.setText("");
                            joueur2.setText("");
                            int W = metrics.widthPixels - 200;
                            int H = metrics.heightPixels - 250;
                            int x1 = (int) (Math.random() * (W - 25) + 1);
                            int y1 = (int) (Math.random() * (H / 2 - 100) + H/2 + 125);
                            int x2 = (int) (Math.random() * (W - 25) + 1);
                            int y2 = (int) (Math.random() * (H / 2 - 100) + 1);
                            catchi1.setX(x1);                                 //Le x et le y du bouton sont aléatoires
                            catchi1.setY(y1);
                            catchi2.setX(x2);                                 //Le x et le y du bouton sont aléatoires
                            catchi2.setY(y2);
                            starttime = System.currentTimeMillis();         //On récupère le temps d'apparition du bouton (variable globale)
                            catchi1.setVisibility(View.VISIBLE);             //Et on fait réapparaitre le bouton à attraper
                            catchi2.setVisibility(View.VISIBLE);             //Et on fait réapparaitre le bouton à attraper
                        } else{
                            affich_result();
                        }
                    }

                });
            }
        }.start();
    }

    public void affich_result(){
        score = new AlertDialog.Builder(this);
        if (score1>score2) {
            score.setTitle("Joueur 1 a gagné "+score1+"-"+score2);
            win1.setVisibility(View.VISIBLE);
        } else if (score2>score1){
            score.setTitle("Joueur 2 a gagné "+score2+"-"+score1);
            win2.setVisibility(View.VISIBLE);
        }
        score.setPositiveButton("Ok rude", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        score.show();
    }
}