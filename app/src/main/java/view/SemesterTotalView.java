package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

import data.Module;
import data.ModuleManual;
import file.MyFile;

/**
 * Created by Ramazan Cinardere} on 19.08.2015.
 */
public class SemesterTotalView extends ActionBarActivity implements AdapterView.OnItemClickListener {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private ListView listView = null;
    private ModuleManual moduleManual = null;
    private ArrayList<String> semesters = null;
    private MyFile myFile = null;
    private ArrayAdapter<String> adapter = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semester_view);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.semesterView_listViewSemester);

        //get semesters from ser_file
        semesters = getSemestersFromFile();

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, semesters);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(this);
    }

    private ArrayList<String> getSemestersFromFile() {

        myFile = new MyFile(getApplicationContext());
        moduleManual = (ModuleManual) myFile.getObjectFromFile(getResources().getString(R.string.moduleManualSer));

        ArrayList<String> tmpList = new ArrayList<>();

        if(moduleManual != null) {

            for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

                String current = moduleManual.getModuleList().get(i).getSemester();
                String indexPlusOne = "";
                String indexPlusTwo = "";

                if(i< moduleManual.getModuleList().size()-1)  {
                    indexPlusOne = moduleManual.getModuleList().get(i+1).getSemester();
                }
                if(i<moduleManual.getModuleList().size()-2) {
                    indexPlusTwo = moduleManual.getModuleList().get(i+2).getSemester();
                }

                //Log.e("current: " +current," indexPlusOne: " +indexPlusOne);
                if(!current.equals(indexPlusOne) && !current.equals(indexPlusTwo)) {
                    tmpList.add(moduleManual.getModuleList().get(i).getSemester() + getResources().getString(R.string.dotPlusSemester));
                }

            }//for

            tmpList.add(getResources().getString(R.string.totalView));
        }

        return tmpList;

    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // ListView Clicked item value
        //String itemValue    = (String) listView.getItemAtPosition(position);
        String semester = semesters.get(position);
        // Show Alert
        Toast.makeText(getApplicationContext(),
                "Position :" + position + "  ListItem : " + semester, Toast.LENGTH_LONG)
                .show();

        if(!semester.equals(getResources().getString(R.string.totalView))) {
            Intent singleView = new Intent(this, SemesterSingleView.class);
                singleView.putExtra("semester",semester);
                singleView.putExtra("moduleManual",moduleManual);
            startActivity(singleView);
        }else {
            startActivity(new Intent(this, ModuleTotalView.class).putExtra("moduleManual", moduleManual));
        }



    }


    //////////////////////////////////////////////
    //              SubClass                   //
    ////////////////////////////////////////////

    public static class SemesterSingleView extends ActionBarActivity implements AdapterView.OnItemClickListener {

        private ArrayList<String> moduleList = null;
        private ListView listView = null;
        private ArrayAdapter<String> adapter = null;
        private String semester = "";
        private ModuleManual moduleManual = null;
        private final int REQUEST_CODE = 101;
        private ArrayList<Module> objectList = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.semester_view);

          //set Title of actionbar respective to the semester
            semester = getIntent().getExtras().getString("semester");
            setTitle(semester);

          //get ModuleManual
            moduleManual = (ModuleManual) getIntent().getExtras().getSerializable("moduleManual");


          //get listview
            listView = (ListView) findViewById(R.id.semesterView_listViewSemester);


          // get modules repsective to the current semester
            checkIfModuleListIsNotNull();
            moduleList = getModules();


            adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, moduleList);

            // Assign adapter to ListView
            listView.setAdapter(adapter);

            // ListView Item Click Listener
            listView.setOnItemClickListener(this);
        }//onCreate

        private void checkIfModuleListIsNotNull() {
            if(moduleList != null) {
                if(moduleList.size() != 0) {
                    moduleList.clear();
                }
            }
        }


        /**
         * get modules of respective semester
         * */
        private ArrayList<String> getModules() {

            ArrayList<String> tmpList = new ArrayList<>();
            objectList = new ArrayList<>();

            for(int i = 0; i<moduleManual.getModuleList().size(); i++) {

                String sem = moduleManual.getModuleList().get(i).getSemester();

                /*
                 * Note that this takes a regular expression, so remember to escape special characters if necessary,
                 * e.g. if you want to split on period . which means "any character" in regex, use either split("\\.")
                 * or split(Pattern.quote("."))
                 */
                String semSplited = semester.split(Pattern.quote("."))[0];

                if(semSplited.equals(sem)) {
                    tmpList.add(moduleManual.getModuleList().get(i).getTitle());
                    objectList.add(moduleManual.getModuleList().get(i));
                }
            }
            return tmpList;
        }


        @Override
        public void setActionBar(Toolbar toolbar) {
            ActionBar actionBar = getSupportActionBar();
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Module module = objectList.get(position);

            Intent moduleSingleView = new Intent(this, ModuleSingleView.class);
                moduleSingleView.putExtra("module", module);
                moduleSingleView.putExtra("semester", semester);
                moduleSingleView.putExtra("moduleManual", moduleManual);

            startActivityForResult(moduleSingleView, REQUEST_CODE);

            Toast.makeText(getApplicationContext(), objectList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        }//onItemClick

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            switch (resultCode) {

                case Activity.RESULT_OK:
                    semester = data.getExtras().getString("semester");
                    moduleManual = (ModuleManual)data.getExtras().getSerializable("moduleManual");
                    break;
            }
        }
    }

}

