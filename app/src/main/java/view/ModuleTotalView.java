package view;

import android.app.Notification;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

import data.Module;
import data.ModuleManual;

/**
 * Created by Ramazan Cinardere} on 20.08.2015.
 */
public class ModuleTotalView extends ActionBarActivity {

    /////////////////////////////
    //       Attributes       //
    ////////////////////////////

    private TextView textView_headline = null;
    private LinearLayout.LayoutParams params  = null;
    private LinearLayout linearLayout_modules = null,
                         linearLayout_row     = null;
    private TextView textView_value   = null;
    private View view_verticalLine    = null;
    private ModuleManual moduleManual = null;

    private final int LINEAR_LAYOUT_HEIGHT = 150;

    /////////////////////////////
    //       Constructors      //
    /////////////////////////////


    /////////////////////////////
    //         Methods         //
    /////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getResources().getString(R.string.totalView));
        setContentView(R.layout.module_total_view);

        initComponents();

        moduleManual = (ModuleManual) getIntent().getExtras().getSerializable("moduleManual");

        assignValuesToComponents();
    }

    private void initComponents() {
        //textView headline
        textView_headline = (TextView) findViewById(R.id.module_totalView_textViewHeadline);

        //linearLayout
        linearLayout_modules = (LinearLayout) findViewById(R.id.module_totalView_linearLayout_modules);
    }

    private void assignValuesToComponents() {

        Module module = moduleManual.getModuleList().get(0);
        //textview headline
        createHeadLine();

        //Each linearLayout_row shows one module
        createLinearLayoutForEachModule();

        createTextView(module.toString(6));

        createVerticalLine();

        createTextView(module.toString(1));

            //add view into linearlayout_row
            addViewIntoLinearLayoutRow(textView_value);
            addViewIntoLinearLayoutRow(view_verticalLine);


            //add view into linearlayout_wrap
            linearLayout_modules.addView(linearLayout_row);

    }

    private void addViewIntoLinearLayoutRow(View view) {

        linearLayout_row.addView(view);
        view = null;
    }

    private void createHeadLine() {

        if(textView_headline != null) {
            textView_headline.setText(getResources().getString(R.string.totalView));
            checkIfParamsIsInitialized();

            params = (LinearLayout.LayoutParams) textView_headline.getLayoutParams();

            textView_headline.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            textView_headline.setTextSize(getResources().getDimension(R.dimen.dimen_2));
            textView_headline.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    private void createLinearLayoutForEachModule() {

        if(linearLayout_row != null) {
            linearLayout_row = null;
        }

        linearLayout_row = new LinearLayout(this);
        //LayoutParams : 1st Param: width, 2nd Param: height
        linearLayout_row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LINEAR_LAYOUT_HEIGHT));
        linearLayout_row.setBackgroundColor(getResources().getColor(R.color.red));

        //linearlayout margin
        params = (LinearLayout.LayoutParams) linearLayout_row.getLayoutParams();
        params.setMargins(0, 10, 0, 0);
        linearLayout_row.setLayoutParams(params);



    }

    private void createVerticalLine() {

        if(view_verticalLine != null) {
            view_verticalLine = null;
        }

        view_verticalLine = new View(this);
        view_verticalLine.setLayoutParams(new LinearLayout.LayoutParams(3, ViewGroup.LayoutParams.MATCH_PARENT));

        checkIfParamsIsInitialized();
        params = (LinearLayout.LayoutParams) view_verticalLine.getLayoutParams();
        params.setMargins(0, 0, 4, 0);

        view_verticalLine.setLayoutParams(params);
        view_verticalLine.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void createTextView(String content) {

        if(textView_value != null) {
            textView_value = null;
        }

        textView_value = new TextView(this);
        textView_value.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView_value.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        checkIfParamsIsInitialized();

        params = (LinearLayout.LayoutParams) textView_value.getLayoutParams();
        int marginRight = Math.round(getResources().getDimension(R.dimen.dimen_1));
        int textSize = Math.round(getResources().getDimension(R.dimen.dimen_5));
        params.setMargins(0,0,marginRight,0);

        textView_value.setText(content);
        textView_value.setTextSize(30);
        textView_value.setLayoutParams(params);
        textView_value.setTextColor(getResources().getColor(R.color.black));

    }

    private void checkIfParamsIsInitialized() {
        if(params != null) params = null;
    }
}
