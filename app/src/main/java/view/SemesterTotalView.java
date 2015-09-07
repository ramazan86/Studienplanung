package view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

import adapter.CustomModuleListViewAdapter;
import data.Module;
import data.ModuleManual;
import file.MyFile;
import helper.MyHelper;

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

        //moduleManual
        moduleManual = (ModuleManual) new MyFile(this).getObjectFromFile(getResources().getString(R.string.moduleManualSer));

        //get semesters from ser_file
        semesters = ModuleManual.getSemesters(getApplicationContext());
        semesters.add(getResources().getString(R.string.totalView));

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data


        String[] values = semesters.toArray(new String[semesters.size()]);
        adapter = new CustomModuleListViewAdapter(this, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(this);
    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.radial_background));

        LayoutInflater myInlater = LayoutInflater.from(this);
        View customView = myInlater.inflate(R.layout.my_actionbar,null);

        actionBar.setCustomView(customView);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // ListView Clicked item value
        //String itemValue    = (String) listView.getItemAtPosition(position);
        String semester = semesters.get(position);
        // Show Alert
        /*Toast.makeText(getApplicationContext(),
                "Position :" + position + "  ListItem : " + semester, Toast.LENGTH_LONG)
                .show();
        */
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


            //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, moduleList);

            String[] values = moduleList.toArray(new String[moduleList.size()]);
            adapter = new CustomModuleListViewAdapter(this, values);

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

            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            //actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.radial_background));

            LayoutInflater myInlater = LayoutInflater.from(this);
            View customView = myInlater.inflate(R.layout.my_actionbar,null);

            actionBar.setCustomView(customView);
            actionBar.setDisplayShowCustomEnabled(true);
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Module module = objectList.get(position);

                showModuleInDetail(module);
            //Toast.makeText(getApplicationContext(), objectList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        }//onItemClick

        private void showModuleInDetail(final Module module) {


            float textSize = 0;

            //TextView: moduleTitle

                //moduletitle

                String[] m = module.getTitle().split(" ");

                if(m.length >3) {
                    textSize = getResources().getDimension(R.dimen.dimen_1_5);
                }else {
                    textSize = getResources().getDimension(R.dimen.dimen_2);
                }

            LayoutInflater factory = LayoutInflater.from(this);
            final View stateView = factory.inflate(R.layout.module_single_view, null);


            //creditpoints
            TextView textView_creditPoints = (TextView) stateView.findViewById(R.id.moduleSingleView_textView_cp);
                textView_creditPoints.setText(module.getCreditPoints());

            //Title of module
            TextView textView_moduleTitle = (TextView) stateView.findViewById(R.id.module_singleView_moduleTitle);
                textView_moduleTitle.setTextSize((textSize+6));
                textView_moduleTitle.setText(module.getTitle());

            //content
            TextView textView_content = (TextView) stateView.findViewById(R.id.module_single_view_textView_content);
                textView_content.setText(getResources().getString(R.string.content));
                textView_content.setTextSize(20);
                textView_content.setBackgroundColor(getResources().getColor(R.color.gray));

            //Page in moduleManual
            TextView textView_page = (TextView) stateView.findViewById(R.id.moduleSingeView_page);
                textView_page.setTypeface(Typeface.createFromAsset(getAssets(), MyHelper.FONTS[5]));
                textView_page.setTextColor(Color.BLUE);
                textView_page.setText("Seite " + System.getProperty("line.separator") +module.getPageOfModuleDescription() + System.getProperty("line.separator"));
                textView_page.setTextSize(15);


                textView_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent content = new Intent(getApplicationContext(), ModuleContentView.class);
                        content.putExtra("semester", semester);
                        content.putExtra("moduleManual", moduleManual);
                        content.putExtra("module", module);
                        startActivityForResult(content, REQUEST_CODE);


                    }
                });

            //assumption
            TextView textView_assumption = (TextView) stateView.findViewById(R.id.module_single_view_textView_assumption);
                //assumption
                textView_assumption.setText(getResources().getString(R.string.assumption));
                textView_assumption.setTextSize(18);
                textView_assumption.setBackgroundColor(getResources().getColor(R.color.gray));

                textView_assumption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent assumption = new Intent(getApplicationContext(), ModuleAssumptionView.class);
                        assumption.putExtra("semester", semester);
                        assumption.putExtra("moduleManual", moduleManual);
                        assumption.putExtra("module", module);
                        startActivityForResult(assumption, REQUEST_CODE);


                    }
                });



            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();


            alertDialog.setView(stateView);


            stateView.findViewById(R.id.moduleSingleView_imgButton_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();



        }

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

