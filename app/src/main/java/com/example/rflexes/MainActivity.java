package com.example.rflexes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public TextView serie;
    public Button start;
    public Button catchi;
    public Button high_scores;
    public ConstraintLayout lay;
    public long starttime;
    public ArrayList<Long> scores;
    public long temps;
    public int tour;
    public DisplayMetrics metrics;
    public AlertDialog.Builder nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.serie = findViewById(R.id.serie);
        this.start = findViewById(R.id.start);
        this.high_scores = findViewById(R.id.high_scores);
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
                high_scores.setVisibility(View.GONE);
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
                    double total = 0;
                    double min = 100000;
                    for (Long note : scores) {
                        total += note;
                        if (note<min){
                            min = note;
                        }
                    }
                    double moyenne = total / scores.size();

                    serie.setText(String.valueOf(scores) + "\nMoyenne : "+String.valueOf(moyenne));
                    start.setVisibility(View.VISIBLE);
                    high_scores.setVisibility(View.VISIBLE);
                    nomJoueur(moyenne, min);
                }
            }
        });

        this.high_scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);

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
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int W = metrics.widthPixels/2;
                        int H = metrics.heightPixels/2;
                        int x = (int) (Math.random() * (W - 0) + 1);
                        int y = (int) (Math.random() * (H - 0) + 1);

                        catchi.setX(x);
                        catchi.setY(y);
                        starttime = System.currentTimeMillis();
                        catchi.setVisibility(View.VISIBLE);
                    }
                });
            }
        }.start();
    }

    public void nomJoueur(final double moyenne, final double best){
        //Création de la boîte de dialogue qui demande le nom du joueur
        this.nom = new AlertDialog.Builder(this);
        nom.setTitle("Nom du joueur");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        nom.setView(input);
        nom.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = input.getText().toString();
                if (!s.equals("") && !s.equals(" ")) {
                    //serie.setText(s);
                    String score = s+" : " + String.valueOf(moyenne)+"ms";
                    String score2 = s+" : " + String.valueOf(best)+"ms";
                    save_moyenne(score,getApplicationContext());
                    save_best(score2,getApplicationContext());

                }
            }
        });
        nom.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        this.nom.show();
    }

    //TODO
    //Préparation du tableau des High ScoreS

    private void save_moyenne(String data, Context fileContext) {
        //Méthode d'écriture sur un fichier .txt
        try {
            // Open Stream to write file.
            FileOutputStream out = fileContext.openFileOutput("save_moy.txt", Context.MODE_APPEND);
            out.write(data.getBytes());
            out.write("\n".getBytes());
            out.close();
        } catch (Exception e) {
            //rien
        }
    }

    private void save_best(String data, Context fileContext) {
        //Méthode d'écriture sur un fichier .txt
        try {
            // Open Stream to write file.
            FileOutputStream out = fileContext.openFileOutput("save_records.txt", Context.MODE_APPEND);
            out.write(data.getBytes());
            out.write("\n".getBytes());
            out.close();
        } catch (Exception e) {
            //rien
        }
    }

/*
    private void erase(){
        //Permet de réinitialiser le fichier de sauvegarde en recréant un fichier du même nom qui écrasera le précédent
        try {
            // Open Stream to write file.
            FileOutputStream out = this.openFileOutput("save.txt", Context.MODE_PRIVATE);
            out.close();
        } catch (Exception e) {
            //rien
        }
    }

     */


}