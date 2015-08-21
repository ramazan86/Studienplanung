package view;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cinardere_ramazan_ba_2015.studienplanung.R;

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

        for(int i = 0; i<Module.NUMBER_OF_MODULE_VIEW_COLUMNS; i++) {
            TextView tv = createTextView(module.toString(i), i);
            addViewIntoLinearLayoutRow(tv);

            if(i != (Module.NUMBER_OF_MODULE_VIEW_COLUMNS -1)) {
                View view = createVerticalLine();
                addViewIntoLinearLayoutRow(view);
            }
        }

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

    private View createVerticalLine( ) {

        View v = new View(this);
        v.setLayoutParams(new LinearLayout.LayoutParams(5, ViewGroup.LayoutParams.MATCH_PARENT));

        checkIfParamsIsInitialized();
        params = (LinearLayout.LayoutParams) v.getLayoutParams();
        params.setMargins(0, 4, 4, 4);

        v.setLayoutParams(params);
        v.setBackgroundColor(getResources().getColor(R.color.white));

        return v;
    }

    private TextView createTextView(String content, int i ) {

        int width = 0;

        switch (i) {
            case 0: width = 132; break;
            case 1: width = 500; break;
            case 2: width = 70;  break;
            case 3: width = 150; break;
            case 4: width = 200; break;
            case 5: width = 250; break;
            case 6: width = 250; break;
            case 7: width = 100; break;
        }



        TextView textView= new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

        checkIfParamsIsInitialized();

        params = (LinearLayout.LayoutParams) textView.getLayoutParams();
        int marginRight = Math.round(getResources().getDimension(R.dimen.dimen_1));
        int textSize = Math.round(getResources().getDimension(R.dimen.dimen_5));
            params.setMargins(0, 0, marginRight, 0);

            textView.setText(content);
            textView.setTextSize(25);
            textView.setLayoutParams(params);
            textView.setTextColor(getResources().getColor(R.color.black));

        return textView;

    }

    private void checkIfParamsIsInitialized() {
        if(params != null) params = null;
    }
}
