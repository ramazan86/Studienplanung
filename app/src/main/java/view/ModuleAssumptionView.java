package view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import data.Module;
import data.ModuleManual;
import file.MyFile;


/**
 * Created by Ramazan Cinardere} on 22.08.2015.
 */
public class ModuleAssumptionView extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextView.OnEditorActionListener {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private String semester = "";
    private ModuleManual moduleManual = null;
    private Module module             = null;

    private ImageButton imageButton_pencil  = null;

    private TextView textView_headLine = null;

    private EditText editText_assumption   = null;

    private Spinner spinner_Assumption = null;

    private ArrayAdapter<String> spinnerAssumptionAdapter = null;

    private static int numberOfEntries = 0;

    private int whichAssumptionIsSelected = 0;

    private LinearLayout moduleAssumption_linearLayout_wrapScroll = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_assumption_view);

        initComponents();
        getValuesFromIntent();
        setTitle(getResources().getString(R.string.assumption));

        createHeadline();
    }

    @SuppressLint("LongLogTag")
    private void setValuesIntoComponents(String content) {

        try {

            if(editText_assumption.getText().length() != 0) {
                editText_assumption.setText(null);
            }

            if(content.length() == 0) {
             content = getResources().getString(R.string.notANumber);
            }

            editText_assumption.setText(content);
        }catch (Exception e) {
            editText_assumption.setText(getResources().getString(R.string.notANumber));
            Log.e("Exception in" +getClass().getName() + ".setValuesIntoComponents()"," -> " +e.getMessage() + " " +e.getCause());
            e.printStackTrace();
        }

    }

    private void createHeadline() {
        textView_headLine.setText(module.getTitle());
    }

    private void initComponents() {

        //ImageButton
        imageButton_pencil = (ImageButton) findViewById(R.id.moduleAssumptionView_imageButton_pencil);
        imageButton_pencil.setOnClickListener(this);


        //TextView
        textView_headLine = (TextView) findViewById(R.id.moduleAssumptionView_textView_headLine);

        //Edittext
        editText_assumption = (EditText) findViewById(R.id.moduleAssumptionView_editText_Assumption);
        editText_assumption.setOnEditorActionListener(this);

        //ArrayAdapter
        spinnerAssumptionAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, getResources().getStringArray(R.array.array_assumptions));
        spinnerAssumptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        //Spinner
        spinner_Assumption = (Spinner) findViewById(R.id.moduleAssumption_spinner_assumption);
        spinner_Assumption.setAdapter(spinnerAssumptionAdapter);

        spinner_Assumption.setOnItemSelectedListener(this);

        //linearLayout
        moduleAssumption_linearLayout_wrapScroll = (LinearLayout) findViewById(R.id.moduleAssumptionView_linearLayout_wrapScroll);


    }

    @Override
    public void finish() {

        Intent moduleSingleView = new Intent();
            moduleSingleView.putExtra("semester",semester);
            moduleSingleView.putExtra("moduleManual", moduleManual);
            moduleSingleView.putExtra("module", module);
        setResult(Activity.RESULT_OK, moduleSingleView);
        //set the number of how much this class was visited to 0
        numberOfEntries = 0;
        super.finish();
    }

    private void getValuesFromIntent() {

        semester = getIntent().getExtras().getString("semester");
        moduleManual = (ModuleManual) getIntent().getExtras().getSerializable("moduleManual");
        module = (Module) getIntent().getExtras().getSerializable("module");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: finish(); return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.moduleAssumptionView_imageButton_pencil) {
            changeEditTextUseAbility(true);
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //((TextView) parent.getChildAt(0)).setBackgroundColor(getResources().getColor(R.color.green));

        //((TextView) parent.getChildAt(0)).setTextSize(30);


        //1 -> contentual assumption
        //2 -> participate assumption
        //3 -> exam assumption

        if(numberOfEntries != 0) {

            moduleAssumption_linearLayout_wrapScroll.setVisibility(View.VISIBLE);

            switch (position) {
                case 1: setValuesIntoComponents(module.getContentualPrerequisite());  break;
                case 2: setValuesIntoComponents(module.getParticipatePrerequisite()); break;
                case 3: setValuesIntoComponents(module.getExaminationPrerequisite()); break;
                case 4: setValuesIntoComponents(module.getAdditionalPrerequisite());  break;
                default: Toast.makeText(getApplicationContext(), getResources().getString(R.string.chooseValidAssumption), Toast.LENGTH_SHORT).show();
            }
        }

        numberOfEntries++;
        whichAssumptionIsSelected = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {




    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


        /***
         *  //If user hit "OK" or "Enter" button on his device keyboard
         if(actionId == EditorInfo.IME_ACTION_DONE) {

         //update content of respective module if and only if the content is changed of the current module
         for(int i = 0; i<moduleManual.getModuleList().size(); i++) {
         if(moduleManual.getModuleList().get(i).getTitle().equals(module.getTitle())) {

         if(!moduleManual.getModuleList().get(i).getContent().equals(v.getText())) {
         moduleManual.getModuleList().get(i).setContent(v.getText().toString());

         //create new serialized file
         MyFile myFile = new MyFile(this);
         myFile.createFileAndWriteObject(getResources().getString(R.string.moduleManualSer), moduleManual);

         moduleManual = (ModuleManual) myFile.getObjectFromFile(getResources().getString(R.string.moduleManualSer));
         }
         }
         }

         changeEditTextUseAbility(false);
         * */


        if(actionId == EditorInfo.IME_ACTION_DONE) {

            for(int i = 0; i<moduleManual.getModuleList().size(); i++) {


                if(moduleManual.getModuleList().get(i).getTitle().equals(module.getTitle())) {

                    moduleManual.getModuleList().get(i).updatePrerequisite(whichAssumptionIsSelected, v.getText().toString());

                    //create new serialized file
                    new MyFile(this).createFileAndWriteObject(getResources().getString(R.string.moduleManualSer), moduleManual);

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.changesSuccess), Toast.LENGTH_SHORT).show();
                }
            }
        }


        changeEditTextUseAbility(false);
        return false;
    }

    @Override
    public void onBackPressed() {
        numberOfEntries = 0;
        super.onBackPressed();
    }

    private void changeEditTextUseAbility(boolean state) {
        editText_assumption.setFocusableInTouchMode(state);
        editText_assumption.setEnabled(state);
        editText_assumption.setClickable(state);
        editText_assumption.setFocusable(state);
        editText_assumption.setInputType(InputType.TYPE_CLASS_TEXT);
        editText_assumption.setOnEditorActionListener(this);
    }
}
