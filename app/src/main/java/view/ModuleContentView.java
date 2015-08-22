package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import data.Module;
import data.ModuleManual;
import file.MyFile;

/**
 * Created by Ramazan Cinardere} on 20.08.2015.
 */
public class ModuleContentView extends ActionBarActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private String semester = "";
    private ModuleManual moduleManual = null;
    private Module module = null;

    private TextView textView_headLine = null,
                     textView_editContent = null;
    private EditText editText_editContent = null;

    private FileOutputStream fileOutputStream = null;
    private ObjectOutputStream objectOutputStream = null;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_content_view);

        setTitle(getResources().getString(R.string.content));

        initComponents();

        getValuesFromIntent();

        assignValuesToComponents();
    }

    private void assignValuesToComponents() {

        textView_headLine.setText(module.getTitle());
        editText_editContent.setText(module.getContent());
    }

    private void initComponents() {

        textView_headLine = (TextView) findViewById(R.id.moduleContentView_textView_headLine);

        textView_editContent = (TextView) findViewById(R.id.moduleContentView_textView_edit);
        textView_editContent.setClickable(true);
        textView_editContent.setOnClickListener(this);

        editText_editContent = (EditText) findViewById(R.id.moduleContentView_editText_content);
        editText_editContent.setOnEditorActionListener(this);
    }

    @Override
    public void finish() {

        Intent moduleSingleView = new Intent();
            moduleSingleView.putExtra("semester",semester);
            moduleSingleView.putExtra("moduleManual", moduleManual);
            moduleSingleView.putExtra("module", module);
        setResult(Activity.RESULT_OK, moduleSingleView);
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

        if(v.getId() == R.id.moduleContentView_textView_edit) {
          changeEditTextUseAbility(true);
        }
    }

    private void changeEditTextUseAbility(boolean state) {
        editText_editContent.setFocusableInTouchMode(state);
        editText_editContent.setEnabled(state);
        editText_editContent.setClickable(state);
        editText_editContent.setFocusable(state);
        editText_editContent.setInputType(InputType.TYPE_CLASS_TEXT);
        editText_editContent.setOnEditorActionListener(this);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        //If user hit "OK" or "Enter" button on his device keyboard
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
        }
        return false;
    }
}
