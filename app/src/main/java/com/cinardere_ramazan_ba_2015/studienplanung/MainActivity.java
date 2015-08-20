package com.cinardere_ramazan_ba_2015.studienplanung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.util.ArrayList;

import file.MyFile;
import reader.ReadDataFromPdf;
import view.SemesterTotalView;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    /////////////////////////////
    //       Attributes        //
    /////////////////////////////

    private final String TAG = getClass().getPackage().getName() + " " +getClass().getName();

    private MenuInflater menuInflater = null;
    private ImageButton imageButton_moduleOverView = null;

    private File downloadFolder = null,
                 moduleManual   = null;
    private String pathOfModuleManual = null;

    private AlertDialog.Builder alertBuilder   = null;

    private ArrayList<String> moduleManualList = null;

    private MyFile myFile = null;

    /////////////////////////////
    //          Methods        //
    /////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("" + TAG, " successfully created!!!");
        initComponents();

       // myFile = new MyFile(this.getApplicationContext());
       // myFile.createFolders();


        //myFile = new MyFile(this.getApplicationContext());
        //myFile.getObjectFromFile("ModuleManual.ser");






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void initComponents() {

        imageButton_moduleOverView = (ImageButton) findViewById(R.id.activityMain_imgBtn_moduleOverView);
        imageButton_moduleOverView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.activityMain_imgBtn_moduleOverView) {
            //initPath();

            startActivity(new Intent(this, SemesterTotalView.class));

            /*MyFile myFile = new MyFile(this.getApplicationContext());
                myFile.getObjectFromFile(getResources().getString(R.string.moduleMaualSer));
            */

        }


    }

    private void initPath() {

        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getResources().getString(R.string.chooseModuleManual));

        downloadFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        moduleManualList = new ArrayList<>();

        if(downloadFolder.exists()) {
            //Fill list; later the user will choose the moduleManual which belongs to him
            for(File file: downloadFolder.listFiles()) {

                if(file.getName().contains(getResources().getString(R.string.MHB))) {
                    moduleManualList.add(file.getName());
                }
            }


            CharSequence[] cs = moduleManualList.toArray(new CharSequence[moduleManualList.size()]);

            alertBuilder.setItems(cs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    for(File file: downloadFolder.listFiles()) {
                        if(file.getName().equals(moduleManualList.get(which))) {
                            pathOfModuleManual = file.getAbsolutePath();
                            moduleManual = file;
                        }
                    }

                    final String[] tmpArray = pathOfModuleManual.split("/");
                    //Toast.makeText(getApplicationContext(), tmpArray[tmpArray.length-1] +" ausgew?hlt!!!", Toast.LENGTH_SHORT).show();

                    new ReadDataFromPdf(moduleManual, MainActivity.this);
                }//onClick



            });

        }



        alertBuilder.create().show();


    }
}
