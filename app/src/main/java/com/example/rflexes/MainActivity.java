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
                    double total = 0;
                    for (Long note : scores) {
                        total += note;
                    }
                    double moyenne = total / scores.size();
                    serie.setText(String.valueOf(scores) + "\nMoyenne : "+String.valueOf(moyenne));
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
                            Thread.sleep(temps);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int W = metrics.widthPixels/2;
                        int H = metrics.heightPixels/2;
                        int x = (int) (Math.random() * (W - 0) + 1);
                        int y = (int) (Math.random() * (H - 0) + 1);

                        catchi.setX(x);
                        catchi.setY(y);
                        catchi.setVisibility(View.VISIBLE);
                        starttime = System.currentTimeMillis();
                    }
                });
            }
        }.start();
    }



    //TODO
    //Préparation du tableau des High Scores
    /*
    private void saveFav(String data, Context fileContext) {
        //Méthode d'écriture sur un fichier .txt
        try {
            // Open Stream to write file.
            FileOutputStream out = fileContext.openFileOutput("save.txt", Context.MODE_APPEND);
            out.write(data.getBytes());
            out.write("\n".getBytes());
            out.close();
        } catch (Exception e) {
            //rien
        }
    }


    private void majFav(Context fileContext){
        //Permet d'actualiser les Array de sauvegarde en fonction du contecnu de save.txt
        try {
            FileInputStream in = fileContext.openFileInput("save.txt");

            favoris = new ArrayList<Integer>();
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s= null;
            while((s= br.readLine())!= null)  {
                String deux[] = s.split(";");
                int id1 = Integer.parseInt(deux[0]);
                int id2 = Integer.parseInt(deux[1]);
                favoris.add(id1);
                favoris_ogg.add(id2);
                favoris_txt.add(deux[2]);
            }

        } catch (Exception e) {
            Toast.makeText(fileContext,"Error:"+ e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

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