package com.example.rflexes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    public TextView text_moy;
    public TextView text_records;
    public ArrayList<String[]> moyennes;
    public ArrayList<String[]> records;
    public String moy_aff;
    public String records_aff;
    public AlertDialog.Builder validation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        this.text_moy = findViewById(R.id.text_moy);
        this.text_records = findViewById(R.id.text_top);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        load_moyennes(this);
        load_records(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.validation = new AlertDialog.Builder(this);
            validation.setTitle("Réinitialiser les scores ?");
            validation.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    erase();
                    load_moyennes(getApplicationContext());
                    load_records(getApplicationContext());
                }
            });
            validation.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            this.validation.show();

        }

        return super.onOptionsItemSelected(item);
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

    private void erase(){
        //Permet de réinitialiser le fichier de sauvegarde en recréant un fichier du même nom qui écrasera le précédent
        try {
            // Open Stream to write file.
            FileOutputStream out = this.openFileOutput("save_records.txt", Context.MODE_PRIVATE);
            FileOutputStream out2 = this.openFileOutput("save_moy.txt", Context.MODE_PRIVATE);
            out.close();
            out2.close();
        } catch (Exception e) {
            Toast.makeText(this, "Reset fail", Toast.LENGTH_SHORT).show();
        }
    }

}