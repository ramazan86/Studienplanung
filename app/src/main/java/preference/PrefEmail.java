package preference;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import data.Student;
import email.MyEmail;
import file.MyFile;
import helper.MyHelper;

/**
 * Created by Ramazan Cinardere on 06.09.15.
 */
public class PrefEmail extends MyPrefPattern {

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
    }//onPause


    @SuppressLint("ValidFragment")
    public class mySubClass extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs_email);

            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);


            if(myFile == myFile) myFile = new MyFile(getActivity());

            try {
                student = (Student) myFile.getObjectFromFile(getActivity().getString(R.string.file_studentSer));
            }catch (Exception e) {

            }


            if(student == null) {
                student = new Student();
            }

            setTextSummary(R.string.key_editEmail, student.getEmail());

            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(getString(R.string.key_checkEmail));
            checkBoxPreference.setChecked(student.isSendEmail());


        }

        public void setTextSummary(int stringID, String text) {

            if(isAdded()) {
                preference = findPreference(getString(stringID));
            }

            if(preference instanceof EditTextPreference) {
                editTextPreference = (EditTextPreference) preference;
                editTextPreference.setSummary(" " +text);
            }

            preference = editTextPreference = null;

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            int id = 0;
            String summary = "";
            String email = "";

            switch (key) {

                case "key_editEmail":

                    email = sharedPreferences.getString(key, "");

                    if(!email.matches(MyHelper.EMAIL_PATTERN)) {

                        setTextSummary(R.string.key_editEmail, getString(R.string.invalid_email));

                    }else {
                        student.setEmail(sharedPreferences.getString(key,""));
                        setTextSummary(R.string.key_editEmail, student.getEmail());
                    }

                    break;
                case "key_checkEmail":
                    student.setSendEmail(sharedPreferences.getBoolean(key, false));
                    break;
            }

        }


        @Override
        public void onPause() {

            EditTextPreference editText = (EditTextPreference) findPreference(getString(R.string.key_editEmail));

            student.setEmail(editText.getText().toString());

            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference(getString(R.string.key_checkEmail));
                student.setSendEmail(checkBoxPreference.isChecked());

            try {

                if(!student.getEmail().equals(editText.getText().toString())) {
                    new MyFile(getApplicationContext()).createFileAndWriteObject(getString(R.string.file_studentSer), student);
                }

            }catch (Exception e) {

            }

            super.onPause();
        }
    }//inner Class



}
