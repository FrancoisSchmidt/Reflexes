package com.example.rflexes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public TextView affichage;
    public Button start;
    public Button catchi;
    public Button high_scores;
    public ConstraintLayout lay;
    public long starttime;
    public ArrayList<Long> liste_scores;
    public long temps;
    public int tour;
    public DisplayMetrics metrics;
    public AlertDialog.Builder nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.affichage = findViewById(R.id.affichage);      //Affichage des scores en haut à gauche de l'écran
        this.start = findViewById(R.id.start);              //Bouton pour démarer une partie
        this.high_scores = findViewById(R.id.high_scores);  //Bouton pour afficher les high_scores
        this.lay = findViewById(R.id.lay);                  //Layout
        this.liste_scores = new ArrayList<Long>();          //Liste contenant tous les scores d'une partie

        this.catchi = findViewById(R.id.catchi);            //Bouton à presser le plus rapidement possible
        this.catchi.setVisibility(View.INVISIBLE);          //Bouton invisible dès le début

        this.metrics = new DisplayMetrics();                //Récupération taille de l'écran
        getWindowManager().getDefaultDisplay().getMetrics(this.metrics);


        this.start.setOnClickListener(new View.OnClickListener() {      //Bouton start
            @Override
            public void onClick(View view) {
                tour = 1;                                               //Tour initialisé à 0
                liste_scores = new ArrayList<Long>();                   //Liste des scores réinitialisée
                start.setVisibility(View.GONE);                         //Disparition du bouton start (pour ne pas commencer une nouvelle partie)
                high_scores.setVisibility(View.GONE);                   //Disparition du bouton high scores
                affichage.setText("");
                startRound();                                            //Appel méthode début de partie
            }
        });

        this.catchi.setOnClickListener(new View.OnClickListener() {     //Bouton à attraper
            @Override
            public void onClick(View view) {
                temps = System.currentTimeMillis() - starttime;         //Calcul du temps mis à cliquer
                catchi.setVisibility(View.GONE);                        //Dès le bouton préssé, on le fait disparaître
                affichage.setText(String.valueOf(temps)+ "ms");         //Affichage du temps en haut à gauche
                liste_scores.add(temps);                                //On ajoute le temps au score
                if (tour<5) {                                           //tour<(n-1) avec n le nombre de parties souhaitées
                    tour ++;                                            //Incrémentation du nombre de tours
                    startRound();                                        //On relance un round
                }else{                                                  //Quand on a fini les 5 rounds
                    double total = 0;
                    double min = 100000;
                    for (Long note : liste_scores) {
                        total += note;
                        if (note<min){
                            min = note;                                 //On regarde le meilleur temps enregistré
                        }
                    }
                    double moyenne = total / liste_scores.size();       //Ainsi que la moyenne

                    affichage.setText(String.valueOf(liste_scores) + "\nMoyenne : "+String.valueOf(moyenne));   //On affiche les résultats
                    nomJoueur(moyenne, min);                            //On actualise les highscores (demande du nom)
                    start.setVisibility(View.VISIBLE);                  //On fait réapparaître le bouton démarrer
                    high_scores.setVisibility(View.VISIBLE);            //Le bouton highscore
                }
            }
        });

        this.high_scores.setOnClickListener(new View.OnClickListener() {        //Lorsqu'on clique sur high_scores, on change d'activité
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(intent);                                          //On va à l'activité MainActivity2

            }
        });
    }

    public void startRound(){                        //Méthode pour lancer un round
        new Thread() {
            public void run(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int temps = 1000 + (int) (Math.random() * (5000 - 1000) + 1);   //Temps d'apparition aléatoire
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        int W = metrics.widthPixels/2;
                        int H = metrics.heightPixels/2;
                        int x = (int) (Math.random() * (W - 0) + 1);
                        int y = (int) (Math.random() * (H - 0) + 1);
                        catchi.setX(x);                                 //Le x et le y du bouton sont aléatoires
                        catchi.setY(y);
                        starttime = System.currentTimeMillis();         //On récupère le temps d'apparition du bouton (variable globale)
                        catchi.setVisibility(View.VISIBLE);             //Et on fait réapparaitre le bouton à attraper
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
                    String score = s+" : " + String.valueOf(moyenne)+"ms";
                    String score2 = s+" : " + String.valueOf(best)+"ms";
                    save_moyenne(score,getApplicationContext());    //On écrit la moyenne dans le tableau des scores
                    save_best(score2,getApplicationContext());      //On écrit le meilleur temps dans le tableau

                }
            }
        });
        nom.setNeutralButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        this.nom.show();        //On affiche l'interface
    }

    //Préparation du tableau des High ScoreS
    private void save_moyenne(String data, Context fileContext) {
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