package preference;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;


import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;

import data.Student;
import file.MyFile;

/**
 * Created by
 * Ramazan Cinardere
 *
 */
public class PrefProfile extends MyPrefPattern {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private SharedPreferences sharedPreferences = null;

    private FileOutputStream fos = null;
    private ObjectOutputStream os = null;
    private String fileName = null;

    private FileInputStream fin = null;
    private ObjectInputStream oin = null;

    private Student student = null;

    private Preference preference = null;

    private EditTextPreference editTextPreference = null;
    private ListPreference listPreference = null;

    private MyFile myFile = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    public void onCreate(Bundle savedInstanceState) {
        myFrag = new mySubClass();
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.e("onPause()."+getClass().getName()," XXXX ");

    }//onPause


    @SuppressLint("ValidFragment")
    public class mySubClass extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs_profil);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);


            if(myFile == null) {
                myFile = new MyFile(getActivity());
            }

            try {
                student = (Student) myFile.getObjectFromFile(getActivity().getString(R.string.file_studentSer));
            }catch (Exception e) {
            }

            if(student == null) {
                student = new Student();
            }



            if(student != null) {
                setTextSummary(R.string.key_userName, student.getName());
                setTextSummary(R.string.key_matrikelNr, student.getMatrikelNumber());
                setTextSummary(R.string.key_studySubject, student.getStudySubject());
                setTextSummary(R.string.key_semester, student.getSemester());
            }


            //set check mark to the state of the user
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(getString(R.string.key_checkAvatar));
                //checkBoxPreference.setChecked(student.isShowAvatar());


        }

        public void setTextSummary(int stringID, String text) {

            if(isAdded()) {
                preference = findPreference(getString(stringID));
            }

            if(preference instanceof EditTextPreference) {
                editTextPreference = (EditTextPreference) preference;
                editTextPreference.setSummary(" " +text);
            }

            if(preference instanceof ListPreference) {
                listPreference = (ListPreference) preference;
                listPreference.setSummary(" " +text);
            }

            preference = editTextPreference = null;


        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            int id = 0;
            String summary = "";

            switch (key) {

                case "key_name":
                    assignName(sharedPreferences, key);
                    id = R.string.key_userName;
                    summary = student.getName();
                    break;

                case "key_matrikelnr":
                    assignMatrikelNumber(sharedPreferences, key);
                    id = R.string.key_matrikelNr;
                    summary = student.getMatrikelNumber();
                    break;

                case "key_studienfach":
                    assignStudySubject(sharedPreferences, key);
                    id = R.string.key_studySubject;
                    summary = student.getStudySubject();
                    break;


                case "key_semester":
                    assignSemester(sharedPreferences, key);
                    id = R.string.key_semester;
                    summary = student.getSemester();
                    break;


                case "key_avatar":
                    Log.e(" key_avatar: " ," " +sharedPreferences.getBoolean(key,false));
                    student.setShowAvatar(sharedPreferences.getBoolean(key, false));
                    break;

            }

            if(!key.equals("key_avatar")) {
                setTextSummary(id, summary);
            }

        }

        private void assignSemester(SharedPreferences sharedPreferences, String key) {
            student.setSemester(sharedPreferences.getString(key, ""));
        }

        private void assignStudySubject(SharedPreferences sharedPreferences, String key) {
            student.setStudySubject(sharedPreferences.getString(key, ""));
        }

        private void assignMatrikelNumber(SharedPreferences sharedPreferences, String key) {
            student.setMatrikelNumber(sharedPreferences.getString(key, ""));
        }

        private void assignName(SharedPreferences sharedPreferences, String key) {
            student.setName(sharedPreferences.getString(key, ""));
        }


        @Override
        public void onPause() {

            EditTextPreference name     = (EditTextPreference) findPreference(getString(R.string.key_userName));
            EditTextPreference matrNr   = (EditTextPreference) findPreference(getString(R.string.key_matrikelNr));
            EditTextPreference subject  = (EditTextPreference) findPreference(getString(R.string.key_studySubject));
            EditTextPreference semest   = (EditTextPreference) findPreference(getString(R.string.key_semester));


            if(student.getName().equals(getString(R.string.notANumber))) {
                Log.e("====> "," " +name.getText().toString());
                student.setName(name.getText().toString());
            }

            if(student.getMatrikelNumber().equals(getString(R.string.notANumber))) {
                student.setMatrikelNumber(matrNr.getText().toString());
            }

            if(student.getStudySubject().equals(getString(R.string.notANumber))) {
                student.setStudySubject(subject.getText().toString());
            }

            if(student.getSemester().equals(getString(R.string.notANumber))) {
                student.setSemester(semest.getText().toString());
            }


            try {
                new MyFile(getApplicationContext()).createFileAndWriteObject(getString(R.string.file_studentSer), student);
            }catch (Exception e) {

            }





            super.onPause();
        }
    }
}
