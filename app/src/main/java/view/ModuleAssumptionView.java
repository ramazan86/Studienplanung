package view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import data.Module;
import data.ModuleManual;

/**
 * Created by Ramazan Cinardere} on 22.08.2015.
 */
public class ModuleAssumptionView extends ActionBarActivity implements View.OnClickListener {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private String semester = "";
    private ModuleManual moduleManual = null;
    private Module module             = null;

    private ImageButton imageButton_participate = null,
                        imageButton_exam        = null,
                        imageButton_contentual  = null;

    private TextView textView_headLine = null;

    private EditText editText_participate  = null,
                     editText_exam         = null,
                     editText_contentual   = null;

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
        setValuesIntoComponents();
    }

    @SuppressLint("LongLogTag")
    private void setValuesIntoComponents() {

        try {
            editText_exam.setText(module.getExaminationPrerequisite());
            editText_contentual.setText(module.getContentualPrerequisite());
            editText_participate.setText(module.getParticipatePrerequisite());
        }catch (Exception e) {
            Log.e("Exception in" +getClass().getName() + ".setValuesIntoComponents()"," -> " +e.getMessage() + " " +e.getCause());
            e.printStackTrace();
        }

    }

    private void createHeadline() {
        textView_headLine.setText(module.getTitle());
    }

    private void initComponents() {

        //ImageButton
        imageButton_contentual = (ImageButton) findViewById(R.id.moduleAssumptionView_imageButton_contentualAssumption);
        imageButton_contentual.setOnClickListener(this);

        imageButton_exam = (ImageButton) findViewById(R.id.moduleAssumptionView_imageButton_exam);
        imageButton_exam.setOnClickListener(this);

        imageButton_participate = (ImageButton) findViewById(R.id.moduleAssumptionView_imageButton_participate);
        imageButton_participate.setOnClickListener(this);

        //TextView
        textView_headLine = (TextView) findViewById(R.id.moduleAssumptionView_textView_headLine);

        //Edittext
        editText_contentual = (EditText) findViewById(R.id.moduleAssumptionView_editText_contentualAssumption);
        editText_participate = (EditText) findViewById(R.id.moduleAssumptionView_editText_participateAssumption);
        editText_exam = (EditText) findViewById(R.id.moduleAssumptionView_editText_examAssumption);
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

    }
}
