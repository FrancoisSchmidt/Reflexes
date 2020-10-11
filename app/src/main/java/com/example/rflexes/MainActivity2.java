package com.example.rflexes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public TextView text_moy;
    public TextView text_records;
    public ArrayList<String> moyennes;
    public ArrayList<String> records;
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

            moyennes = new ArrayList<String>();
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s= null;
            while((s= br.readLine())!= null)  {
                moyennes.add(s);
            }

            moy_aff = "";
            for (String moyy :moyennes){
                moy_aff += moyy+"\n";
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

            records = new ArrayList<String>();
            BufferedReader br= new BufferedReader(new InputStreamReader(in));

            String s= null;
            while((s= br.readLine())!= null)  {
                records.add(s);
            }

            records_aff = "";
            for (String moyy :records){
                records_aff += moyy+"\n";
            }
            text_records.setText(records_aff);

        } catch (Exception e) {
            //rien
        }


    }



}