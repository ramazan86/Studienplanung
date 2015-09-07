package com.cinardere_ramazan_ba_2015.studienplanung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import activity.MySettings;
import activity.MyTabActivity;
import data.ModuleManual;
import data.ModuleOrganizer;
import data.Student;
import email.MyEmail;
import file.MyFile;
import helper.MyHelper;
import reader.ReadDataFromPdf;
import view.ModuleAssumptionView;
import view.ModuleContentView;
import view.SemesterTotalView;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    /////////////////////////////
    //        ATTRIBUTES       //
    /////////////////////////////

    private final String TAG = getClass().getPackage().getName() + " " +getClass().getName();

    private MenuInflater menuInflater = null;

    private ImageButton imageButton_moduleOverView = null,
                        imageButton_examination    = null,
                        imageButton_pdf            = null;

    private File downloadFolder = null,
                 moduleManual   = null;

    private String pathOfModuleManual = null;

    private AlertDialog.Builder alertBuilder   = null;

    private ArrayList<String> moduleManualList = null;

    private MyFile myFile = null;

    private ImageButton imageButton_avatar = null;

    private Typeface font = null;

    private TextView textView_overview      = null,
                     textView_notesAndExams = null;

    private Student student = null;

    /////////////////////////////
    //         METHODS         //
    /////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();

        boolean state = new MyFile(this).createFolders();


        MyFile myFile = new MyFile(this);

        if(myFile.checkIfFileExists(getString(R.string.file_studentSer))) {
            student = (Student) new MyFile(this).getObjectFromFile(this.getString(R.string.file_studentSer));
        }else {
            student = new Student();
        }


        if(student.isShowAvatar()) {
            imageButton_avatar.setVisibility(View.VISIBLE);
        }else {
            imageButton_avatar.setVisibility(View.INVISIBLE);
        }

        font = Typeface.createFromAsset(getAssets(), MyHelper.FONTS[0]);

        textView_overview.setTypeface(font);
        textView_overview.setTextSize(20);

        textView_notesAndExams.setTypeface(font);
        textView_notesAndExams.setTextSize(20);


        //new ModuleOrganizer(this).desiredNoteAverage(2.5f);

       // myFile = new MyFile(this.getApplicationContext());
       // myFile.createFolders();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.action_properties: openSettings(); break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        startActivity(new Intent(this, MySettings.class));
    }

    public void initComponents() {

        imageButton_moduleOverView = (ImageButton) findViewById(R.id.activityMain_imgBtn_moduleOverView);
        imageButton_moduleOverView.setOnClickListener(this);

        imageButton_examination = (ImageButton) findViewById(R.id.activityMain_imgBtn_examination);
        imageButton_examination.setOnClickListener(this);

        imageButton_avatar = (ImageButton) findViewById(R.id.activityMain_imgBtn_avatar);
        imageButton_avatar.setOnClickListener(this);

        imageButton_pdf = (ImageButton) findViewById(R.id.activityMain_imgBtn_pdf);
        imageButton_pdf.setOnClickListener(this);

        textView_overview = (TextView) findViewById(R.id.activityMain_textView_overview);
        textView_notesAndExams = (TextView) findViewById(R.id.activityMain_textView_notesAndExames);
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

        if(v.getId() == R.id.activityMain_imgBtn_examination) {
            startActivity(new Intent(this, MyTabActivity.class));
        }



        if(v.getId() == R.id.activityMain_imgBtn_avatar) {
            showStudentView();
        }


        if(v.getId() == R.id.activityMain_imgBtn_pdf) {
            initPath();
        }



    }

    private void showStudentView() {

        LayoutInflater factory = LayoutInflater.from(this);
        final View stateView = factory.inflate(R.layout.student_view, null);


        TextView textView_name = (TextView) stateView.findViewById(R.id.studentView_textView_name);
            textView_name.setText(student.getName());

        TextView textView_matrikelNr = (TextView) stateView.findViewById(R.id.studentView_textView_matrikleNr);
            textView_matrikelNr.setText(student.getMatrikelNumber());

        TextView textView_subject = (TextView) stateView.findViewById(R.id.studentView_textView_subject);
            textView_subject.setText(student.getStudySubject());

        TextView textView_semester = (TextView) stateView.findViewById(R.id.studentView_textView_semester);
            textView_semester.setText(student.getSemester());

        TextView textView_email = (TextView) stateView.findViewById(R.id.studentView_textView_email);
            textView_email.setText(student.getEmail());

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();


        alertDialog.setView(stateView);


        stateView.findViewById(R.id.studentView_imageButton_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();




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

                    for (File file : downloadFolder.listFiles()) {
                        if (file.getName().equals(moduleManualList.get(which))) {
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
