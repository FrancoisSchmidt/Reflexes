package com.example.rflexes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public TextView text_moy;
    public TextView text_records;
    public ArrayList<String[]> moyennes;
    public ArrayList<String[]> records;
    public String moy_aff;
    public String records_aff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.text_moy = findViewById(R.id.text_moy);
        this.text_records = findViewById(R.id.text_top);

        load_moyennes(this);
        load_records(this);
    }


    private void load_moyennes(Context fileContext){
        //Permet d'actualiser les Array de sauvegarde en fonction du contecnu de save.txt
        try {
            FileInputStream in = fileContext.openFileInput("save_moy.txt");

            moyennes = new ArrayList<String[]>();
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s= null;
            while((s= br.readLine())!= null)  {
                String[] deux = s.split(" : ");
                float temps = Float.parseFloat(deux[1]);
                moyennes.add(deux);
            }

            moy_aff = "";
            boolean change = false;
            ArrayList<String[]> deja = new ArrayList<String[]>();
            int compteur = 0;
            for (int i=0; i<45; i++){
                String[] min = new String[]{"Min", "10000"};
                change = false;
                for (String[] moyy : moyennes){
                    if (!deja.contains(moyy)) {
                        if (Float.parseFloat(moyy[1]) < Float.parseFloat(min[1]))  {
                            min = moyy;
                            change = true;
                        }
                    }
                }
                if (change) {
                    deja.add(min);
                    compteur ++;
                    moy_aff += String.valueOf(compteur)+". "+ min[0] + " : " + min[1] + "ms" + "\n";
                }
            }
            text_moy.setText(moy_aff);

        } catch (Exception e) {
            //rien
        }
    }


    private void load_records(Context fileContext){
        //Permet d'actualiser les Array de sauvegarde en fonction du contecnu de save.txt
                try {
            FileInputStream in = fileContext.openFileInput("save_records.txt");

            records = new ArrayList<String[]>();
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s= null;
            while((s= br.readLine())!= null)  {
                String[] deux = s.split(" : ");
                float temps = Float.parseFloat(deux[1]);
                records.add(deux);
            }

            records_aff = "";
            boolean change = false;
            ArrayList<String[]> deja = new ArrayList<String[]>();
            int compteur = 0;
            for (int i=0; i<45; i++){
                String[] min = new String[]{"Min", "10000"};
                change = false;
                for (String[] moyy : records){
                    if (!deja.contains(moyy)) {
                        if (Float.parseFloat(moyy[1]) < Float.parseFloat(min[1]))  {
                            min = moyy;
                            change = true;
                        }
                    }
                }
                if (change) {
                    deja.add(min);
                    compteur ++;
                    records_aff += String.valueOf(compteur)+". "+ min[0] + " : " + min[1] + "ms" + "\n";
                }
            }
            text_records.setText(records_aff);

        } catch (Exception e) {
            //rien
        }
    }



}