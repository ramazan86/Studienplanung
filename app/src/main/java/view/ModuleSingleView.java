package view;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import org.w3c.dom.Text;

import data.Module;
import data.ModuleManual;

/**
 * Created by Ramazan Cinardere} on 20.08.2015.
 */
public class ModuleSingleView extends ActionBarActivity implements View.OnClickListener {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private LinearLayout linearLayout_wrap = null;
    private String semester = "";

    private ModuleManual moduleManual = null;
    private View view_status          = null;

    private TextView textView_moduleTitle  = null,
                     textView_creditPoints = null,
                     textView_note         = null,
                     textView_content      = null,
                     textView_assumption   = null;

    private String moduleTitle = "";
    private Module module = null;
    private LinearLayout.LayoutParams params = null;

    private final int REQUEST_CODE = 101;
    //


    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    public void finish() {
        Intent data = new Intent();
            data.putExtra("semester", semester);
            data.putExtra("moduleManual", moduleManual);
            setResult(Activity.RESULT_OK, data);
        super.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_single_view);

        initComponents();

        //get extras
        getPassedValues();

        //change title of actionbar

        setTitle(getString(R.string.detailView));

        //assign values to module_single_view
        assignValuesToComponents();
    }

    private void assignValuesToComponents() {


        float textSize = 0;

       //TextView: moduleTitle
        if(textView_moduleTitle != null) {

        //moduletitle

            String[] m = moduleTitle.split(" ");
            textView_moduleTitle.setText(" " +module.getTitle());
            textView_moduleTitle.setTextColor(getResources().getColor(R.color.black));

            if(m.length >3) {
                textSize = getResources().getDimension(R.dimen.dimen_1_5);
            }else {
                textSize = getResources().getDimension(R.dimen.dimen_2);
            }
            textView_moduleTitle.setTextSize(textSize);
        }

        //creditpoints
        textView_creditPoints.setText(module.getCreditPoints());

        //contents
        textView_content.setText(getResources().getString(R.string.content));
        textView_content.setTextSize(textSize);
        textView_content.setBackgroundColor(getResources().getColor(R.color.gray));

        if(params != null) params = null;
        params = (LinearLayout.LayoutParams) textView_content.getLayoutParams();
        params.setMargins(60, 200, 60, 0);

        textView_content.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textView_content.setLayoutParams(params);

        //assumption
        textView_assumption.setText(getResources().getString(R.string.assumption));
        textView_assumption.setTextSize(textSize);
        textView_assumption.setBackgroundColor(getResources().getColor(R.color.gray));

        if(params != null) params = null;
        params = (LinearLayout.LayoutParams) textView_assumption.getLayoutParams();
        params.setMargins(60, 30, 60, 0);
        textView_assumption.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        textView_assumption.setLayoutParams(params);


    }

    private void getPassedValues() {
        semester = getIntent().getExtras().getString("semester");
        moduleManual = (ModuleManual) getIntent().getExtras().getSerializable("moduleManual");

        //module object
        module = (Module) getIntent().getExtras().getSerializable("module");
        moduleTitle = module.getTitle();
    }

    private void initComponents() {

        //creditpoints
        textView_creditPoints = (TextView) findViewById(R.id.moduleSingleView_textView_cp);

        //Title of module
        textView_moduleTitle = (TextView) findViewById(R.id.module_singleView_moduleTitle);

        //content
        textView_content = (TextView) findViewById(R.id.module_single_view_textView_content);
        textView_content.setOnClickListener(this);

        //assumption
        textView_assumption = (TextView) findViewById(R.id.module_single_view_textView_assumption);
        textView_assumption.setOnClickListener(this);
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
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.module_single_view_textView_content:
                Intent content = new Intent(this, ModuleContentView.class);
                    content.putExtra("semester", semester);
                    content.putExtra("moduleManual", moduleManual);
                    content.putExtra("module", module);
                startActivityForResult(content, REQUEST_CODE);
                break;

            case R.id.module_single_view_textView_assumption:
                Intent assumption = new Intent(this, ModuleAssumptionView.class);
                    assumption.putExtra("semester", semester);
                    assumption.putExtra("moduleManual", moduleManual);
                    assumption.putExtra("module", module);
                startActivityForResult(assumption, REQUEST_CODE);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {

            case Activity.RESULT_OK:
                semester = data.getExtras().getString("semester");
                moduleManual = (ModuleManual) data.getExtras().getSerializable("moduleManual");
                module = (Module) data.getExtras().getSerializable("module");
                break;
        }



    }
}
